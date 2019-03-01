package co.luthfillahmafazi.crudmakanan.UI.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.luthfillahmafazi.crudmakanan.Model.Login.LoginData;
import co.luthfillahmafazi.crudmakanan.R;
import co.luthfillahmafazi.crudmakanan.UI.Main.MainActivity;
import co.luthfillahmafazi.crudmakanan.UI.Register.RegisterActivity;
import co.luthfillahmafazi.crudmakanan.Utils.Constant;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    @BindView(R.id.edtUser)
    EditText edtUser;
    @BindView(R.id.edtPassword)
    EditText edtPassword;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.txtRegister)
    TextView txtRegister;

    private ProgressDialog progressDialog;
    private LoginPresenter loginPresenter = new LoginPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loginPresenter.checkLogin(this);
    }

    @OnClick({R.id.btnLogin, R.id.txtRegister})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                // Mengambil data inputan dari user
                loginPresenter.doLogin(edtUser.getText().toString(), edtPassword.getText().toString());
                break;
            case R.id.txtRegister:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }

    @Override
    public void showProgress() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void hidePorgress() {
        progressDialog.dismiss();
    }

    @Override
    public void loginSucces(String message, LoginData loginData) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        // Menyimpan data ke dalam shared preference
        loginPresenter.saveDataUser(this, loginData);
        // Menggunakan Parcelable
        LoginData mLoginData = new LoginData();
        mLoginData.setId_user(loginData.getId_user());
        mLoginData.setAlamat(loginData.getAlamat());
        mLoginData.setJenkel(loginData.getJenkel());
        mLoginData.setLevel(loginData.getLevel());
        mLoginData.setNama_user(loginData.getNama_user());
        mLoginData.setNo_telp(loginData.getNo_telp());
        mLoginData.setPassword(loginData.getPassword());
        mLoginData.setUsername(loginData.getUsername());

        startActivity(new Intent(this, MainActivity.class).putExtra(Constant.KEY_LOGIN, mLoginData));
        finish();
    }

    @Override
    public void loginFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void usernameError(String message) {
        edtUser.setError(message);
        edtUser.setFocusable(true);
    }

    @Override
    public void passwordError(String message) {
        edtPassword.setError(message);
        edtPassword.setFocusable(true);
    }

    @Override
    public void isLogin() {
        // Berpindah halaman apabila user sudah login
        startActivity(new Intent(this, MainActivity.class));
    }
}
