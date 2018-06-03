package scan.lucas.com.contadeluz;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import scan.lucas.com.contadeluz.Adapters.AllAreasAdapter;
import scan.lucas.com.contadeluz.DTO.PerfilConsumo;
import scan.lucas.com.contadeluz.DTO.Usuario;
import scan.lucas.com.contadeluz.Helpers.PreferenceHelper;
import scan.lucas.com.contadeluz.REST.ApiClient;
import scan.lucas.com.contadeluz.REST.Controller;

public class AreasActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    private ProgressDialog mDialog;
    private Usuario mUser;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Context mContext = AreasActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_areas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent EditarProduto = new Intent(view.getContext(), AreaConsumoActivity.class);
                EditarProduto.putExtra("perfilId", 0);
                startActivityForResult(EditarProduto, 1);
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //       .setAction("Action", null).show();
            }
        });
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                getAreasInformation(mUser.getId());
            }
        });
        Gson gson = new Gson();
        SharedPreferences preferences = PreferenceHelper.INSTANCE.defaultPrefs(this);
        String json = preferences.getString("USUARIO", "");
        mUser = gson.fromJson(json, Usuario.class);

        mRecyclerView = (RecyclerView) findViewById(R.id.rvAllAreas);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        showProgress(true, "Aguarde....", "Carregando informações");
        getAreasInformation(mUser.getId());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    void onItemsLoadComplete() {
        // Update the adapter and notify data set changed
        // ...

        // Stop refresh animation
        mSwipeRefreshLayout.setRefreshing(false);
    }
    private void getAreasInformation(Integer usuarioId) {

        if (usuarioId > 0) {

            ApiClient controllerApi = Controller.createService(ApiClient.class);
            //vai na API com esse id e traz o cliente

            retrofit2.Call<List<PerfilConsumo>> request = controllerApi.GetPerfisConsumoUsuario(usuarioId);
            request.enqueue(new Callback<List<PerfilConsumo>>() {
                @Override
                public void onResponse(Call<List<PerfilConsumo>> call, Response<List<PerfilConsumo>> response) {

                    if (response != null && response.isSuccessful()) {
                        List<PerfilConsumo> perfilConsumos = (List<PerfilConsumo>) response.body();

                        AllAreasAdapter allAreasAdapter = new AllAreasAdapter(perfilConsumos, mContext);
                        mRecyclerView.setAdapter(allAreasAdapter);
                        onItemsLoadComplete();
                    }
                    showProgress(false);
                }

                @Override
                public void onFailure(Call<List<PerfilConsumo>> call, Throwable t) {
                    showProgress(false);
                }
            });
        }
    }

    private void addArea() {

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

    private void showProgress(Boolean show, String title, String mensagem) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (show)
            mDialog = ProgressDialog.show(this, title, mensagem, true);
        else
            mDialog.dismiss();
    }

}
