package scan.lucas.com.contadeluz.REST;

/**
 * Created by lucas on 11/04/2018.
 */

import java.security.PublicKey;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import scan.lucas.com.contadeluz.REST.DateDeserializer;

public class Controller {

    static final String BASE_URL = "https://ti2018.azurewebsites.net/api/";

    static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build();
    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                            .registerTypeAdapter(Date.class, new DateDeserializer())
                            .create()))
                    .client(okHttpClient);

    private static Retrofit retrofit = builder.build();




    public static <T> T createService(Class<T> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
