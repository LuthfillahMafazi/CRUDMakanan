package co.luthfillahmafazi.crudmakanan.UI.MakananByKategori;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.luthfillahmafazi.crudmakanan.Adapter.MakananAdapter;
import co.luthfillahmafazi.crudmakanan.Model.Makanan.MakananData;
import co.luthfillahmafazi.crudmakanan.R;
import co.luthfillahmafazi.crudmakanan.Utils.Constant;

public class MakananByCategoriActivity extends AppCompatActivity implements MakananByCategoryContract.View {

    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;
    @BindView(R.id.txt_info)
    TextView txtInfo;
    @BindView(R.id.rl_progress)
    RelativeLayout rlProgress;
    @BindView(R.id.rv_makanan)
    RecyclerView rvMakanan;
    @BindView(R.id.sr_makanan)
    SwipeRefreshLayout srMakanan;

    private MakananByCategoryPresenter makananByCategoryPresenter = new MakananByCategoryPresenter(this);
    private String idCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makanan_by_categori);
        ButterKnife.bind(this);

        // Mengambil id categiru dari kiriman halaman sebelumnya
        idCategory = getIntent().getStringExtra(Constant.KEY_EXTRA_ID_CATEGORY);
        // nereqyest makanan by id catedory
        makananByCategoryPresenter.getListFoodByCategory(idCategory);
        // mensetting swipe refresh menghilangkan loading dan memanggil ulang data makanan by category
        srMakanan.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srMakanan.setRefreshing(false);
                makananByCategoryPresenter.getListFoodByCategory(idCategory);
            }
        });

    }

    @Override
    public void showProgress() {
        rlProgress.setVisibility(View.VISIBLE);
        srMakanan.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        rlProgress.setVisibility(View.GONE);
        rvMakanan.setVisibility(View.VISIBLE);
        srMakanan.setVisibility(View.VISIBLE);
    }

    @Override
    public void showFoodByCategory(List<MakananData> foodNewList) {
        rvMakanan.setLayoutManager(new LinearLayoutManager(this));
        rvMakanan.setAdapter(new MakananAdapter(MakananAdapter.TYPE_4, this, foodNewList));
    }

    @Override
    public void showFailure(String msg) {
        srMakanan.setVisibility(View.VISIBLE);
        rvMakanan.setVisibility(View.GONE);
        pbLoading.setVisibility(View.GONE);
        txtInfo.setText(msg);
    }
}
