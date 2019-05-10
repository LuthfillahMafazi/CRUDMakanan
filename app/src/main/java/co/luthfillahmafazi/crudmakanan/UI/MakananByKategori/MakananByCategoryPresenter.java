package co.luthfillahmafazi.crudmakanan.UI.MakananByKategori;

import co.luthfillahmafazi.crudmakanan.Data.Remote.ApiClient;
import co.luthfillahmafazi.crudmakanan.Data.Remote.ApiInterface;
import co.luthfillahmafazi.crudmakanan.Model.Makanan.MakananResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MakananByCategoryPresenter implements MakananByCategoryContract.Presenter {

    private final MakananByCategoryContract.View view;
    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);


    public MakananByCategoryPresenter(MakananByCategoryContract.View view) {
        this.view = view;
    }

    @Override
    public void getListFoodByCategory(String idCategory) {
        view.showProgress();

        if (idCategory.isEmpty()) {
            view.showFailure("Data Category tidak ada");
            return;
        }
       Call<MakananResponse> call = apiInterface.getMakananByCategory(Integer.valueOf(idCategory));
        call.enqueue(new Callback<MakananResponse>() {
            @Override
            public void onResponse(Call<MakananResponse> call, Response<MakananResponse> response) {
                view.hideProgress();
                if (response.body() != null) {
                    if (response.body().getResult() == 1) {
                        view.showFoodByCategory(response.body().getData());
                    } else {
                        view.showFailure(response.body().getMessage());
                    }
                } else {
                    view.showFailure("Data Kosong");
                }
            }

            @Override
            public void onFailure(Call<MakananResponse> call, Throwable t) {
                view.hideProgress();
                view.showFailure("Koneksi gagal");
            }
        });
    }
}
