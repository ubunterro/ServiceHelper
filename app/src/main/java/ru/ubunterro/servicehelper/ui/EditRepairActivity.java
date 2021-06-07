package ru.ubunterro.servicehelper.ui;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.navigation.ui.AppBarConfiguration;

import ru.ubunterro.servicehelper.R;
import ru.ubunterro.servicehelper.engine.DBAgent;
import ru.ubunterro.servicehelper.engine.Storage;
import ru.ubunterro.servicehelper.models.Repair;

public class EditRepairActivity extends AppCompatActivity {

    Repair r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_repair);

        Intent intent = this.getIntent();
        int repairId = intent.getExtras().getInt("id");

        r = Storage.getRepair(repairId);

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