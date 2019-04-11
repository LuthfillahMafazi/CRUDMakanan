package co.luthfillahmafazi.crudmakanan.UI.Profile;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.Array;
import java.security.Permission;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import co.luthfillahmafazi.crudmakanan.Model.Login.LoginData;
import co.luthfillahmafazi.crudmakanan.R;
import co.luthfillahmafazi.crudmakanan.Utils.Constant;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements ProfileContract.View {


    @BindView(R.id.picture)
    CircleImageView picture;
    @BindView(R.id.fabChoosePic)
    FloatingActionButton fabChoosePic;
    @BindView(R.id.layoutPicture)
    RelativeLayout layoutPicture;
    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_alamat)
    EditText edtAlamat;
    @BindView(R.id.edt_no_telp)
    EditText edtNoTelp;
    @BindView(R.id.spin_gender)
    Spinner spinGender;
    @BindView(R.id.layoutProfil)
    CardView layoutProfil;
    @BindView(R.id.btn_logout)
    Button btnLogout;
    @BindView(R.id.layoutJenkel)
    CardView layoutJenkel;
    Unbinder unbinder;

    // Siapkan variable yang dibutuhkan
    ProfilePresenter profilePresenter = new ProfilePresenter(this);
    private ProgressDialog progressDialog;
    // Mengambil data dari array
    private String idUser, name, alamat, noTelp;
    private int gender;
    private Menu action;

    // Uri untuk memasukan gambar berdasarkan storage gambar dmana kita simpan di hp
    private Uri filePath;


    private String mGender;
    public static final int GENDER_MALE = 1;
    public static final int GENDER_FEMALE = 2;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        // menampilkan option menu di fragment
        setHasOptionsMenu(true);
        // Mensetting spinner
        setUpSpinner();
        PermissionGallery();

        // Mengambil data yang di kerjakan oleh presenter
        profilePresenter.getData(getContext());
        return view;
    }

    private void PermissionGallery() {
        // Mencek apakah user sudah memberikan permission untuk mengakses external storage
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Constant.STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constant.STORAGE_PERMISSION_CODE) {
            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                showMessage("Permission granted now you can read the storage");
                Log.i("Permission on", "onRequestPermissionsResult: " + String.valueOf(grantResults));
            } else {
                //Displaying another toast if permission is not granted
                showMessage("Oops you just denied the permission");
                Log.i("Permission off", "onRequestPermissionsResult: " + String.valueOf(grantResults));

            }
        }

    }

    private void setUpSpinner() {
        // Membuat adapter spinner
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.array_gender_options, android.R.layout.simple_spinner_item);
        // Menampilkan spinner 1line
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinGender.setAdapter(genderSpinnerAdapter);

        // Listener Spinner
        spinGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Mengambil posisi item yang dipilih
                // Mengambil posisi dari parent AdapterView
                String selection = (String) parent.getItemAtPosition(position);
                // Mengecek posisi apakah ada isinya
                if (!TextUtils.isEmpty(selection)) {
                    // Mencek apakah 1 atau 2 yang dipilih user
                    if (selection.equals(getString(R.string.gender_male))) {
                        mGender = "L";
                    } else if (selection.equals(getString(R.string.gender_female))) {
                        mGender = "P";
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void showProgress() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Saving...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSuccessUpdateUser(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        // Mengambil data ulang di sharedPeference
        profilePresenter.getData(getContext());
    }

    @Override
    public void showDataUser(LoginData loginData) {
        readMode();
        // Memasukan data yang sudah diambil oleh presenter
        idUser = loginData.getId_user();
        name = loginData.getNama_user();
        alamat = loginData.getAlamat();
        noTelp = loginData.getNo_telp();
        // Menagmbil data jenkel dan di confert menjadi int
        if (loginData.getJenkel().equals("L")) {
            gender = 1;
        } else {
            gender = 2;
        }
        if (!TextUtils.isEmpty(idUser)) {
            // Menset nama title actionBar
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Profile " + name);

            // Menampilakn data ke layar
            edtName.setText(name);
            edtAlamat.setText(alamat);
            edtNoTelp.setText(noTelp);

            // Mencek gender dan memilik sesuai gender untuk ditampilkan pada spinner
            switch (gender) {
                case GENDER_MALE:
                    spinGender.setSelection(0);
                    break;
                case GENDER_FEMALE:
                    spinGender.setSelection(1);
                    break;
            }
        } else {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Profile ");

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // mencek apakah request code dan data ada
        if (requestCode == Constant.REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null){
            // Mengambil data image yand sudah dipilih user
            filePath = data.getData();

            try {
                // Mengubah file image pilihan user menjadi bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), filePath);
                // Menampiljan gambar preview yang di pilih
                picture.setImageBitmap(bitmap);

            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }


    @OnClick(R.id.btn_logout)
    public void onViewClicked() {
        // Melakukan perintah logout ke presenter
        profilePresenter.logoutSession(getContext());
        // Menutup mainActivity
        getActivity().finish();

        fabChoosePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }

            private void showFileChooser() {
                Intent intentGalery = new Intent(Intent.ACTION_PICK);
                intentGalery.setType("image/");
                startActivityForResult(Intent.createChooser(intentGalery, "Select pictures"), Constant.REQUEST_CODE);
            }
        });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_editor, menu);
        action = menu;
        // menghilangkan menu save pada saat pertama kali
        action.findItem(R.id.menu_save).setVisible(false);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_edit:
                editMode();

                // Menghilangkan menu edit ketika menu edit di pencet
                action.findItem(R.id.menu_edit).setVisible(false);
                // Menampilkan menu save ketika menu edit di pencet
                action.findItem(R.id.menu_save).setVisible(true);
                return true;
            case R.id.menu_save:
                // Mencek id user apakah masih ada isinya
                if (!TextUtils.isEmpty(idUser)) {
                    // Mencek apakah field masih kosong
                    if (TextUtils.isEmpty(edtName.getText().toString()) || TextUtils.isEmpty(edtAlamat.getText().toString())
                            || TextUtils.isEmpty(edtNoTelp.getText().toString())) {
                        // Menampilkan alert dialog untuk memberitahu user agar tidak boleh ada yang kosong
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                        alertDialog.setMessage("Please complite the field !");
                        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alertDialog.show();

                    } else {
                        // apabila user sudah mengisi semua field
                        LoginData loginData = new LoginData();
                        // Mengisi inputan user ke mocel loginData
                        loginData.setId_user(idUser);
                        loginData.setNama_user(edtName.getText().toString());
                        loginData.setAlamat(edtAlamat.getText().toString());
                        loginData.setNo_telp(edtNoTelp.getText().toString());
                        loginData.setJenkel(mGender);

                        // Mengirim data ke presenter untuk di masukan ke dalam database
                        profilePresenter.updateDataUser(getContext(), loginData);

                        readMode();// Menghilangkan menu edit ketika menu edit di pencet
                        action.findItem(R.id.menu_edit).setVisible(true);
                        // Menampilkan menu save ketika menu edit di pencet
                        action.findItem(R.id.menu_save).setVisible(false);
                    }
                } else {
                    readMode();
                    // Menghilangkan menu edit ketika menu edit di pencet
                    action.findItem(R.id.menu_edit).setVisible(true);
                    // Menampilkan menu save ketika menu edit di pencet
                    action.findItem(R.id.menu_save).setVisible(false);
                }

                readMode();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @SuppressLint("RestrictedApi")
    private void readMode() {
        edtName.setFocusableInTouchMode(false);
        edtAlamat.setFocusableInTouchMode(false);
        edtNoTelp.setFocusableInTouchMode(false);

        edtName.setFocusable(false);
        edtNoTelp.setFocusable(false);
        edtAlamat.setFocusable(false);

        spinGender.setEnabled(false);
        fabChoosePic.setVisibility(View.INVISIBLE);


    }

    @SuppressLint("RestrictedApi")
    private void editMode() {
        // Agar bisa di edit
        edtName.setFocusableInTouchMode(true);
        edtAlamat.setFocusableInTouchMode(true);
        edtNoTelp.setFocusableInTouchMode(true);

        spinGender.setEnabled(true);
        fabChoosePic.setVisibility(View.VISIBLE);


    }

}
