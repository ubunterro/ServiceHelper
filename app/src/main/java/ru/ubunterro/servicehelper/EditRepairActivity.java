package ru.ubunterro.servicehelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class EditRepairActivity extends AppCompatActivity {

    Repair r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_repair);

        Intent intent = this.getIntent();
        int repairId = intent.getExtras().getInt("id");

        r = RepairsStorage.getRepair(repairId);

        TextView name = findViewById(R.id.textERNameInfo);
        TextView client = findViewById(R.id.textERclient);

        final EditText def = findViewById(R.id.editTextERDeffect);
        final EditText recv = findViewById(R.id.editTextERRecv);
        final EditText info = findViewById(R.id.editTextERinfo);


        name.setText(r.getName());
        client.setText(r.getClient());

        def.setText(r.getDef());
        recv.setText(r.getRecv());
        info.setText(r.getDescription());


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle(Integer.toString(repairId));



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabSaveEdit);
        fab.setOnClickListener(new View.OnClickListener() {

            // обработчик нажатия фаба поиска
            @Override
            public void onClick(View view) {
                //Repair r = new Repair(2, "ass", "Иван Говнов", Repair.Status.DONE, "desc", "recv", "def");

                r.setDef(def.getText().toString());
                r.setRecv(recv.getText().toString());
                r.setDescription(info.getText().toString());

                DBAgent.setRepairInfo(r);

                onBackPressed();
            }
        });

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}