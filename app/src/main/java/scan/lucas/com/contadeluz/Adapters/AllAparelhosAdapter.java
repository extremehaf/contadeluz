package scan.lucas.com.contadeluz.Adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import scan.lucas.com.contadeluz.AparelhoActivity;
import scan.lucas.com.contadeluz.AparelhosActivity;
import scan.lucas.com.contadeluz.DTO.Recurso;
import scan.lucas.com.contadeluz.Helpers.PreferenceHelper;
import scan.lucas.com.contadeluz.R;
import scan.lucas.com.contadeluz.REST.ApiClient;
import scan.lucas.com.contadeluz.REST.Controller;

/**
 * Created by lucas on 03/04/2018.
 */

public class AllAparelhosAdapter extends RecyclerView.Adapter<AllAparelhosAdapter.AparelhoViewHolder> {

    public List<Recurso> userList;
    ApiClient controllerApi;
    private Context context;
    private ProgressDialog mDialog;
    private int recursoId = 0;

    public AllAparelhosAdapter(List<Recurso> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    public void add(int position, Recurso person) {
        userList.add(position, person);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        userList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public AparelhoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.aparelho_card, null);
        AparelhoViewHolder aparelhoViewHolder = new AparelhoViewHolder(view);
        return aparelhoViewHolder;
    }

    @Override
    public void onBindViewHolder(final AparelhoViewHolder holder, int position) {
        final Recurso recurso = userList.get(position);


        if (recurso.getFoto() != null) {
            Bitmap foto = recurso.retornaFotoBmp();
            holder.foto.setImageBitmap(foto);
        }
        else
            holder.foto.setImageResource(R.drawable.ic_launcher_background);


        holder.potencia.setText(String.valueOf(recurso.getPotencia()) + "w");
        holder.voltagem.setText(recurso.getVoltagem() + "v");
        holder.nome.setText(recurso.getNome());
        holder.descricao.setText(recurso.getDescricao());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUpdateActivity(recurso.getId(), holder.getAdapterPosition());
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(context)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Deseja apagar o aparelho?")
                        .setMessage("essa operação não pode ser desfeita")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                showProgress(true);
                                deletarAparelho(recurso.getId(), holder.getAdapterPosition());
                            }


                        })
                        .setNegativeButton("Cancelar", null)
                        .show();
                return true;
            }
        });


    }

    private void goToUpdateActivity(int Id, int pos) {
        Intent goToUpdate = new Intent(context, AparelhoActivity.class);
        goToUpdate.putExtra("recursoId", Id);
        goToUpdate.putExtra("posicao", pos);
        Gson gson = new Gson();
        String json = gson.toJson(userList.get(pos), Recurso.class);
        SharedPreferences preferences = PreferenceHelper.INSTANCE.defaultPrefs(context);
        preferences.edit()
                .putString("aparelho", json)
                .apply();
        if (context instanceof Activity) {
            ((AparelhosActivity) context).startActivityForResult(goToUpdate, (Id > 0 ? 3 : 2));
        } else {
            context.startActivity(goToUpdate);
        }

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    @Override
    public long getItemId(int position) {
        if (position <= userList.size() && position > 0)
            return userList.get(position).getId();
        else
            return -1;
    }

    private void deletarAparelho(int recursoId, final int position) {
        retrofit2.Call<Void> request;
        controllerApi = Controller.createService(ApiClient.class);
        request = controllerApi.DeleteRecurso(recursoId);

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

    public void removeAt(int position) {
        userList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, userList.size());
    }

    public static class AparelhoViewHolder extends RecyclerView.ViewHolder {

        ImageView foto;
        TextView descricao;
        TextView nome;
        TextView voltagem;
        TextView potencia;

        public AparelhoViewHolder(View itemView) {
            super(itemView);
            foto = (ImageView) itemView.findViewById(R.id.imgAparelho);
            descricao = (TextView) itemView.findViewById(R.id.kwh);
            nome = (TextView) itemView.findViewById(R.id.nome);
            voltagem = (TextView) itemView.findViewById(R.id.voltagem);
            potencia = (TextView) itemView.findViewById(R.id.potencia);
        }

    }
}
