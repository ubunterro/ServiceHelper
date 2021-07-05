package ru.ubunterro.servicehelper.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

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
        ImageView imageViewAvatar = findViewById(R.id.imageViewAvatar);

        textViewProfileName.setText(SettingsManager.getName(getBaseContext()));

        int status = SettingsManager.getStatus(getBaseContext());
        String statusExplain = "STATUS UNKNOWN";

        if (status == 1){
            statusExplain = "Сервис-инженер";
        } else if (status == 2){
            statusExplain = "Менеджер";
        }

        Glide.with(getBaseContext()).load("https://sun9-11.userapi.com/impg/52oXnjhq0TCyjuJT4KYIeckD07OWWatShQBK6g/hy1c4qdlOOg.jpg?size=130x174&quality=96&sign=56d33c8f6567b6365a6bffd914ab9199&type=album").into(imageViewAvatar);

        textViewProfileStatus.setText(statusExplain);
        //textViewProfileRepairsCount.setText(SettingsManager.getName(getBaseContext()));
    }

    public void onLogoffButtonPressed(View view){
        SettingsManager.logoff(getBaseContext());

        finish();
    }
}