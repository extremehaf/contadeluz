package scan.lucas.com.contadeluz.REST;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import scan.lucas.com.contadeluz.DTO.ItemPerfil;
import scan.lucas.com.contadeluz.DTO.PerfilConsumo;
import scan.lucas.com.contadeluz.DTO.Recurso;
import scan.lucas.com.contadeluz.DTO.RecursoMaiorConsumoViewModel;
import scan.lucas.com.contadeluz.DTO.Usuario;

/**
 * Created by lucas on 05/04/2018.
 */
public interface ApiClient {

    // asynchronously with a callback
    @GET("/api/user")
    Usuario getUser(@Query("user_id") int userId, Callback<Usuario> callback);
    // synchronously
    @POST("/api/Usuario")
    Call<Usuario> registerUser(@Body Usuario user);

    @PUT("/api/Usuario/{id}")
    Call<Usuario> AtualizarUsuario(@Path("id") int id, @Body Usuario user);
    @POST("/api/Usuario/autentica/")
    Call<Usuario> AutenticaUsuario(@Body Usuario usuario);

    @GET("/api/Recurso/usuario/{usuarioId}")
    Call<List<Recurso>> RecursosUsuario(@Path("usuarioId") int usuarioId);

    @GET("/api/Recurso/usuario/{usuarioId}/simples")
    Call<List<Recurso>> RecursosUsuarioSimples(@Path("usuarioId") int usuarioId);
    @GET("/api/Recurso/{id}")
    Call<Recurso> GetRecurso(@Path("id") int recursoId);
    @POST("/api/Recurso")
    Call<Integer> PostRecurso(@Body Recurso value);
    @PUT("/api/Recurso/{id}")
    Call<Integer> PutRecurso(@Path("id") int recursoId, @Body Recurso value);
    @DELETE("/api/Recurso/{id}")
    Call<Void> DeleteRecurso(@Path("id") int recursoId);

    @GET("/api/PerfilConsumo/usuario/{usuarioId}")
    Call<List<PerfilConsumo>> GetPerfisConsumoUsuario(@Path("usuarioId") int usuarioId);
    @GET("/api/PerfilConsumo/{id}")
    Call<PerfilConsumo> GetPerfilConsumo(@Path("id") int perfilId);

    @GET("/api/PerfilConsumo/CalculaFatura/{IdPerfil}")
    Call<PerfilConsumo> CalculaFaturaReais(@Path("IdPerfil") int perfilId);
    @POST("/api/PerfilConsumo")
    Call<Integer> PostPerfilConsumo(@Body PerfilConsumo value);
    @PUT("/api/PerfilConsumo/{id}")
    Call<Void> PutPerfilConsumo(@Path("id") int perfilId, @Body PerfilConsumo value);
    @DELETE("/api/PerfilConsumo/{id}")
    Call<Void> DeletePerfilConsumo(@Path("id") int perfilId);

    @GET("/api/PerfilConsumo/ItemMaiorConsumo/{usuarioId}")
    Call<List<RecursoMaiorConsumoViewModel>> ItemMaiorConsumo(@Path("usuarioId") int usuarioId);

    @GET("/api/ItemPerfil/{id}")
    Call<ItemPerfil> GetItemPerfil(@Path("id") int itemPerfilId);
    @POST("/api/ItemPerfil")
    Call<Integer> PostItemPerfil(@Body ItemPerfil value);
    @PUT("/api/ItemPerfil/{id}")
    Call<Integer> PutItemPerfil(@Path("id") int itemPerfilId, @Body ItemPerfil value);
    @DELETE("/api/ItemPerfil/{id}")
    Call<Void> DeleteItemPerfil(@Path("id") int itemPerfilId);



}