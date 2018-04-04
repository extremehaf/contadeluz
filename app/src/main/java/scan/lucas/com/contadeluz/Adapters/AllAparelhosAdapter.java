package scan.lucas.com.contadeluz.Adapters;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import scan.lucas.com.contadeluz.DTO.Recurso;
import scan.lucas.com.contadeluz.R;

/**
 * Created by lucas on 03/04/2018.
 */

public class AllAparelhosAdapter extends RecyclerView.Adapter<AllAparelhosAdapter.AparelhoViewHolder>{

    private List<Recurso> userList;
    private Context context;
    public AllAparelhosAdapter(List<Recurso> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @Override
    public AparelhoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.aparelho_card, null);
        AparelhoViewHolder aparelhoViewHolder = new AparelhoViewHolder(view);
        return aparelhoViewHolder;
    }

    @Override
    public void onBindViewHolder(AparelhoViewHolder holder, int position) {
        Recurso recurso = userList.get(position);

        Bitmap foto = recurso.retornaFotoBmp();
        if(foto != null)
            holder.foto.setImageBitmap(foto);
        else
            holder.foto.setImageResource(R.drawable.ic_launcher_background);
        holder.potencia.setText(String.valueOf(recurso.getPotencia()));
        holder.voltagem.setText(recurso.getVoltagem());
        holder.nome.setText(recurso.getNome());
        holder.descricao.setText(recurso.getDescricao());
    }

    @Override
    public int getItemCount() {
        return userList.size();
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
