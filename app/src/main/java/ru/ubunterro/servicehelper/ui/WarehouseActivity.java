package ru.ubunterro.servicehelper.ui;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import ru.ubunterro.servicehelper.R;
import ru.ubunterro.servicehelper.adapter.PartDataAdapter;
import ru.ubunterro.servicehelper.engine.DBAgent;
import ru.ubunterro.servicehelper.models.Part;

import static ru.ubunterro.servicehelper.engine.Storage.parts;

public class WarehouseActivity extends AppCompatActivity {

    /*
    FloatingActionButton fab = findViewById(R.id.WHfab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
       */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehouse);

        DBAgent.setWarehouseActivity((WarehouseActivity.this));

        DBAgent.getLastParts();
        Log.d("SHLP", "created parts activity" );

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbarWarehouse);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Склад");

        FloatingActionButton fab = findViewById(R.id.WHfab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
            int position = viewHolder.getAdapterPosition();
            int id = parts.get(position).getId();

            Intent goToRepairDetailsActivity = new Intent(getBaseContext(), RepairDetailsActivity.class);
            goToRepairDetailsActivity.putExtra("id", id);
            startActivity(goToRepairDetailsActivity);
        }
    };

    public void redrawList(List<Part> parts){
        Log.w("SHLP", "redrawn parts list");
        Log.w("SHLP", parts.get(0).getDescription());
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.partsPartsList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.supportsPredictiveItemAnimations();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setClickable(true);
        recyclerView.setHasFixedSize(true);
        // создаем адаптер
        PartDataAdapter adapter = new PartDataAdapter(this, parts);
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(onItemClickListener);
    }
}