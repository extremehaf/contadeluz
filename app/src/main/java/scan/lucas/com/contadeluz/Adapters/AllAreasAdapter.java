package scan.lucas.com.contadeluz.Adapters;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import scan.lucas.com.contadeluz.DTO.AreaConsumo;
import scan.lucas.com.contadeluz.R;

/**
 * Created by lucas on 03/04/2018.
 */

public class AllAreasAdapter extends RecyclerView.Adapter<AllAreasAdapter.AparelhoViewHolder>{

    private List<AreaConsumo> userList;
    private Context context;
    public AllAreasAdapter(List<AreaConsumo> userList, Context context) {
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
    public void onBindViewHolder(AparelhoViewHolder holder, int position) {
        AreaConsumo recurso = userList.get(position);

        holder.nome.setText(recurso.getNome());
        int qnt = 0;
        if(recurso.getRecursos() != null){
            qnt = recurso.getRecursos().size();
        }
        holder.qntAparelhos.setText(String.valueOf(qnt));
        holder.kwh.setText("Kwh R$: " + String.valueOf(recurso.getKwh()));
        holder.pis.setText("Pis: " + String.valueOf(recurso.getPis()));
        holder.cofis.setText("Cofis: " + String.valueOf(recurso.getCofis()));
        holder.add.setText("Adicional bandeira: " + String.valueOf(recurso.getCofis()));
        holder.valor.setText("Valor Mensal: " + String.valueOf(recurso.getValorEstimado()));
        holder.consumoMensal.setText("C. Mensal: " + String.valueOf(recurso.getConsumoMensal()));
        holder.consumoDiario.setText("C. Diario:" + String.valueOf(recurso.getConsumoDiario()));


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
        }
    }
}
