package scan.lucas.com.contadeluz.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import scan.lucas.com.contadeluz.DTO.RecursoMaiorConsumoViewModel;
import scan.lucas.com.contadeluz.R;
import scan.lucas.com.contadeluz.REST.ApiClient;

/**
 * Created by lucas on 03/04/2018.
 */

public class AllMaisCarosPerfilAdapter extends RecyclerView.Adapter<AllMaisCarosPerfilAdapter.AparelhoViewHolder> {

    ApiClient controllerApi;
    private List<RecursoMaiorConsumoViewModel> recursoMaiorConsumoViewModels;
    private Context context;
    private ProgressDialog mDialog;
    private int mPerfilId = 0;

    public AllMaisCarosPerfilAdapter(List<RecursoMaiorConsumoViewModel> recursoMaiorConsumoViewModels, Context context) {
        this.setRecursoMaiorConsumoViewModels(recursoMaiorConsumoViewModels);
        this.context = context;
    }

    @Override
    public AparelhoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mais_caros_card, null);
        AparelhoViewHolder aparelhoViewHolder = new AparelhoViewHolder(view);
        return aparelhoViewHolder;
    }

    @Override
    public void onBindViewHolder(final AparelhoViewHolder holder, int position) {
        final RecursoMaiorConsumoViewModel recursoMaiorConsumoViewModel = getRecursoMaiorConsumoViewModels().get(position);

        holder.nomePerfil.setText(recursoMaiorConsumoViewModel.getNomePerfil());
        holder.nomeAparelho.setText(recursoMaiorConsumoViewModel.getNomeRecurs());

        String valorKw = String.format(" %.2f", recursoMaiorConsumoViewModel.getKwhConsumo()) + " Kwh";
        String valorR = String.format("R$ %.2f", recursoMaiorConsumoViewModel.getValorConsumo());
        holder.txtValorKwh.setText(valorR + " / " + valorKw);


    }

    @Override
    public int getItemCount() {
        return getRecursoMaiorConsumoViewModels().size();
    }

    public List<RecursoMaiorConsumoViewModel> getRecursoMaiorConsumoViewModels() {
        return recursoMaiorConsumoViewModels;
    }

    public void setRecursoMaiorConsumoViewModels(List<RecursoMaiorConsumoViewModel> recursoMaiorConsumoViewModels) {
        this.recursoMaiorConsumoViewModels = recursoMaiorConsumoViewModels;
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

    public static class AparelhoViewHolder extends RecyclerView.ViewHolder {

        TextView nomePerfil;
        TextView nomeAparelho;
        TextView txtValorKwh;

        public AparelhoViewHolder(View itemView) {
            super(itemView);
            nomePerfil = (TextView) itemView.findViewById(R.id.nomePerfil);
            nomeAparelho = (TextView) itemView.findViewById(R.id.nomeAparelho);
            txtValorKwh = (TextView) itemView.findViewById(R.id.txtValorKwh);


            int[] presetSizes = new int[]{18, 100, 2};
            int unit = TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM;
            TextViewCompat.setAutoSizeTextTypeWithDefaults(txtValorKwh, unit);
            TextViewCompat.setAutoSizeTextTypeWithDefaults(nomePerfil, unit);
            TextViewCompat.setAutoSizeTextTypeWithDefaults(nomeAparelho, unit);


        }
    }

}
