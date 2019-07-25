package ru.ubunterro.servicehelper;

import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextInputEditText loginTextEdit = findViewById(R.id.textEditLogin);
        TextInputEditText textEditFio = findViewById(R.id.textEditFIO);
        loginTextEdit.setText(SettingsManager.getLogin(getBaseContext()));
        textEditFio.setText(SettingsManager.getFIO(getBaseContext()));

    }

    //Button buttonSetLogin = findViewById(R.id.buttonSaveLogin);


    public void setLogin(View v){
        TextInputEditText loginTextEdit = findViewById(R.id.textEditLogin);
        TextInputEditText textEditFio = findViewById(R.id.textEditFIO);
        TextInputEditText textEditServer = findViewById(R.id.textEditServer);

        String login = loginTextEdit.getText().toString();
        String fio = textEditFio.getText().toString();
        String server = textEditServer.getText().toString();

        SettingsManager.setLogin(getApplicationContext(), login);
        SettingsManager.setFIO(getApplicationContext(), fio);
        SettingsManager.setServer(getApplicationContext(), server);

        Snackbar.make(v, "Сохранено", Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }
}
