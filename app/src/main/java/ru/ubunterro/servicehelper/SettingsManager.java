package ru.ubunterro.servicehelper;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingsManager {
    public static void setLogin(Context context, String login){
        SharedPreferences settings = context.getSharedPreferences("Settings", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("login", login);
        editor.apply();
    }

    public static String getLogin(Context context){
        SharedPreferences settings = context.getSharedPreferences("Settings", 0);
        return settings.getString("login", "NOCODE");
    }

    public static void setFIO(Context context, String fio){
        SharedPreferences settings = context.getSharedPreferences("Settings", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("fio", fio);
        editor.apply();
    }

    public static String getFIO(Context context){
        SharedPreferences settings = context.getSharedPreferences("Settings", 0);
        return settings.getString("fio", "Repairer");
    }

    public static void setServer(Context context, String server){
        SharedPreferences settings = context.getSharedPreferences("Settings", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("server", server);
        editor.apply();
    }

    public static String getServer(Context context){
        SharedPreferences settings = context.getSharedPreferences("Settings", 0);
        return settings.getString("server", "http://ubunterro.ru/service.php");
    }
}
