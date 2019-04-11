package co.luthfillahmafazi.crudmakanan.UI.UploadMakanan;

import android.content.Context;
import android.net.Uri;

import java.util.List;

import co.luthfillahmafazi.crudmakanan.Model.Makanan.MakananData;

public interface UploadMakananContract {
    interface View {
        void showProgress();
        void hideProgress();
        void showMessage(String msg);
        void successUpload();
        void showSinnerCategory(List<MakananData> categoryDataList);
    }
    interface Presenter {
        void getCategory();
        void UploadMakanan(Context context, Uri filePath, String namaMakanan, String descMakanan
        , String idCategory);
    }
}
