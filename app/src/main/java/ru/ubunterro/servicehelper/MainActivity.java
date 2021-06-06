package ru.ubunterro.servicehelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {


    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
            int position = viewHolder.getAdapterPosition();
            int id = RepairsStorage.repairs.get(position).getId();
            
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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
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
                DBAgent.getLastRepairs(true);
                DBAgent.checkForUpdates();

            }
        }, 0);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(FloatingActionButton.VISIBLE);
    }

    public void redrawList(){
        //Log.w("Volley", "redrawn!");
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        // создаем адаптер
        DataAdapter adapter = new DataAdapter(this, RepairsStorage.repairs);
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
            Log.d("actif", "yas");
            Intent goToLoginActivity = new Intent(this, LoginActivity.class);
            this.startActivity(goToLoginActivity);
        }

        return true;
    }

    public static void showError(String error){
        Toast toast = Toast.makeText(DBAgent.getContext(), error, Toast.LENGTH_LONG);
        toast.show();
    }
}
