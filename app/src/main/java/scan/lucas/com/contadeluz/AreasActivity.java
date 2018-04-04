package scan.lucas.com.contadeluz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import scan.lucas.com.contadeluz.Adapters.AllAreasAdapter;
import scan.lucas.com.contadeluz.DTO.AreaConsumo;

public class AreasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_areas);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvAllAreas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        AllAreasAdapter allAreasAdapter = new AllAreasAdapter(getAreasInformation(), this);
        recyclerView.setAdapter(allAreasAdapter);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent EditarArea = new Intent(view.getContext(), AreaConsumoActivity.class);
                        startActivity(EditarArea);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
    }

    private List<AreaConsumo> getAreasInformation() {

        List<AreaConsumo> areaConsumoList = new ArrayList<>();
        areaConsumoList.add(new AreaConsumo(0,0,
                "Teste 1", "Rural",
                1.1,1.1,1.1,1.1,
                1.1,1.1,1.1,
                1.1,null)
        );
        areaConsumoList.add(new AreaConsumo(0,0,
                "Teste 1", "Rural",
                1.1,1.1,1.1,1.1,
                1.1,1.1,1.1,
                1.1,null)
        );
        areaConsumoList.add(new AreaConsumo(0,0,
                "Teste 1", "Rural",
                1.1,1.1,1.1,1.1,
                1.1,1.1,1.1,
                1.1,null)
        );

        return areaConsumoList;
    }

}
class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);

        public void onLongItemClick(View view, int position);
    }

    public Context mContext;
    GestureDetector mGestureDetector;

    public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnItemClickListener listener) {
        mContext = context;
        mListener = listener;
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null && mListener != null) {
                    mListener.onLongItemClick(child, recyclerView.getChildAdapterPosition(child));
                }
            }
        });
    }

    @Override public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
            mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
            return true;
        }
        return false;
    }

    @Override public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) { }

    @Override
    public void onRequestDisallowInterceptTouchEvent (boolean disallowIntercept){}
}