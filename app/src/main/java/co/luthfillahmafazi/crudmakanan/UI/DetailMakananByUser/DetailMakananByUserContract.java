package co.luthfillahmafazi.crudmakanan.UI.DetailMakananByUser;


import android.content.Context;
import android.net.Uri;

import java.util.List;

import co.luthfillahmafazi.crudmakanan.Model.Makanan.MakananData;

public interface DetailMakananByUserContract {
    interface View {
        void showProgress();
        void hideProgress();
        void showFoodByUser(MakananData makananData);
        void showMessage(String msg);
        void successDelete();
        void successUpdate();
        void showSpinnerCategory(List<MakananData> categoryDataList);
    }
    interface Presenter {
        void getCategory();
        void getDetailMakanan(String idMakanan);
        void UpdateDataMakanan(Context context, Uri filePath, String namaMakanan, String descMakanan,
                               String idCategory, String namaFotoMakanan, String idMakanan);
        void deleteMakanan(String idMakanan, String namaFotoMakanan);
    }
}
