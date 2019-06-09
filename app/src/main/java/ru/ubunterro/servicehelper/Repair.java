package ru.ubunterro.servicehelper;

public class Repair {
    public Repair(){

    }

    public Repair(int id, String name, String client, ClientTypes clientType, Status status) {
        this.id = id;
        this.name = name;
        this.client = client;
        this.clientType = clientType;
        this.status = status;
    }

    public enum Status{
        DONE
    }

    public enum ClientTypes{
        ORGANIZATION,
        IP,
        PRIVATE,
        GROUP
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

    public ClientTypes getClientType() {
        return clientType;
    }

    public void setClientType(ClientTypes clientType) {
        this.clientType = clientType;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    private String name = "none";
    private String client = "noone";
    private ClientTypes clientType = ClientTypes.PRIVATE;
    private Status status = Status.DONE;
}
