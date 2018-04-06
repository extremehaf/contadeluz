package scan.lucas.com.contadeluz.REST;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.Call;
import retrofit2.http.Query;
import scan.lucas.com.contadeluz.DTO.Usuario;

/**
 * Created by lucas on 05/04/2018.
 */
public interface ApiClient {

    // asynchronously with a callback
    @GET("/api/user")
    Usuario getUser(@Query("user_id") int userId, Callback<Usuario> callback);

    // synchronously
    @POST("/api/user/register")
    Usuario registerUser(@Body Usuario user);
}