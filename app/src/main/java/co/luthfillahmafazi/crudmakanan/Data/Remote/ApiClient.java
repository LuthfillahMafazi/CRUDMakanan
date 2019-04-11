package co.luthfillahmafazi.crudmakanan.Data.Remote;

import co.luthfillahmafazi.crudmakanan.Utils.Constant;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static Retrofit retrofit = null;

    public static Retrofit getClient(){

        // Membuat object loggingInterceptor
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        // membuat object httpClient
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // Menambahkan inteceptor ke dalam httpClient
        httpClient.addInterceptor(logging);

        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        return retrofit;
    }

}
