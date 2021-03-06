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
import ru.ubunterro.servicehelper.engine.SettingsManager;
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

            // ???????????????????? ?????????????? ???????? ????????????
            @Override
            public void onClick(View view) {
                Intent goToSearchActivity = new Intent(getBaseContext(), SearchActivity.class);
                goToSearchActivity.putExtra("id", 123);
                startActivity(goToSearchActivity);
            }
        });

        DBAgent.setContext(getBaseContext());
        DBAgent.initRequestQueue();

        DBAgent.auth();

        DBAgent.setActivity(MainActivity.this);

        //???????????????? ???????????? ???????????????? ?? ??????????????
        DBAgent.getLastRepairs();
        DBAgent.checkForUpdates();

        redrawList();
    }

    // ?????? ???????????? ?????? ????????????????????
    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // ???????????????? ???????????????? ????????????????????
                mSwipeRefreshLayout.setRefreshing(false);
                Log.d("SHLP", "refreshed list");

                Snackbar.make(findViewById(android.R.id.content), "?????????????????? ???????????? ??????????????", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                //???????????????? ???????????? ???????????????? ?? ??????????????
                DBAgent.getLastRepairs();
                DBAgent.checkForUpdates();

            }
        }, 0);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.WHfab);
        //fab.setVisibility(FloatingActionButton.VISIBLE);
    }

    public void redrawList(){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        // ?????????????? ??????????????
        RepairDataAdapter adapter = new RepairDataAdapter(this, Storage.repairs);
        // ?????????????????????????? ?????? ???????????? ??????????????
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(onItemClickListener);
    }

    // ????????????????????, ???????? ?????????????? ???????????????????? ?????? ????????????????????
    public void doUpdate(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("???????????????? ????????????????????")
                .setPositiveButton("????????????????????", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://zip46.ru/servicehelper/app-release.apk"));
                        startActivity(browserIntent);
                    }
                })
                .setNegativeButton("???????????????????? ??????????", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast toast = Toast.makeText(DBAgent.getContext(), "??, ????????????, ????????????????, ?? ???? ?????? ??????... ??????, ??????...", Toast.LENGTH_LONG);
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
        } else if (id == R.id.mainMenuProfileItem){
            Log.d("SHLP", "Opened profile from menu");

            if (!SettingsManager.getName(getBaseContext()).isEmpty()){
                Intent goToProfileActivity = new Intent(this, ProfileActivity.class);
                this.startActivity(goToProfileActivity);
            } else {
                Toast.makeText(getBaseContext(), "???? ???? ?????????? ?? ??????????????!", Toast.LENGTH_LONG).show();
            }
        } else if (id == R.id.mainMenuSettingsItem) {
            Log.d("SHLP", "Opened settings from menu");
            Intent goToSettingsActivity = new Intent(this, SettingsActivity.class);
            this.startActivity(goToSettingsActivity);
        } else if (id == R.id.mainMenuAddRepairItem){
            Log.d("SHLP", "Opened settings from menu");
            Intent goToNewRepairActivity = new Intent(this, EditRepairActivity.class);
            goToNewRepairActivity.putExtra("id", -1); // -1 means new repair
            this.startActivity(goToNewRepairActivity);
        }

        return true;
    }

    public static void showError(String error){
        Toast toast = Toast.makeText(DBAgent.getContext(), error, Toast.LENGTH_LONG);
        toast.show();
    }
}
