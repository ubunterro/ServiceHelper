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
    boolean isNewRepair = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_repair);

        Intent intent = this.getIntent();
        final int repairId = intent.getExtras().getInt("id");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (repairId == -1){
            isNewRepair = true;
            r = new Repair();
            getSupportActionBar().setTitle("Новый ремонт");
        } else {
            isNewRepair = false;
            r = Storage.getRepair(repairId);
            getSupportActionBar().setTitle(Integer.toString(repairId));
        }

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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabSaveEdit);
        fab.setOnClickListener(new View.OnClickListener() {

            // обработчик нажатия фаба сохранения
            @Override
            public void onClick(View view) {


                r.setDef(def.getText().toString());
                r.setRecv(recv.getText().toString());
                r.setDescription(info.getText().toString());


                if (isNewRepair){
                    DBAgent.createRepair(r);
                } else {
                    DBAgent.setRepairInfo(r);
                }


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