package co.luthfillahmafazi.crudmakanan.UI.MakananByKategori;

import java.util.List;

import co.luthfillahmafazi.crudmakanan.Model.Makanan.MakananData;

public interface MakananByCategoryContract {
    interface View {
        void showProgress();
        void hideProgress();
        void showFoodByCategory(List<MakananData> foodNewList);
        void showFailure(String msg);
    }
    interface Presenter {
        void getListFoodByCategory(String idCategory);
    }
}
