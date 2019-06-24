package ru.ubunterro.servicehelper;

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
        IN_WORK,
        DONE
    }


    private int id;

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

    private String name = "none";
    private String client = "noone";
    private Status status = Status.DONE;
    private String description = "";

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


    private String recv = "";
    private String def = "";
}
