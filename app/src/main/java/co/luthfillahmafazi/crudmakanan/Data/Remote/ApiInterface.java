package co.luthfillahmafazi.crudmakanan.Data.Remote;

import co.luthfillahmafazi.crudmakanan.Model.DetailMakanan.DetailMakananResponse;
import co.luthfillahmafazi.crudmakanan.Model.Makanan.MakananResponse;
import co.luthfillahmafazi.crudmakanan.Model.Login.LoginResponse;
import co.luthfillahmafazi.crudmakanan.Model.Makanan.Upload.UploadMakananRespose;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

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

    // membuat update
    @FormUrlEncoded
    @POST("updateuser.php")
    Call<LoginResponse> updateUser(
            @Field("iduser") int iduser,
            @Field("namauser") String namauser,
            @Field("alamat") String alamat,
            @Field("jenkel") String jenkel,
            @Field("notelp") String notelp);

    @GET("getkategori.php")
    Call<MakananResponse> getKategoryMakanan();

    // Mengambil data makanan baru
    @GET("getmakananbaru.php")
    Call<MakananResponse> getMakananBaru();

    // mengambil data makanan populer
    @GET("getmakananpopuler.php")
    Call<MakananResponse> getMakananPopuler();

    // Mengupload makanan
    @Multipart
    @POST("uploadmakanan.php")
    Call<UploadMakananRespose> uploadMakanan(
            @Part("iduser") int idUser,
            @Part("idkategori") int idkategori,
            @Part("namamakanan") RequestBody namamakanan,
            @Part("descmakanan") RequestBody descmakanan,
            @Part("timeinsert") RequestBody timeinsert,
            @Part MultipartBody.Part image);

    // Mengambil data detail makanan
    @GET("getdetailmakanan.php")
    Call<DetailMakananResponse> getDetailMakanan(@Query("idmakanan") int idmakanan);

    // Mengambil data makanan berdasarkan id category
    @GET("getmakananbykategori.php")
    Call<MakananResponse> getMakananByCategory(@Query("id_kategori") int idkategori);

    // Memanggil data
    @GET("getmakananbyuser.php")
    Call<MakananResponse> getMakananByUser(@Query("id_user") int iduser);

    @FormUrlEncoded
    @POST("deletemakanan.php")
    Call<MakananResponse> deleteMakanan(
            @Field("idmakanan") int idmakanan,
            @Field("fotomakanan") String namaFotoMakanan
    );

    // Menupdate makanan
    @Multipart
    @POST("updatemakanan.php")
    Call<MakananResponse> updateMakanan(
            @Part("idmakanan") int idMakanan,
            @Part("idkategori") int idKategori,
            @Part("namamakanan") RequestBody namamakanan,
            @Part("descmakanan") RequestBody descmakanan,
            @Part("fotomakanan") RequestBody fotomakanan,
            @Part("inserttime") RequestBody inserttime,
            @Part MultipartBody.Part image);
}
