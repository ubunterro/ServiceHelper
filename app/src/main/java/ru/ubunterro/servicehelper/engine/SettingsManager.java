package ru.ubunterro.servicehelper.engine;

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

    public static void setName(Context context, String name){
        SharedPreferences settings = context.getSharedPreferences("Settings", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("name", name);
        editor.apply();
    }

    public static int getStatus(Context context){
        SharedPreferences settings = context.getSharedPreferences("Settings", 0);
        return settings.getInt("status", -1);
    }

    public static void setStatus(Context context, int status){
        SharedPreferences settings = context.getSharedPreferences("Settings", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("status", status);
        editor.apply();
    }

    public static String getName(Context context){
        SharedPreferences settings = context.getSharedPreferences("Settings", 0);
        return settings.getString("name", "");
    }

    public static void setServer(Context context, String server){
        SharedPreferences settings = context.getSharedPreferences("Settings", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("server", server);
        editor.apply();
    }


    public static String getServer(Context context){
        SharedPreferences settings = context.getSharedPreferences("Settings", 0);
        return settings.getString("server", "");
    }

    // переход по айди в поиске
    public static void setFastGoto(Context context, boolean state){
        SharedPreferences settings = context.getSharedPreferences("Settings", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("fastGoto", state);
        editor.apply();
    }


    public static boolean getFastGoto(Context context){
        SharedPreferences settings = context.getSharedPreferences("Settings", 0);
        return settings.getBoolean("fastGoto", true);
    }


    public static void logoff(Context context){
        SettingsManager.setPassword(context, "");
        SettingsManager.setLogin(context, "");
        SettingsManager.setName(context, "");

        Storage.repairs.clear();
        Storage.parts.clear();
        Storage.orders.clear();
    }

}
