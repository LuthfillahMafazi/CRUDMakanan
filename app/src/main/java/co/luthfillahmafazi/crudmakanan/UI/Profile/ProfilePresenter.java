package co.luthfillahmafazi.crudmakanan.UI.Profile;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;

import co.luthfillahmafazi.crudmakanan.Data.Remote.ApiClient;
import co.luthfillahmafazi.crudmakanan.Data.Remote.ApiInterface;
import co.luthfillahmafazi.crudmakanan.Model.Login.LoginData;
import co.luthfillahmafazi.crudmakanan.Model.Login.LoginResponse;
import co.luthfillahmafazi.crudmakanan.Utils.Constant;
import co.luthfillahmafazi.crudmakanan.Utils.SesssionManager;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilePresenter implements ProfileContract.Presenter {

    private final ProfileContract.View view;
    private SharedPreferences pref;
    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

    public ProfilePresenter(ProfileContract.View view) {
        this.view = view;
    }

    @Override
    public void updateDataUser(final Context context, final LoginData loginData) {

        //show Porgress
        view.showProgress();
        // Membuat object call dan membuat method updateuser serta mengirimkan datanya
        Call<LoginResponse> call = apiInterface.updateUser(Integer.valueOf(loginData.getId_user()),
                loginData.getNama_user(), loginData.getAlamat(), loginData.getJenkel(), loginData.getNo_telp());

        // mengeksekusi call
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                view.hideProgress();
                // mencek response dan isi body
                if (response.isSuccessful() && response.body() != null) {
                    // mencek result apakah 1
                    if (response.body().getResult() == 1) {
                        // steleah berhasil update ke server online, lalu update ke SharedPreference
                        // membuat object SharedPreferences yang sudah ada di session manager
                        pref = context.getSharedPreferences(Constant.pref_name, 0);
                        // Mengubah mode SharedPreferences menjadi edit
                        SharedPreferences.Editor editor = pref.edit();
                        // Memasukan data ke dalam SharedPreferences
                        editor.putString(Constant.KEY_USER_NAMA, loginData.getNama_user());
                        editor.putString(Constant.KEY_USER_ALAMAT, loginData.getAlamat());
                        editor.putString(Constant.KEY_USER_NOTELP, loginData.getNo_telp());
                        editor.putString(Constant.KEY_USER_JENKEL, loginData.getJenkel());
                        // Apply perubahan
                        editor.apply();
                        view.showSuccessUpdateUser(response.body().getMessage());
                    } else {
                        view.showSuccessUpdateUser(response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                view.hideProgress();
                view.showSuccessUpdateUser(t.getMessage());
            }
        });

        // membuat object SharedPreferences yang sudah ada di session manager
        pref = context.getSharedPreferences(Constant.pref_name, 0);
        // Mengubah mode SharedPreferences menjadi edit
        SharedPreferences.Editor editor = pref.edit();
        // Memasukan data ke dalam SharedPreferences
        editor.putString(Constant.KEY_USER_NAMA, loginData.getNama_user());
        editor.putString(Constant.KEY_USER_ALAMAT, loginData.getAlamat());
        editor.putString(Constant.KEY_USER_NOTELP, loginData.getNo_telp());
        editor.putString(Constant.KEY_USER_JENKEL, loginData.getJenkel());
        // Apply perubahan
        editor.apply();
        view.showSuccessUpdateUser("Update Berhasil");
    }

    @Override
    public void getData(Context context) {
        // Pengambilan data dari SharedPreference
        pref = context.getSharedPreferences(Constant.pref_name, 0);

        // Membuat object model logindata untuk penampung
        LoginData loginData = new LoginData();
        // Memasukan data shared preference ke dalam model login data
        loginData.setId_user(pref.getString(Constant.KEY_USER_ID, ""));
        loginData.setNama_user(pref.getString(Constant.KEY_USER_NAMA, ""));
        loginData.setAlamat(pref.getString(Constant.KEY_USER_ALAMAT, ""));
        loginData.setNo_telp(pref.getString(Constant.KEY_USER_NOTELP, ""));
        loginData.setJenkel(pref.getString(Constant.KEY_USER_JENKEL, ""));

        // Mengirim data model loginDAta ke view
        view.showDataUser(loginData);
    }

    @Override
    public void uploadProfile(Context context, Uri filePath) {
        // Mengambil alamat file image
        File myFile = new File(filePath.getPath());
        Uri selectedGambar = getImageContentUri(context, myFile, filePath);
        String partImage = getPath(context, selectedGambar);
        File imageFile = new File(partImage);

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
        MultipartBody.Part mPartImage = MultipartBody.Part.createFormData("image", imageFile.getName(), requestBody);


    }

    private String getPath(Context context, Uri selectedGambar) {
        Cursor cursor = context.getContentResolver().query(selectedGambar, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ",
                new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;

    }

    private Uri getImageContentUri(Context context, File myFile, Uri filePath) {
        String fileAbsolutePath = myFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{fileAbsolutePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            // Apabila file gambar sudah pernah diapakai namun ada kondisi lain yang belum diketahui
            // Apabila file gambar sudah pernah dipakai pengambilan bukan di galery

            Log.i("Isi Selected if", "Masuk cursor ada");
            return filePath;

        } else {
            Log.i("Isi Selected else", "cursor tidak ada");
            if (myFile.exists()) {
                // Apabila file gambar baru belum pernah di pakai
                Log.i("Isi Selected else", "imagefile exists");
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, fileAbsolutePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                // Apabila file gambar sudah pernah dipakai
                // Apabila file gambar sudah pernah dipakai di galery
                Log.i("Isi Selected else", "imagefile tidak exists");
                return filePath;
            }
        }
    }

    @Override
    public void logoutSession(Context context) {
        // Membuat class session Manager untuk memanggil method logout
        SesssionManager sesssionManager = new SesssionManager(context);
        sesssionManager.logout();
    }
}
