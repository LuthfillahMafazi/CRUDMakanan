package co.luthfillahmafazi.crudmakanan.UI.DetailMakanan;

import co.luthfillahmafazi.crudmakanan.Model.Makanan.MakananData;

public interface DetailMakananContract {
    interface View {
        void showProgress();
        void hideProgress();
        void showDetailMakanan(MakananData makananData);
        void showFailureMessage(String msg);
    }
    interface Presenter {
        void getDetailMakanan(String idMakanan);
    }
}
