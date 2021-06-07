package ru.ubunterro.servicehelper.models;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

// предмет на складе
public class Part {
    private int id = -1;
    private String name = "";
    private String photo = "";
    @SerializedName("sn")
    private String serialNumber = "";
    private double amount = -1;
    private String description = "";

    public Part(int itemId, String name, String photo, String serialNumber, double amount, String description) {
        this.id = itemId;
        this.name = name;
        this.photo = photo;
        this.serialNumber = serialNumber;
        this.amount = amount;
        this.description = description;
    }

    public Part() {

    }

    public JSONObject toJSONObject(){
        Gson gson = new Gson();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
