package ru.ubunterro.servicehelper;

// предмет на складе
public class WHItem {
    private int itemId = -1;
    private String name = "";
    private String photoURL = "";
    private String serialNumber = "";
    private double amount = -1;
    private String description = "";

    public WHItem(int itemId, String name, String photoURL, String serialNumber, double amount, String description) {
        this.itemId = itemId;
        this.name = name;
        this.photoURL = photoURL;
        this.serialNumber = serialNumber;
        this.amount = amount;
        this.description = description;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
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
