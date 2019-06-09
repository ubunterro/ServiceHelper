package ru.ubunterro.servicehelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
            int position = viewHolder.getAdapterPosition();
            int id = RepairsStorage.repairs.get(position).getId();
            Toast.makeText(MainActivity.this, Integer.toString(id), Toast.LENGTH_SHORT).show();


            Intent goToRepairDetailsActivity = new Intent(getBaseContext(), RepairDetailsActivity.class);
            goToRepairDetailsActivity.putExtra("id", id);
            startActivity(goToRepairDetailsActivity);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarDetails);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Обновляем список заказов", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                DBAgent.setActivity(MainActivity.this);
                DBAgent.getLastRepairs();
            }
        });

        DBAgent.context = getBaseContext();
        DBAgent.initRequestQueue();

        RepairsStorage.repairs.add(new Repair(666, "canon lexmark laserJet", "Putin", Repair.ClientTypes.IP, Repair.Status.DONE));
        RepairsStorage.repairs.add(new Repair(1488, "mobila", "IFNS", Repair.ClientTypes.IP, Repair.Status.DONE));
        //RepairsStorage.repairs.add(new Repair(228, "utug", "Agropromservis", Repair.ClientTypes.IP, Repair.Status.DONE));
        redrawList();


    }

    public void redrawList(){
        Log.w("Volley", "redrawn!");
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        // создаем адаптер
        DataAdapter adapter = new DataAdapter(this, RepairsStorage.repairs);
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(onItemClickListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
