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

    public static void setLogin(Context context){
        SharedPreferences settings = context.getSharedPreferences("Settings", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("login", null);
        editor.apply();
    }

    public static void setPassword(Context context, String password){
        SharedPreferences settings = context.getSharedPreferences("Settings", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("password", password);
        editor.apply();
    }

    public static String getLogin(Context context){
        SharedPreferences settings = context.getSharedPreferences("Settings", 0);
        return settings.getString("login", "NOCODE");
    }

    public static String getPassword(Context context){
        SharedPreferences settings = context.getSharedPreferences("Settings", 0);
        return settings.getString("password", "NOPWD");
    }

    @Deprecated
    public static void setFIO(Context context, String fio){
        SharedPreferences settings = context.getSharedPreferences("Settings", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("fio", fio);
        editor.apply();
    }

    @Deprecated
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

    @Deprecated
    public static void setServer2(Context context, String server){
        SharedPreferences settings = context.getSharedPreferences("Settings", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("server2", server);
        editor.apply();
    }

    public static String getServer(Context context){
        SharedPreferences settings = context.getSharedPreferences("Settings", 0);
        return settings.getString("server", "http://192.168.112.75:81/api.php");
    }

    @Deprecated
    public static String getServer2(Context context){
        SharedPreferences settings = context.getSharedPreferences("Settings", 0);
        return settings.getString("server2", "http://188.187.143.80:2081/api.php");
    }
}
