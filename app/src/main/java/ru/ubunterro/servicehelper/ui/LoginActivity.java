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
        TextInputEditText textEditFio = findViewById(R.id.textEditFIO);
        TextInputEditText textEditServer = findViewById(R.id.textEditServer);
        TextInputEditText textEditServer2 = findViewById(R.id.textEditServer2);
        loginTextEdit.setText(SettingsManager.getLogin(getBaseContext()));
        textEditFio.setText(SettingsManager.getFIO(getBaseContext()));
        textEditServer.setText(SettingsManager.getServer(getBaseContext()));
        textEditServer2.setText(SettingsManager.getServer2(getBaseContext()));

    }

    //Button buttonSetLogin = findViewById(R.id.buttonSaveLogin);


    public void setLogin(View v){
        TextInputEditText loginTextEdit = findViewById(R.id.textEditLogin);
        TextInputEditText textEditFio = findViewById(R.id.textEditFIO);
        TextInputEditText textEditServer = findViewById(R.id.textEditServer);
        TextInputEditText textEditServer2 = findViewById(R.id.textEditServer2);

        String login = loginTextEdit.getText().toString();
        String fio = textEditFio.getText().toString();
        String server = textEditServer.getText().toString();
        String server2 = textEditServer2.getText().toString();

        SettingsManager.setLogin(getApplicationContext(), login);
        SettingsManager.setFIO(getApplicationContext(), fio);
        SettingsManager.setServer(getApplicationContext(), server);
        SettingsManager.setServer2(getApplicationContext(), server2);

        DBAgent.updateBaseUrl();

        Snackbar.make(v, "Сохранено", Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }
}
