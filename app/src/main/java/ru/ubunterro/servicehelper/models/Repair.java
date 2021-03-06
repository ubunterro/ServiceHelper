package ru.ubunterro.servicehelper.models;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

public class Repair {
    public Repair(){

    }

    public Repair(int id, String name, String client, Status status, String description, String recv, String def) {
        this.id = id;
        this.name = name;
        this.client = client;
        this.status = status;
        this.description = description;
        this.recv = recv;
        this.def = def;
    }

    public enum Status{
        @SerializedName("2")
        IN_WORK ,
        @SerializedName("1")
        DONE,
        @SerializedName("3")
        ZIP
    }


    private int id;

    private String name = "";
    private String client = "";
    private Status status = Status.IN_WORK;
    @SerializedName("desc")
    private String description = "";
    private String recv = "";
    private String def = "";


    private String serialNumber = "";

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    private String responsible = "noone";

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

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRecv() {
        return recv;
    }

    public void setRecv(String recv) {
        this.recv = recv;
    }

    public String getDef() {
        return def;
    }

    public void setDef(String def) {
        this.def = def;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }


    // serialization
    public JSONObject toJSONObject(){
        Gson gson = new Gson();
        try {
            return new JSONObject(gson.toJson(this));
        } catch (JSONException e) {
            Log.e("SHLP", "Failed serialization of " + this.toString());
        }

        return new JSONObject();
    }


}
