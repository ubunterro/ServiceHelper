package ru.ubunterro.servicehelper.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import ru.ubunterro.servicehelper.R;
import ru.ubunterro.servicehelper.adapter.RepairDataAdapter;
import ru.ubunterro.servicehelper.engine.DBAgent;
import ru.ubunterro.servicehelper.engine.Storage;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {


    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
            int position = viewHolder.getAdapterPosition();
            int id = Storage.repairs.get(position).getId();
            
            Intent goToRepairDetailsActivity = new Intent(getBaseContext(), RepairDetailsActivity.class);
            goToRepairDetailsActivity.putExtra("id", id);
            startActivity(goToRepairDetailsActivity);
        }
    };

    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarDetails);
        setSupportActionBar(toolbar);


        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setProgressViewOffset(false,100,150);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.WHfab);
        fab.setOnClickListener(new View.OnClickListener() {

            // обработчик нажатия фаба поиска
            @Override
            public void onClick(View view) {
                Intent goToSearchActivity = new Intent(getBaseContext(), SearchActivity.class);
                goToSearchActivity.putExtra("id", 123);
                startActivity(goToSearchActivity);
            }
        });


        DBAgent.setContext(getBaseContext());
        DBAgent.initRequestQueue();

        //RepairsStorage.repairs.add(new Repair(666, "canon lexmark laserJet", "Putin", Repair.ClientTypes.IP, Repair.Status.DONE));
        //RepairsStorage.repairs.add(new Repair(1488, "mobila", "IFNS", Repair.ClientTypes.IP, Repair.Status.DONE));

        redrawList();
    }

    // при свайпе для обновления
    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Отменяем анимацию обновления
                mSwipeRefreshLayout.setRefreshing(false);
                Log.d("Volley", "obnova");

                Snackbar.make(findViewById(android.R.id.content), "Обновляем список заказов", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                DBAgent.setActivity(MainActivity.this);

                //получаем список ремонтов с сервера
                DBAgent.getLastRepairs();
                DBAgent.checkForUpdates();

            }
        }, 0);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.WHfab);
        //fab.setVisibility(FloatingActionButton.VISIBLE);
    }

    public void redrawList(){
        //Log.w("Volley", "redrawn!");
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        // создаем адаптер
        RepairDataAdapter adapter = new RepairDataAdapter(this, Storage.repairs);
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(onItemClickListener);
    }

    // показываем, если имеется обновление для приложения
    public void doUpdate(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Доступно обновление")
                .setPositiveButton("Установить", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://zip46.ru/servicehelper/app-release.apk"));
                        startActivity(browserIntent);
                    }
                })
                .setNegativeButton("Установить позже", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast toast = Toast.makeText(DBAgent.getContext(), "Я, значит, старался, а вы вот как... Зря, зря...", Toast.LENGTH_LONG);
                        toast.show();
                    }
                });
        // Create the AlertDialog object and return it
        builder.create().show();


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
        if (id == R.id.loginMenuItem) {
            Log.d("SHLP", "Opened login from menu");
            Intent goToLoginActivity = new Intent(this, LoginActivity.class);
            this.startActivity(goToLoginActivity);
        } else if (id == R.id.mainMenuOrderItem){
            Log.d("SHLP", "Opened orders from menu");
            Intent goToOrdersActivity = new Intent(this, OrdersActivity.class);
            this.startActivity(goToOrdersActivity);
        } else if (id == R.id.mainMenuWHItem) {
            Log.d("SHLP", "Opened warehouse from menu");
            Intent goToWHActivity = new Intent(this, WarehouseActivity.class);
            this.startActivity(goToWHActivity);
        }

        return true;
    }

    public static void showError(String error){
        Toast toast = Toast.makeText(DBAgent.getContext(), error, Toast.LENGTH_LONG);
        toast.show();
    }
}
