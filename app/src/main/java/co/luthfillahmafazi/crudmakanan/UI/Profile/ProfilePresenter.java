package co.luthfillahmafazi.crudmakanan.UI.Profile;

import android.content.Context;
import android.content.SharedPreferences;

import co.luthfillahmafazi.crudmakanan.Model.Login.LoginData;
import co.luthfillahmafazi.crudmakanan.Utils.Constant;
import co.luthfillahmafazi.crudmakanan.Utils.SesssionManager;

public class ProfilePresenter implements ProfileContract.Presenter {

    private final ProfileContract.View view;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public ProfilePresenter(ProfileContract.View view) {
        this.view = view;
    }

    @Override
    public void getData(Context context) {
        // Pengambilan data dari SharedPreference
        pref = context.getSharedPreferences(Constant.pref_name,0);

        // Membuat object model logindata untuk penampung
        LoginData loginData = new LoginData();
        // Memasukan data shared preference ke dalam model login data
        loginData.setId_user(pref.getString(Constant.KEY_USER_ID, ""));
        loginData.setNama_user(pref.getString(Constant.KEY_USER_NAMA,""));
        loginData.setAlamat(pref.getString(Constant.KEY_USER_ALAMAT,""));
        loginData.setNo_telp(pref.getString(Constant.KEY_USER_NOTELP,""));
        loginData.setJenkel(pref.getString(Constant.KEY_USER_JENKEL,""));

        // Mengirim data model loginDAta ke view
        view.showDataUser(loginData);
    }

    @Override
    public void logoutSession(Context context) {
        // Membuat class session Manager untuk memanggil method logout
        SesssionManager sesssionManager = new SesssionManager(context);
        sesssionManager.logout();
    }
}
