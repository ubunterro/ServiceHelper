package ru.ubunterro.servicehelper.models;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Order {

    private int id = -1;
    private String text = "";
    private Date datetime;

    @SerializedName("name")
    private String userOrderedName = "";

    public Order(int id, String text, Date datetime, String userOrderedName) {
        this.id = id;
        this.text = text;
        this.datetime = datetime;
        this.userOrderedName = userOrderedName;
    }

    public Order() {

    }

    public JSONObject toJSONObject(){
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").create();
        try {
            return new JSONObject(gson.toJson(this));
        } catch (JSONException e) {
            Log.e("SHLP", "Failed serialization of " + this.toString());
        }

        return new JSONObject();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDatetime() {
        return datetime;
    }

    public String getLocalizedDatetime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm, E, dd.MM.yy", new Locale("ru", "RU"));
        return simpleDateFormat.format(datetime);
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public void setDatetime(long timestamp) {
        this.datetime = new Date(timestamp);
    }

    public String getUserOrderedName() {
        return userOrderedName;
    }

    public void setUserOrderedName(String userOrderedName) {
        this.userOrderedName = userOrderedName;
    }
}
