package ru.ubunterro.servicehelper.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;

import ru.ubunterro.servicehelper.R;
import ru.ubunterro.servicehelper.engine.SettingsManager;

public class SettingsActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        Switch gotoSwitch = findViewById(R.id.switchSettingsFastGoto);


        if (gotoSwitch != null) {
            gotoSwitch.setOnCheckedChangeListener(this);
        }

        gotoSwitch.setChecked(SettingsManager.getFastGoto(getApplicationContext()));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Настройки");
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        SettingsManager.setFastGoto(getApplicationContext(), isChecked);
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

}