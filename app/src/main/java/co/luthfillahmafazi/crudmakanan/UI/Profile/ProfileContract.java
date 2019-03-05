package co.luthfillahmafazi.crudmakanan.UI.Profile;

import android.content.Context;

import co.luthfillahmafazi.crudmakanan.Model.Login.LoginData;

public interface ProfileContract {
    interface View{
        void showDataUser(LoginData loginData);
    }
    interface Presenter{
        void getData(Context context);
        void logoutSession(Context context);
    }
}
