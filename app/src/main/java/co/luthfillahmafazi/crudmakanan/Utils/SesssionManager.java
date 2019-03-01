package co.luthfillahmafazi.crudmakanan.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import co.luthfillahmafazi.crudmakanan.Model.Login.LoginData;
import co.luthfillahmafazi.crudmakanan.UI.Login.LoginActivity;

public class SesssionManager {
    // Membuat variabel global untuk shared preference
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private final Context context;

    public SesssionManager(Context context) {
        this.context = context;
        // Membuat object shared preference untuk siap digunakan
        pref = context.getSharedPreferences(Constant.pref_name,0);

        // Membuat pref dengan mode edti
        editor = pref.edit();
    }
    // Membuat finction untuk memasukan data
    public void createSession(LoginData loginData){
        // Memasukan data user yang sidah login ke dalam shared preference
        editor.putBoolean(Constant.KEY_IS_LOGIN, true);
        // Memasukan data2 user yg terdiri dari user, id dll
        editor.putString(Constant.KEY_USER_ID ,loginData.getId_user());
        editor.putString(Constant.KEY_USER_NAMA,loginData.getNama_user());
        editor.putString(Constant.KEY_USER_ALAMAT, loginData.getAlamat());
        editor.putString(Constant.KEY_USER_JENKEL,loginData.getJenkel());
        editor.putString(Constant.KEY_USER_NOTELP, loginData.getNo_telp());
        editor.putString(Constant.KEY_USER_USERNAME, loginData.getUsername());
        editor.putString(Constant.KEY_USER_LEVEL, loginData.getLevel());

        // Mengeksekusi penyimpanan
        editor.commit();

    }

    // Function untuk mengecek apakah user sudah pernah login
    public boolean isLogin(){
        // Mengembalikan nilai boolean dengan mengambil data dari pref KEY_IS_LOGIN
        return pref.getBoolean(Constant.KEY_IS_LOGIN, false);
    }

    // Function untuk melakukan logout atau menghapus isi di dalam shared preference
    public void logout(){
        // Memanggil method clear untuk menhapus data sharedrederence
        editor.clear();
        // Mengeksekusi perintah clear
        editor.commit();
        // Membuat intent untuk berpindah halaman
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
