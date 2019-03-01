package co.luthfillahmafazi.crudmakanan.Data.Remote;

import co.luthfillahmafazi.crudmakanan.Model.Login.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    // Membuat api login
    @FormUrlEncoded
    @POST("loginuser.php")
    Call<LoginResponse> loginUser(
            @Field("username") String username,
            @Field("password") String password);

    // Membuat endpoint register
    @FormUrlEncoded
    @POST("registeruser.php")
    Call<LoginResponse> registerUser(@Field("username") String username,
                                     @Field("password") String password,
                                     @Field("namauser") String namauser,
                                     @Field("alamat") String alamat,
                                     @Field("jenkel") String jenkel,
                                     @Field("notelp") String notelp,
                                     @Field("level") String level);

}
