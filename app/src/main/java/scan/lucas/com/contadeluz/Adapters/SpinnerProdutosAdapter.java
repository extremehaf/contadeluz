package scan.lucas.com.contadeluz.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import scan.lucas.com.contadeluz.DTO.Recurso;
import scan.lucas.com.contadeluz.R;

/**
 * Created by lucas on 22/04/2018.
 */

public class SpinnerProdutosAdapter extends ArrayAdapter {

    private Context mContext;
    private List<Recurso> mRecursos;

    public SpinnerProdutosAdapter(Context context, int textViewResourceId, List<Recurso> objects) {
        super(context, textViewResourceId, objects);
        // TODO Auto-generated constructor stub
        mRecursos = objects;
        mContext = context;
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

// Inflating the layout for the custom Spinner
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View layout = inflater.inflate(R.layout.row_spinner_produto, parent, false);

// Declaring and Typecasting the textview in the inflated layout
        TextView produto = (TextView) layout
                .findViewById(R.id.produto);

// Setting the text using the array
        produto.setText(mRecursos.get(position).getNome());

// Setting the color of the text
        ///tvLanguage.setTextColor(Color.rgb(75, 180, 225));

// Declaring and Typecasting the imageView in the inflated layout
        ImageView img = (ImageView) layout.findViewById(R.id.icon);

// Setting an image using the id's in the array
        img.setImageBitmap(mRecursos.get(position).retornaFotoBmp());
// Setting Special atrributes for 1st element
        if (position == 0) {
// Removing the image view
            //img.setVisibility(View.GONE);
// Setting the size of the text
            produto.setTextSize(20f);
// Setting the text Color
            produto.setTextColor(Color.BLACK);

        }

        return layout;
    }
    @Override
    public int getCount() {
        return mRecursos.size();
    }

    @Override
    public Object getItem(int i) {
        return mRecursos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mRecursos.get(i).getId();
    }
    // It gets a View that displays in the drop down popup the data at the specified position
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    // It gets a View that displays the data at the specified position
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }
}