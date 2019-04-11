package co.luthfillahmafazi.crudmakanan.UI.Profile;

import android.content.Context;
import android.net.Uri;

import co.luthfillahmafazi.crudmakanan.Model.Login.LoginData;

public interface ProfileContract {
    interface View{
        void showProgress();
        void hideProgress();
        void showMessage(String msg);
        void showSuccessUpdateUser(String message);
        void showDataUser(LoginData loginData);
    }
    interface Presenter{
        void updateDataUser(Context context, LoginData loginData);
        void getData(Context context);
        void uploadProfile(Context context, Uri filePath);
        void logoutSession(Context context);
    }
}
