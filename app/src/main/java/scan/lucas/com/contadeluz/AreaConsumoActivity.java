package scan.lucas.com.contadeluz;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import scan.lucas.com.contadeluz.DTO.Recurso;
import scan.lucas.com.contadeluz.ViewModel.ItemConsumo;

public class AreaConsumoActivity extends AppCompatActivity {

    private Spinner tipo;
    private List<ItemConsumo> itens = new ArrayList<>();
    private int perfilId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_consumo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               AddAparelho();
            }
        });

        tipo = (Spinner) findViewById(R.id.spinner);
        List<String> list = new ArrayList<String>();
        list.add("Urbano");
        list.add("Rural");

        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipo.setAdapter(adp1);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvAparelhos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void AddAparelho(){
        LayoutInflater viewInflated = LayoutInflater.from(this);
        final View dialogView = viewInflated.inflate(R.layout.dialog_area, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(dialogView);

        AlertDialog alertDialog = alertDialogBuilder.create();

        final Spinner aparelho = (Spinner) alertDialog.findViewById(R.id.aparelho);

        //ArrayAdapter<Recurso> adp1 = new ArrayAdapter<Recurso>(this,
        //        android.R.layout.simple_list_item_1, getRecursoInformation());
        //adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //aparelho.setAdapter(adp1);

        final EditText txtqnt = (EditText) alertDialog.findViewById(R.id.txtqnt);
        final EditText txtDiasUso = (EditText) alertDialog.findViewById(R.id.txtDiasUso);
        final EditText txtTempo = (EditText) alertDialog.findViewById(R.id.txtTempo);

// Set up the buttons
        final AlertDialog.Builder builder = alertDialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                ItemConsumo item = new ItemConsumo(1,
                        perfilId, Integer.valueOf(txtqnt.getText().toString()),
                        Integer.valueOf(txtDiasUso.getText().toString()),
                        Integer.valueOf(txtTempo.getText().toString()),
                        "", "", 100);


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
    private List<Recurso> getRecursoInformation() {

        List<Recurso> userList = new ArrayList<>();
        userList.add(new Recurso("TESTE","A", null, "220",100));
        userList.add(new Recurso("TESTE 1","A", null, "220",100));
        userList.add(new Recurso("TESTE 2","A", null, "220",200));
        userList.add(new Recurso("TESTE 3","A", null, "220",300));
        userList.add(new Recurso("TESTE 4","A", null, "220",400));

        return userList;
    }

}
