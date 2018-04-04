package scan.lucas.com.contadeluz;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import scan.lucas.com.contadeluz.Adapters.AllAparelhosAdapter;
import scan.lucas.com.contadeluz.DTO.Recurso;

public class AparelhosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aparelhos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvAllAparelhos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        AllAparelhosAdapter allAparelhosAdapter = new AllAparelhosAdapter(getRecursoInformation(), this);
        recyclerView.setAdapter(allAparelhosAdapter);
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
