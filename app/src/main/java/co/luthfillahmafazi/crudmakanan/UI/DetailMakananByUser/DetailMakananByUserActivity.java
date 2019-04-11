package co.luthfillahmafazi.crudmakanan.UI.DetailMakananByUser;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.luthfillahmafazi.crudmakanan.Model.Makanan.MakananData;
import co.luthfillahmafazi.crudmakanan.R;
import co.luthfillahmafazi.crudmakanan.Utils.Constant;

public class DetailMakananByUserActivity extends AppCompatActivity implements DetailMakananByUserContract.View {

    @BindView(R.id.img_picture)
    ImageView imgPicture;
    @BindView(R.id.fab_choose_picture)
    FloatingActionButton fabChoosePicture;
    @BindView(R.id.layoutPicture)
    CardView layoutPicture;
    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_desc)
    EditText edtDesc;
    @BindView(R.id.spin_category)
    Spinner spinCategory;
    @BindView(R.id.layoutSaveMakanan)
    CardView layoutSaveMakanan;
    @BindView(R.id.btn_update)
    Button btnUpdate;
    @BindView(R.id.btn_delete)
    Button btnDelete;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    private DetailMakananByUserPresenter detailMakananByUserPresenter = new DetailMakananByUserPresenter(this);
    private Uri filePath;
    private String idCategory, idMakanan;
    private MakananData mMakananData;
    private String namaFotoMakanan;
    private String[] mIdCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_makanan_bu_user);
        ButterKnife.bind(this);
        // melakukan pengecekan untuk bisa mengakses gallert
        permissionGallery();
        // menangkap id makanan yang dikirimkan dari activity sebelumnya
        idMakanan = getIntent().getStringExtra(Constant.KEY_EXTRA_ID_MAKANAN);

        // mengambil data category untuk ditampilkan di spinner
        detailMakananByUserPresenter.getCategory();

        // mensetting swipe refresh
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefresh.setRefreshing(false);
                detailMakananByUserPresenter.getCategory();
            }
        });
    }

    private void permissionGallery() {
        // Mencek apakah user sudah memberikan permission untuk mengakses external storage
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Constant.STORAGE_PERMISSION_CODE);

    }

    @Override
    public void showProgress() {
        swipeRefresh.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void showFoodByUser(MakananData makananData) {
        // kita ambil smua data detail makanan
        mMakananData = makananData;
        // Mengambil nama foto makanan
        namaFotoMakanan = makananData.getFoto_makanan();
        // mengambil id category
        idCategory = makananData.getId_kategori();
        // menampilkna smua data ke lyar
        edtName.setText(makananData.getNama_makanan());
        edtDesc.setText(makananData.getDesc_makanan());

        for (int i = 0; i < mIdCategory.length; i++) {
            if (Integer.valueOf(mIdCategory[i]).equals(Integer.valueOf(idCategory))) {
                spinCategory.setSelection(i);
            }
        }

        // menampilkan gambar makanan
        RequestOptions requestOptions = new RequestOptions().error(R.drawable.ic_broken_image).placeholder(R.drawable.ic_broken_image);
        Glide.with(this).load(makananData.getUrl_makanan()).apply(requestOptions).into(imgPicture);
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successDelete() {
        finish();
    }

    @Override
    public void successUpdate() {
        detailMakananByUserPresenter.getCategory();
    }

    @Override
    public void showSpinnerCategory(final List<MakananData> categoryDataList) {
        // Membuat data penampung untuk spinner
      //  List<String> listSpinner = new ArrayList<>();

        //

        String [] namaCategory = new String[categoryDataList.size()];
        mIdCategory = new String[categoryDataList.size()];

        for (int i = 0; i < categoryDataList.size(); i++) {
//            listSpinner.add(categoryDataList.get(i).getNama_kategori());
            namaCategory[i] = categoryDataList.get(i).getNama_kategori();
            mIdCategory[i] = categoryDataList.get(i).getId_kategori();
        }

        // Membuat adapter spinner
        ArrayAdapter<String> categorySpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, namaCategory);
        // settinf spinner dengan 1 line
        categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        // Memasukan adapter ke spinner
        spinCategory.setAdapter(categorySpinnerAdapter);

        spinCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Mengambil id category sesuai dengan pilihan user
                idCategory = categoryDataList.get(position).getId_kategori();

                // untuk menghilangkan swipe refrest
                swipeRefresh.post(new Runnable() {
                    @Override
                    public void run() {
                        hideProgress();
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        detailMakananByUserPresenter.getDetailMakanan(idMakanan);
    }

    @OnClick({R.id.fab_choose_picture, R.id.btn_update, R.id.btn_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fab_choose_picture:
                showFileChooser();
                break;
            case R.id.btn_update:
                // Mengirim data ke presenter
                detailMakananByUserPresenter.UpdateDataMakanan(this,filePath, edtName.getText().toString(),
                        edtDesc.getText().toString(), idCategory, namaFotoMakanan , idMakanan);
                break;
            case R.id.btn_delete:
                  detailMakananByUserPresenter.deleteMakanan(idMakanan, namaFotoMakanan);
                break;
        }
    }

    private void showFileChooser() {
        // Membuat object intent untuk dapat memilih data
        Intent intentGallery = new Intent(Intent.ACTION_PICK);
        intentGallery.setType("image/*");
        intentGallery.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intentGallery, "Select Picture"),
                Constant.REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_CODE && resultCode == RESULT_OK && data != null &&
                data.getData() != null) {

            // Mengambil data foto dan memasukkan ke dalam variable filepath
            filePath = data.getData();

            try {
                // Mengambil data lalu di convert ke bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                // tampilkan gambar yang baru lalu dipilih ke layar
                imgPicture.setImageBitmap(bitmap );
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
