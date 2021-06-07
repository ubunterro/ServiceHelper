package ru.ubunterro.servicehelper.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import ru.ubunterro.servicehelper.R;
import ru.ubunterro.servicehelper.engine.SettingsManager;
import ru.ubunterro.servicehelper.engine.Storage;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        final TextView textViewProfileName, textViewProfileStatus, textViewProfileRepairsCount;

        textViewProfileName = findViewById(R.id.textViewProfileName);
        textViewProfileStatus = findViewById(R.id.textViewProfileStatus);
        textViewProfileRepairsCount = findViewById(R.id.textViewProfileRepairsCount);

        textViewProfileName.setText(SettingsManager.getName(getBaseContext()));

        int status = SettingsManager.getStatus(getBaseContext());
        String statusExplain = "STATUS UNKNOWN";

        if (status == 1){
            statusExplain = "Сервис-инженер";
        } else if (status == 2){
            statusExplain = "Менеджер";
        }

        textViewProfileStatus.setText(statusExplain);
        //textViewProfileRepairsCount.setText(SettingsManager.getName(getBaseContext()));
    }

    public void onLogoffButtonPressed(View view){
        SettingsManager.logoff(getBaseContext());

        finish();
    }
}