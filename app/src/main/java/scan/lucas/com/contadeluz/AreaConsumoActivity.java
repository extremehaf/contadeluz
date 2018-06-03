package scan.lucas.com.contadeluz;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import scan.lucas.com.contadeluz.Adapters.AllItemPerfilAdapter;
import scan.lucas.com.contadeluz.Adapters.SpinnerAdapter;
import scan.lucas.com.contadeluz.Adapters.SpinnerProdutosAdapter;
import scan.lucas.com.contadeluz.DTO.ItemData;
import scan.lucas.com.contadeluz.DTO.ItemPerfil;
import scan.lucas.com.contadeluz.DTO.PerfilConsumo;
import scan.lucas.com.contadeluz.DTO.Recurso;
import scan.lucas.com.contadeluz.DTO.Usuario;
import scan.lucas.com.contadeluz.Helpers.PreferenceHelper;
import scan.lucas.com.contadeluz.REST.ApiClient;
import scan.lucas.com.contadeluz.REST.Controller;
import scan.lucas.com.contadeluz.ViewModel.ItemConsumo;

public class AreaConsumoActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    AllItemPerfilAdapter allItemPerfilAdapter;
    private List<Recurso> mRecursos;
    private List<ItemConsumo> itens = new ArrayList<>();
    private int perfilId = 1;
    private ProgressDialog mDialog;
    private Usuario mUser;
    private PerfilConsumo perfilConsumo;
    private ApiClient controllerApi;
    private EditText txtKwh;
    private EditText txtPis;
    private EditText txtCofins;
    private EditText txtIcms;
    private Spinner adicional;
    private double adicionalVal = 0.0;
    private EditText txtDescricao;
    private TextView cDiario;
    private TextView cMensal;
    private TextView valorEstmado;
    private Spinner tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_consumo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvaRecurso();
            }
        });

        Button btnAtualizar = (Button) findViewById(R.id.btnAtualizar);
        btnAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atualizaValores();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddAparelho();
            }
        });
        controllerApi = Controller.createService(ApiClient.class);
        tipo = (Spinner) findViewById(R.id.spinner);
        txtDescricao = (EditText) findViewById(R.id.txtDescricao);
        txtKwh = (EditText) findViewById(R.id.txtKwh);
        txtPis = (EditText) findViewById(R.id.txtPis);
        txtCofins = (EditText) findViewById(R.id.txtCofis);
        txtIcms = (EditText) findViewById(R.id.txtIcms);
        adicional = (Spinner) findViewById(R.id.adicional);
        cDiario = (TextView) findViewById(R.id.cDiario);
        cMensal = (TextView) findViewById(R.id.cMensal);
        valorEstmado = (TextView) findViewById(R.id.valorEstmado);

        List<String> list = new ArrayList<String>();
        list.add("Urbano");
        list.add("Rural");
        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipo.setAdapter(adp1);

        ArrayList<ItemData> listBaneiras = new ArrayList<>();
        listBaneiras.add(new ItemData("Verde", R.mipmap.ic_flag_green256_25053));
        listBaneiras.add(new ItemData("Vermelha", R.mipmap.ic_flag_red256_25052));
        listBaneiras.add(new ItemData("Amarela", R.mipmap.ic_flag_yellow256_25051));

        SpinnerAdapter adapter = new SpinnerAdapter(this, R.layout.row_spinner_produto, R.id.produto, listBaneiras);
        adicional.setAdapter(adapter);

        Gson gson = new Gson();
        SharedPreferences preferences = PreferenceHelper.INSTANCE.defaultPrefs(this);
        String json = preferences.getString("USUARIO", "");
        mUser = gson.fromJson(json, Usuario.class);

        mRecyclerView = (RecyclerView) findViewById(R.id.rvAparelhos);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        //layoutManager.setAutoMeasureEnabled(true);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setNestedScrollingEnabled(true);

        Intent intent = getIntent();
        carregaRecursos();
        perfilId = intent.getIntExtra("perfilId", -1);
        if (perfilId > 0)
            obterDadosPerfil(perfilId);
        else {
            cadastrarArea();
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void AddAparelho() {
        LayoutInflater viewInflated = LayoutInflater.from(this);
        final View dialogView = viewInflated.inflate(R.layout.dialog_area, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(dialogView);

        AlertDialog alertDialog = alertDialogBuilder.create();

        final Spinner aparelho = (Spinner) dialogView.findViewById(R.id.aparelho);
        final EditText txtqnt = (EditText) dialogView.findViewById(R.id.txtqnt);
        final EditText txtDiasUso = (EditText) dialogView.findViewById(R.id.txtDiasUso);
        final EditText txtTempo = (EditText) dialogView.findViewById(R.id.txtTempo);

        ArrayAdapter<Recurso> adp1 = new SpinnerProdutosAdapter(AreaConsumoActivity.this, R.layout.row_spinner_produto, mRecursos);
        aparelho.setAdapter(adp1);

        // Set up the buttons
        final AlertDialog.Builder builder = alertDialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Recurso recurso = (Recurso) aparelho.getSelectedItem();
                ItemPerfil item = new ItemPerfil(0, recurso.getId(),
                        perfilId,
                        Integer.valueOf(txtqnt.getText().toString()),
                        Integer.valueOf(txtDiasUso.getText().toString()),
                        Double.valueOf(txtTempo.getText().toString()));
                item.setRecurso(recurso);
                addItemPerfil(item);
            }
        });
        alertDialogBuilder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialogBuilder.show();
    }

    private void obterDadosPerfil(final int perfilId) {
        showProgress(true);
        controllerApi = Controller.createService(ApiClient.class);
        //vai na API com esse id e traz o cliente

        retrofit2.Call<PerfilConsumo> request = controllerApi.GetPerfilConsumo(perfilId);
        request.enqueue(new Callback<PerfilConsumo>() {
            @Override
            public void onResponse(Call<PerfilConsumo> call, Response<PerfilConsumo> response) {

                if (response != null && response.isSuccessful() && response.body() != null) {
                    perfilConsumo = (PerfilConsumo) response.body();
                    txtDescricao.setText(perfilConsumo.getDescricao());
                    txtKwh.setText(String.valueOf(perfilConsumo.getKwh()));
                    txtPis.setText(String.valueOf(perfilConsumo.getPis()));
                    txtCofins.setText(String.valueOf(perfilConsumo.getCofins()));
                    txtIcms.setText(String.valueOf(perfilConsumo.getIcms()));

                    switch (String.valueOf(perfilConsumo.getAdicional())) {
                        case "0":
                            adicional.setSelection(1);
                            break;
                        case "1":
                            adicional.setSelection(2);
                            break;
                        case "3":
                            adicional.setSelection(3);
                            break;

                    }
                    cDiario.setText(String.format("Consumo diario: %.2f", perfilConsumo.getConsumoDiario()) + " kw");
                    cMensal.setText(String.format("Consumo Mensal: %.2f", perfilConsumo.getConsumoMensal()) + " kw");
                    valorEstmado.setText(String.format("Valor estimado: R$ %.2f", perfilConsumo.getValorEstimado()));
                    allItemPerfilAdapter = new AllItemPerfilAdapter(perfilConsumo.getItemPerfils(), AreaConsumoActivity.this);
                    mRecyclerView.setAdapter(allItemPerfilAdapter);
                }
                showProgress(false);
            }

            @Override
            public void onFailure(Call<PerfilConsumo> call, Throwable t) {

                showProgress(false);
                Toast.makeText(AreaConsumoActivity.this, "Erro ao buscar os dados" + t.getMessage(), Toast.LENGTH_LONG);
            }
        });
    }

    private void addItemPerfil(final ItemPerfil itemPerfil) {
        showProgress(true);
        //vai na API com esse id e traz o cliente
        ItemPerfil item = new ItemPerfil(0,
                itemPerfil.getRecursoId(),
                itemPerfil.getPerfilId(),
                itemPerfil.getQuantidade(),
                itemPerfil.getDiasUso(),
                itemPerfil.getTempo_uso());
        final retrofit2.Call<Integer> request = controllerApi.PostItemPerfil(item);
        request.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {

                if (response != null && response.isSuccessful() && response.body() != null) {
                    itemPerfil.setId((int) response.body());
                    allItemPerfilAdapter.getItemPerfils().add(itemPerfil);
                    allItemPerfilAdapter.notifyItemInserted(allItemPerfilAdapter.getItemPerfils().size() - 1);
                }
                showProgress(false);
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

                showProgress(false);
                Toast.makeText(AreaConsumoActivity.this, "Erro ao buscar os dados" + t.getMessage(), Toast.LENGTH_LONG);
            }
        });
    }

    private void showProgress(Boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (show)
            mDialog = ProgressDialog.show(AreaConsumoActivity.this, "", "Carregando. Por favor Aguarde...", true);
        else
            mDialog.dismiss();
    }

    private void cadastrarArea() {

        LayoutInflater viewInflated = LayoutInflater.from(this);
        final View dialogView1 = viewInflated.inflate(R.layout.dialog_descricao, null);
        AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(this);
        alertDialogBuilder1.setView(dialogView1);

        AlertDialog alertDialog1 = alertDialogBuilder1.create();

        final EditText txtDescricaoModal = (EditText) dialogView1.findViewById(R.id.txtDescricao);

        final AlertDialog.Builder builder = alertDialogBuilder1.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                String descricao = txtDescricaoModal.getText().toString();
                final PerfilConsumo pf = new PerfilConsumo(0,
                        descricao,
                        mUser.getId(),
                        "Urbano",
                        0.0,
                        0.0,
                        0.0,
                        0.0,
                        0.0,
                        0.0,
                        0.0,
                        null);

                showProgress(true);
                retrofit2.Call<Integer> request = controllerApi.PostPerfilConsumo(pf);
                request.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {

                        if (response != null && response.isSuccessful() && response.body() != null) {
                            try {
                                pf.setId(response.body());
                                perfilId = response.body();
                                txtDescricaoModal.setText(pf.getDescricao());
                                txtKwh.setText(String.valueOf(pf.getKwh()));
                                txtPis.setText(String.valueOf(pf.getPis()));
                                txtCofins.setText(String.valueOf(pf.getCofins()));
                                txtIcms.setText(String.valueOf(pf.getIcms()));
                                if (pf.getItemPerfils() == null)
                                    pf.setItemPerfils(new ArrayList<ItemPerfil>());
                                allItemPerfilAdapter = new AllItemPerfilAdapter(pf.getItemPerfils(), AreaConsumoActivity.this);
                                mRecyclerView.setAdapter(allItemPerfilAdapter);
                                perfilConsumo = pf;
                            } catch (Exception e) {
                                Toast.makeText(AreaConsumoActivity.this, "Erro ao buscar os dados" + e.getMessage(), Toast.LENGTH_LONG);
                            }
                        }
                        showProgress(false);
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {

                        showProgress(false);
                        Toast.makeText(AreaConsumoActivity.this, "Erro ao buscar os dados" + t.getMessage(), Toast.LENGTH_LONG);
                    }
                });
            }


        });
        alertDialogBuilder1.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                finish();
            }
        });

        alertDialogBuilder1.show();
    }

    private void carregaRecursos() {
        controllerApi = Controller.createService(ApiClient.class);
        retrofit2.Call<List<Recurso>> request = controllerApi.RecursosUsuarioSimples(mUser.getId());
        request.enqueue(new Callback<List<Recurso>>() {
            @Override
            public void onResponse(Call<List<Recurso>> call, Response<List<Recurso>> response) {

                if (response != null && response.isSuccessful()) {
                    List<Recurso> recursos = (List<Recurso>) response.body();
                    mRecursos = recursos;
                }
            }

            @Override
            public void onFailure(Call<List<Recurso>> call, Throwable t) {
                Toast.makeText(AreaConsumoActivity.this, "Erro ao buscar os dados" + t.getMessage(), Toast.LENGTH_LONG);
            }
        });
    }

    private void salvaRecurso() {
        if (perfilConsumo == null)
            return;
        showProgress(true);
        perfilConsumo.setDescricao(txtDescricao.getText().toString());
        perfilConsumo.setKwh(Double.valueOf(txtKwh.getText().toString()));
        perfilConsumo.setPis(Double.valueOf(txtPis.getText().toString()));
        perfilConsumo.setCofins(Double.valueOf(txtCofins.getText().toString()));
        perfilConsumo.setIcms(Double.valueOf(txtIcms.getText().toString()));

        switch (adicional.getSelectedItem().toString()) {
            case "Vermelha":
                this.adicionalVal = 3;
                break;
            case "Amarela":
                this.adicionalVal = 1;
                break;
            case "Verde":
                this.adicionalVal = 0;
                break;

        }
        perfilConsumo.setAdicional(adicionalVal);
        perfilConsumo.setTipo(tipo.getSelectedItem().toString());
        perfilConsumo.setItemPerfils(null);
        perfilConsumo.setUsuario(null);

        retrofit2.Call<Void> request = controllerApi.PutPerfilConsumo(perfilId, perfilConsumo);
        request.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if (response != null && response.isSuccessful() && response.body() != null) {
                    Toast.makeText(AreaConsumoActivity.this, "Atualizado", Toast.LENGTH_LONG);
                } else
                    Toast.makeText(AreaConsumoActivity.this, "Erro ao buscar os dados", Toast.LENGTH_LONG);
                showProgress(false);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

                showProgress(false);
                Toast.makeText(AreaConsumoActivity.this, "Erro ao buscar os dados" + t.getMessage(), Toast.LENGTH_LONG);
            }
        });

    }

    private void atualizaValores() {
        if (perfilConsumo == null)
            return;
        showProgress(true);
        final retrofit2.Call<PerfilConsumo> request = controllerApi.CalculaFaturaReais(perfilId);
        request.enqueue(new Callback<PerfilConsumo>() {
            @Override
            public void onResponse(Call<PerfilConsumo> call, Response<PerfilConsumo> response) {

                if (response != null && response.isSuccessful() && response.body() != null) {
                    perfilConsumo = response.body();

                    cDiario.setText(String.format("Consumo diario: %.2f", perfilConsumo.getConsumoDiario()) + " kw");
                    cMensal.setText(String.format("Consumo Mensal:  %.2f", perfilConsumo.getConsumoMensal()) + " kw");
                    valorEstmado.setText(String.format("Valor estimado: R$ %.2f", perfilConsumo.getValorEstimado()));
                } else
                    Toast.makeText(AreaConsumoActivity.this, "Erro ao buscar os dados", Toast.LENGTH_LONG);
                showProgress(false);
            }

            @Override
            public void onFailure(Call<PerfilConsumo> call, Throwable t) {

                showProgress(false);
                Toast.makeText(AreaConsumoActivity.this, "Erro ao buscar os dados" + t.getMessage(), Toast.LENGTH_LONG);
            }
        });

    }

}
