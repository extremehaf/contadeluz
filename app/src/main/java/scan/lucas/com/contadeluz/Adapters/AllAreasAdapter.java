package scan.lucas.com.contadeluz.Adapters;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import scan.lucas.com.contadeluz.AparelhoActivity;
import scan.lucas.com.contadeluz.AreaConsumoActivity;
import scan.lucas.com.contadeluz.DTO.AreaConsumo;
import scan.lucas.com.contadeluz.DTO.PerfilConsumo;
import scan.lucas.com.contadeluz.R;
import scan.lucas.com.contadeluz.REST.ApiClient;
import scan.lucas.com.contadeluz.REST.Controller;

/**
 * Created by lucas on 03/04/2018.
 */

public class AllAreasAdapter extends RecyclerView.Adapter<AllAreasAdapter.AparelhoViewHolder>{

    private List<PerfilConsumo> userList;
    private Context context;
    private  ProgressDialog mDialog;
    private int mPerfilId = 0;
    ApiClient controllerApi;
    public AllAreasAdapter(List<PerfilConsumo> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @Override
    public AparelhoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.areas_card, null);
        AparelhoViewHolder aparelhoViewHolder = new AparelhoViewHolder(view);
        return aparelhoViewHolder;
    }

    @Override
    public void onBindViewHolder(final AparelhoViewHolder holder, int position) {
        final PerfilConsumo perfilConsumo = userList.get(position);

        holder.nome.setText(perfilConsumo.getDescricao());
        int qnt = 0;
        if(perfilConsumo.getItemPerfils() != null){
            qnt = perfilConsumo.getItemPerfils().size();
        }
        holder.qntAparelhos.setText("Aparelhos: " + String.valueOf(qnt));
        holder.kwh.setText("Kwh R$: " + String.valueOf(perfilConsumo.getKwh()));
        holder.pis.setText("Pis: " + String.valueOf(perfilConsumo.getPis()));
        holder.cofis.setText("Cofis: " + String.valueOf(perfilConsumo.getCofins()));
        holder.add.setText("Adicional bandeira: " + String.valueOf(perfilConsumo.getAdicional()));
        holder.valor.setText("Valor Mensal: " + String.valueOf(perfilConsumo.getValorEstimado()));
        holder.consumoMensal.setText("C. Mensal: " + String.valueOf(perfilConsumo.getConsumoMensal()));
        holder.consumoDiario.setText("C. Diario: " + String.valueOf(perfilConsumo.getConsumoDiario()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUpdateActivity(perfilConsumo.getId());
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(context)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Deseja apagar o perfil?")
                        .setMessage("essa operação não pode ser desfeita")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                showProgress(true);
                                deletarPerfil(perfilConsumo.getId(), holder.getAdapterPosition());
                            }


                        })
                        .setNegativeButton("Cancelar", null)
                        .show();
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class AparelhoViewHolder extends RecyclerView.ViewHolder {

        TextView nome;
        TextView qntAparelhos;
        TextView kwh;
        TextView pis;
        TextView cofis;
        TextView add;
        TextView valor;
        TextView consumoDiario;
        TextView consumoMensal;
        public AparelhoViewHolder(View itemView) {
            super(itemView);
            nome = (TextView) itemView.findViewById(R.id.nome);
            qntAparelhos = (TextView) itemView.findViewById(R.id.qntAparelhos);
            kwh = (TextView) itemView.findViewById(R.id.kwh);
            pis = (TextView) itemView.findViewById(R.id.pis);
            cofis = (TextView) itemView.findViewById(R.id.cofis);
            add = (TextView) itemView.findViewById(R.id.add);
            valor = (TextView) itemView.findViewById(R.id.valor);
            consumoDiario = (TextView) itemView.findViewById(R.id.consumoDiario);
            consumoMensal = (TextView) itemView.findViewById(R.id.consumoMensao);

            int[] presetSizes = new int[]{18,100,2};
            int unit = TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM;
            TextViewCompat.setAutoSizeTextTypeUniformWithPresetSizes(nome,presetSizes,  unit);
            TextViewCompat.setAutoSizeTextTypeUniformWithPresetSizes(qntAparelhos,presetSizes,  unit);
            TextViewCompat.setAutoSizeTextTypeWithDefaults(kwh, unit);
            TextViewCompat.setAutoSizeTextTypeWithDefaults(pis,  unit);
            TextViewCompat.setAutoSizeTextTypeWithDefaults(cofis,  unit);
            TextViewCompat.setAutoSizeTextTypeWithDefaults(add,  unit);
            TextViewCompat.setAutoSizeTextTypeWithDefaults(valor,  unit);
            TextViewCompat.setAutoSizeTextTypeWithDefaults(consumoDiario, unit);
            TextViewCompat.setAutoSizeTextTypeWithDefaults(consumoMensal,  unit);



        }
    }
    private void showProgress(Boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (mDialog != null) {
            if (show)
                mDialog = ProgressDialog.show(context, "", "Carregando. Por favor Aguarde...", true);
            else
                mDialog.dismiss();
        }

    }
    private void goToUpdateActivity(int Id) {
        Intent goToUpdate = new Intent(context, AreaConsumoActivity.class);
        goToUpdate.putExtra("perfilId", Id);
        context.startActivity(goToUpdate);
    }
    private void deletarPerfil(int perfilId, final int position) {
        retrofit2.Call<Void> request;
        controllerApi = Controller.createService(ApiClient.class);
        request = controllerApi.DeletePerfilConsumo(perfilId);

        request.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                showProgress(false);

                if (response != null && response.isSuccessful()) {
                    removeAt(position);
                }
                else
                    Toast.makeText(context, "Erro ao salvar dados, tente novamnente", Toast.LENGTH_SHORT);

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                showProgress(false);
                Toast.makeText(context, "Erro ao salvar dados, tente novamnente", Toast.LENGTH_SHORT);
            }
        });
    }
    public void removeAt(int position) {
        userList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, userList.size());
    }

}
