package co.luthfillahmafazi.crudmakanan.UI.Main;

import android.content.Context;

import co.luthfillahmafazi.crudmakanan.Utils.SesssionManager;

public class MainPresenter implements MainContract.Presenter {
    @Override
    public void logoutSession(Context context) {
        // Membuat object SessionManager untuk dapat digunakan
        SesssionManager mSesssionManager = new SesssionManager(context);
        mSesssionManager.logout();
    }
}
