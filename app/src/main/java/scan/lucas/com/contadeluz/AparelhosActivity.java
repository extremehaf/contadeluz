package scan.lucas.com.contadeluz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import scan.lucas.com.contadeluz.Adapters.AllAparelhosAdapter;
import scan.lucas.com.contadeluz.Adapters.AllAreasAdapter;
import scan.lucas.com.contadeluz.DTO.Recurso;
import scan.lucas.com.contadeluz.DTO.Usuario;
import scan.lucas.com.contadeluz.Helpers.PreferenceHelper;
import scan.lucas.com.contadeluz.REST.ApiClient;
import scan.lucas.com.contadeluz.REST.Controller;

public class AparelhosActivity extends AppCompatActivity {

    private Usuario mUser;
    private RecyclerView mRecyclerView;
    ProgressDialog mDialog;
    AllAparelhosAdapter allAparelhosAdapter;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aparelhos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent EditarProduto = new Intent(view.getContext(), AparelhoActivity.class);
                EditarProduto.putExtra("recursoId", 0);
                startActivity(EditarProduto);
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //       .setAction("Action", null).show();
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.rvAllAparelhos);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        gson = new Gson();
        SharedPreferences preferences = PreferenceHelper.INSTANCE.defaultPrefs(this);
        String json = preferences.getString("USUARIO", "");
        mUser = gson.fromJson(json, Usuario.class);
        if(getCallingActivity() == null)
            getRecursoInformation(mUser.getId());
    }

    private void getRecursoInformation(int usuarioId) {
        showProgress(true);

        ApiClient controllerApi = Controller.createService(ApiClient.class);
        //vai na API com esse id e traz o cliente

        retrofit2.Call<List<Recurso>> request = controllerApi.RecursosUsuario(usuarioId);
        request.enqueue(new Callback<List<Recurso>>() {
            @Override
            public void onResponse(Call<List<Recurso>> call, Response<List<Recurso>> response) {

                if (response != null && response.isSuccessful()) {
                    List<Recurso> recursos = (List<Recurso>) response.body();

                    allAparelhosAdapter = new AllAparelhosAdapter(recursos, AparelhosActivity.this);
                    mRecyclerView.setAdapter(allAparelhosAdapter);
                }
                showProgress(false);
            }

            @Override
            public void onFailure(Call<List<Recurso>> call, Throwable t) {
                showProgress(false);
            }
        });

    }

    private void showProgress(Boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (show)
            mDialog = ProgressDialog.show(this, "", "Carregando. Por favor Aguarde...", true);
        else if (mDialog != null)
            mDialog.dismiss();
    }

    @Override
    public void onResume(){
        super.onResume();
        //gson = new Gson();
        //SharedPreferences preferences = PreferenceHelper.INSTANCE.defaultPrefs(this);
        //String json = preferences.getString("USUARIO", "");
        //mUser = gson.fromJson(json, Usuario.class);
        //getRecursoInformation(mUser.getId());

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                gson = new Gson();
                SharedPreferences preferences = PreferenceHelper.INSTANCE.defaultPrefs(AparelhosActivity.this);
                String json = preferences.getString("aparelho","");
                Recurso r = gson.fromJson(json, Recurso.class);

                allAparelhosAdapter.userList.add(r);
                int position = allAparelhosAdapter.userList.size() - 1;
                allAparelhosAdapter.notifyItemChanged(position);
            }
        } else if (requestCode == 3) {
            if (resultCode == RESULT_OK) {
                gson = new Gson();
                SharedPreferences preferences = PreferenceHelper.INSTANCE.defaultPrefs(AparelhosActivity.this);
                String json = preferences.getString("aparelho","");
                Integer pos = data.getIntExtra("posicao",0);
                Recurso r = gson.fromJson(json, Recurso.class);

                allAparelhosAdapter.userList.set(pos, r);
                allAparelhosAdapter.notifyItemChanged(pos);
            }

        }
    }
}
