package co.luthfillahmafazi.crudmakanan.UI.Login;

import android.content.Context;

import co.luthfillahmafazi.crudmakanan.Model.Login.LoginData;

public interface LoginContract {
    interface View{
        void showProgress();
        void hidePorgress();
        void loginSucces(String message, LoginData loginData);
        void loginFailure(String message);
        void usernameError(String message);
        void passwordError(String message);
        void isLogin();
    }
    interface Presenter{
        void doLogin(String username, String password);
        void saveDataUser(Context context, LoginData loginData);
        void checkLogin(Context context);
    }
}

