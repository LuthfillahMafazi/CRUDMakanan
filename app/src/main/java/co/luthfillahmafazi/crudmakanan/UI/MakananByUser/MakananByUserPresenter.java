package co.luthfillahmafazi.crudmakanan.UI.MakananByUser;

import co.luthfillahmafazi.crudmakanan.Data.Remote.ApiClient;
import co.luthfillahmafazi.crudmakanan.Data.Remote.ApiInterface;
import co.luthfillahmafazi.crudmakanan.Model.Makanan.MakananResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MakananByUserPresenter implements MakananByUserContract.Presenter {

    private final MakananByUserContract.View view;
    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

    public MakananByUserPresenter(MakananByUserContract.View view) {
        this.view = view;
    }

    @Override
    public void getListFoodByUser(String idUser) {
        view.showProgress();

        if (idUser.isEmpty()) {
            view.showFailureMessage(" Id Kosong");
            return;
        }
        Call<MakananResponse> call = apiInterface.getMakananByUser(Integer.valueOf(idUser));
        call.enqueue(new Callback<MakananResponse>() {
            @Override
            public void onResponse(Call<MakananResponse> call, Response<MakananResponse> response) {
                view.hideProgress();
                if (response != null) {
                    if (response.body().getResult() == 1) {
                        view.showFoodByUser(response.body().getData());
                    } else {
                        response.body().getMessage();
                    }
                }else {
                    view.showFailureMessage("Data kosong");
                }
            }

            @Override
            public void onFailure(Call<MakananResponse> call, Throwable t) {
                view.hideProgress();
                view.showFailureMessage(t.getMessage());
            }
        });
    }
}
