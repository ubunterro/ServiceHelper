package ru.ubunterro.servicehelper.ui;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import ru.ubunterro.servicehelper.R;
import ru.ubunterro.servicehelper.engine.DBAgent;
import ru.ubunterro.servicehelper.engine.SettingsManager;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextInputEditText loginTextEdit = findViewById(R.id.textEditLogin);
        TextInputEditText textEditPassword = findViewById(R.id.textEditPassword);
        TextInputEditText textEditServer = findViewById(R.id.textEditServer);
        loginTextEdit.setText(SettingsManager.getLogin(getBaseContext()));
        textEditServer.setText(SettingsManager.getServer(getBaseContext()));
        textEditPassword.setText(SettingsManager.getPassword(getBaseContext()));

    }

    // on button press
    public void setLogin(View v){
        TextInputEditText loginTextEdit = findViewById(R.id.textEditLogin);
        TextInputEditText textEditPassword= findViewById(R.id.textEditPassword);
        TextInputEditText textEditServer = findViewById(R.id.textEditServer);

        String login = loginTextEdit.getText().toString();
        String password = textEditPassword.getText().toString();
        String server = textEditServer.getText().toString();


        SettingsManager.setLogin(getApplicationContext(), login);
        SettingsManager.setPassword(getApplicationContext(), password);
        SettingsManager.setServer(getApplicationContext(), server);


        DBAgent.updateBaseUrl();
        DBAgent.auth();

        Snackbar.make(v, "Сохранено", Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }
}
