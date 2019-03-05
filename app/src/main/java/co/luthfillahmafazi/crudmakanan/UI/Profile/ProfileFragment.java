package co.luthfillahmafazi.crudmakanan.UI.Profile;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
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

import java.lang.reflect.Array;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import co.luthfillahmafazi.crudmakanan.Model.Login.LoginData;
import co.luthfillahmafazi.crudmakanan.R;
import de.hdodenhof.circleimageview.CircleImageView;

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
    // Mengambil data dari array
    private String idUser, name, alamat, noTelp;
    private int gender;
    private Menu action;

    private int mGender = 0;
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
        // Mengambil data yang di kerjakan oleh presenter
        profilePresenter.getData(getContext());
        return view;
    }

    private void setUpSpinner() {
        // Membuat adapter spinner
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(getContext(), R.array.array_gender_options, android.R.layout.simple_spinner_item);
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
                if (!TextUtils.isEmpty(selection)){
                    // Mencek apakah 1 atau 2 yang dipilih user
                    if (selection.equals(getString(R.string.gender_male))){
                        mGender = GENDER_MALE;
                    }else if (selection.equals(getString(R.string.gender_female))){
                        mGender = GENDER_FEMALE;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
        if (loginData.getJenkel().equals("L")){
            gender = 1;
        }else {
            gender = 2;
        }
        if (!TextUtils.isEmpty(idUser)){
            // Menset nama title actionBar
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Profile " + name);

            // Menampilakn data ke layar
            edtName.setText(name);
            edtAlamat.setText(alamat);
            edtNoTelp.setText(noTelp);

            // Mencek gender dan memilik sesuai gender untuk ditampilkan pada spinner
            switch (gender){
                case GENDER_MALE:
                    spinGender.setSelection(1);
                    break;
                case GENDER_FEMALE:
                    spinGender.setSelection(2);
                    break;
            }
        }else {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Profile ");

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_logout)
    public void onViewClicked() {
        // Melakukan perintah logout ke presenter
        profilePresenter.logoutSession(getContext());
        // Menutup mainActivity
        getActivity().finish();

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
                if (TextUtils.isEmpty(idUser)){
                    // Mencek apakah field masih kosong
                    if (TextUtils.isEmpty(edtName.getText().toString()) || TextUtils.isEmpty(edtAlamat.getText().toString())
                            || TextUtils.isEmpty(edtNoTelp.getText().toString())){
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

                    }else {
                        readMode();// Menghilangkan menu edit ketika menu edit di pencet
                        action.findItem(R.id.menu_edit).setVisible(true);
                        // Menampilkan menu save ketika menu edit di pencet
                        action.findItem(R.id.menu_save).setVisible(false);
                    }
                }else {
                    readMode();
                    // Menghilangkan menu edit ketika menu edit di pencet
                    action.findItem(R.id.menu_edit).setVisible(true);
                    // Menampilkan menu save ketika menu edit di pencet
                    action.findItem(R.id.menu_save).setVisible(false);
                }

                readMode();
                return true ;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

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

    private void editMode() {
        // Agar bisa di edit
        edtName.setFocusableInTouchMode(true);
        edtAlamat.setFocusableInTouchMode(true);
        edtNoTelp.setFocusableInTouchMode(true);

        spinGender.setEnabled(true);
        fabChoosePic.setVisibility(View.VISIBLE);


    }
}
