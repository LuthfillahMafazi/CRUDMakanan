package co.luthfillahmafazi.crudmakanan.UI.MakananByUser;

import java.util.List;

import co.luthfillahmafazi.crudmakanan.Model.Makanan.MakananData;

public interface MakananByUserContract {
    interface View {
        void showProgress();
        void hideProgress();
        void showFoodByUser(List<MakananData> foodByUserList);
        void showFailureMessage(String msg);
    }
    interface Presenter {
        void getListFoodByUser(String idUser);
    }
}
