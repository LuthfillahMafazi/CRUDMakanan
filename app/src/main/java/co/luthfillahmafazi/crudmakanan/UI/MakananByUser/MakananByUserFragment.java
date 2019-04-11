package co.luthfillahmafazi.crudmakanan.UI.MakananByUser;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import co.luthfillahmafazi.crudmakanan.Adapter.MakananAdapter;
import co.luthfillahmafazi.crudmakanan.Model.Makanan.MakananData;
import co.luthfillahmafazi.crudmakanan.R;
import co.luthfillahmafazi.crudmakanan.Utils.Constant;

/**
 * A simple {@link Fragment} subclass.
 */
public class MakananByUserFragment extends Fragment implements MakananByUserContract.View {


    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;
    @BindView(R.id.txt_info)
    TextView txtInfo;
    @BindView(R.id.rl_progress_by_user)
    RelativeLayout rlProgressByUser;
    @BindView(R.id.rv_makanan)
    RecyclerView rvMakanan;
    @BindView(R.id.sr_makanan_by_user)
    SwipeRefreshLayout srMakananByUser;
    Unbinder unbinder;

    private MakananByUserPresenter makananByUserPresenter = new MakananByUserPresenter(this);
    private String idUser;

    public MakananByUserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_makanan_by_user, container, false);
        unbinder = ButterKnife.bind(this, view);

//        rlProgressByUser = getView().findViewById(R.id.rl_progress_by_user);

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Mengambil id user dari sharedPreference
        SharedPreferences pref = getContext().getSharedPreferences(Constant.pref_name, 0);

        // Memasukan id user yang sudah di ambil ke dalam variabel
        idUser = pref.getString(Constant.KEY_USER_ID, "");
        Log.i("cek", "onCreateView: " + idUser);

        // merequest data makanan by user
        makananByUserPresenter.getListFoodByUser(idUser);

        srMakananByUser.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srMakananByUser.setRefreshing(false);
                makananByUserPresenter.getListFoodByUser(idUser);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        makananByUserPresenter.getListFoodByUser(idUser);
    }

    @Override
    public void showProgress() {
        rlProgressByUser.setVisibility(View.VISIBLE);
        srMakananByUser.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        rlProgressByUser.setVisibility(View.GONE);
        srMakananByUser.setVisibility(View.VISIBLE);
        rvMakanan.setVisibility(View.VISIBLE);
    }

    @Override
    public void showFoodByUser(List<MakananData> foodByUserList) {
        rvMakanan.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMakanan.setAdapter(new MakananAdapter(MakananAdapter.TYPE_5, getContext(), foodByUserList));
    }

    @Override
    public void showFailureMessage(String msg) {
        srMakananByUser.setVisibility(View.VISIBLE);
        rlProgressByUser.setVisibility(View.VISIBLE);
        rvMakanan.setVisibility(View.GONE);
        pbLoading.setVisibility(View.VISIBLE);
        txtInfo.setText(msg);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
