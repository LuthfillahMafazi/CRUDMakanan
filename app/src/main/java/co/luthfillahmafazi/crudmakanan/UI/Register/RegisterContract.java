package co.luthfillahmafazi.crudmakanan.UI.Register;

import android.widget.EditText;

import co.luthfillahmafazi.crudmakanan.Model.Login.LoginData;

public interface RegisterContract {
    interface View{
        void showProgress();
        void hideProgress();
        void showError(String message);
        void showRegisterSucces(String message);
    }
    interface Presenter{
        void doRegisterUser(LoginData loginData);
    }
}
