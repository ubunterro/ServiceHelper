package ru.ubunterro.servicehelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.Objects;

public class RepairDetailsActivity extends AppCompatActivity {


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarDetails);

        setSupportActionBar(toolbar);

        //toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));

        TextView textViewRId = (TextView) findViewById(R.id.textViewRId);

        Intent intent = getIntent();
        int repairId = intent.getExtras().getInt("id");

        Repair r = RepairsStorage.getRepair(repairId);
        textViewRId.setText(Integer.toString(repairId));

        //textViewRId.setText("nibba");

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }
}
