package co.luthfillahmafazi.crudmakanan.UI.Makanan;

import java.util.List;

import co.luthfillahmafazi.crudmakanan.Model.Makanan.MakananData;

public interface MakananContract {
    interface View{
        void showProgress();
        void hideProgress();
        void showFoodNewsList(List<MakananData> foodNewsList);
        void showFoodPopulerList(List<MakananData> foodPopulerList);
        void showFoodKategoryList(List<MakananData> foodKategoryList);
        void showFailureMessage(String message);
    }
    interface Presenter{
        void getListFoodNews();
        void getListFoodPopuler();
        void getListFoodKategory();
    }
}
