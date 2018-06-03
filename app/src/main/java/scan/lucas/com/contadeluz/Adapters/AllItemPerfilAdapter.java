package scan.lucas.com.contadeluz.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import scan.lucas.com.contadeluz.DTO.ItemPerfil;
import scan.lucas.com.contadeluz.R;
import scan.lucas.com.contadeluz.REST.ApiClient;
import scan.lucas.com.contadeluz.REST.Controller;

/**
 * Created by lucas on 03/04/2018.
 */

public class AllItemPerfilAdapter extends RecyclerView.Adapter<AllItemPerfilAdapter.AparelhoViewHolder> {

    ApiClient controllerApi;
    private List<ItemPerfil> itemPerfils;
    private Context context;
    private ProgressDialog mDialog;
    private int mPerfilId = 0;

    public AllItemPerfilAdapter(List<ItemPerfil> itemPerfils, Context context) {
        this.setItemPerfils(itemPerfils);
        this.context = context;
    }

    @Override
    public AparelhoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.consumo_card, null);
        AparelhoViewHolder aparelhoViewHolder = new AparelhoViewHolder(view);
        return aparelhoViewHolder;
    }

    @Override
    public void onBindViewHolder(final AparelhoViewHolder holder, int position) {
        final ItemPerfil itemPerfil = getItemPerfils().get(position);

        holder.nome.setText(itemPerfil.getRecurso().getNome());
        holder.potencia.setText(String.valueOf(itemPerfil.getRecurso().getPotencia()) + "w");
        holder.dias.setText(String.valueOf(itemPerfil.getDiasUso()) + " Dias ");
        holder.horas.setText(String.valueOf(itemPerfil.getTempo_uso() + " Horas"));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUpdateActivity(itemPerfil.getId());
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(context)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Deseja apagar esse item do perfil?")
                        .setMessage("essa operação não pode ser desfeita")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                showProgress(true);
                                deletarPerfil(itemPerfil.getId(), holder.getAdapterPosition());
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
        return getItemPerfils().size();
    }

    public List<ItemPerfil> getItemPerfils() {
        return itemPerfils;
    }

    public void setItemPerfils(List<ItemPerfil> itemPerfils) {
        this.itemPerfils = itemPerfils;
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

    }

    private void deletarPerfil(int perfilId, final int position) {
        Call<Void> request;
        controllerApi = Controller.createService(ApiClient.class);
        request = controllerApi.DeleteItemPerfil(perfilId);

        request.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                showProgress(false);

                if (response != null && response.isSuccessful()) {
                    removeAt(position);
                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                showProgress(false);
                Toast.makeText(context, "Erro ao salvar dados, tente novamnente", Toast.LENGTH_SHORT);
            }
        });
    }

    public void removeAt(int position) {
        getItemPerfils().remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemPerfils().size());
    }

    public static class AparelhoViewHolder extends RecyclerView.ViewHolder {

        TextView nome;
        TextView potencia;
        TextView dias;
        TextView horas;

        public AparelhoViewHolder(View itemView) {
            super(itemView);
            nome = (TextView) itemView.findViewById(R.id.nome);
            potencia = (TextView) itemView.findViewById(R.id.potencia);
            dias = (TextView) itemView.findViewById(R.id.dias);
            horas = (TextView) itemView.findViewById(R.id.horas);


            int[] presetSizes = new int[]{18, 100, 2};
            int unit = TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM;
            TextViewCompat.setAutoSizeTextTypeWithDefaults(nome, unit);
            TextViewCompat.setAutoSizeTextTypeWithDefaults(potencia, unit);
            TextViewCompat.setAutoSizeTextTypeWithDefaults(dias, unit);
            TextViewCompat.setAutoSizeTextTypeWithDefaults(horas, unit);


        }
    }

}
