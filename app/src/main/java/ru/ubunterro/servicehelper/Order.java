package ru.ubunterro.servicehelper;

import java.sql.Timestamp;
import java.util.Date;

public class Order extends SearchActivity {
    private int orderId = -1;
    private String text = "";
    private Date datetime;
    private String userOrderedName = "";

    public Order(int orderId, String text, long timestamp, String userOrderedName) {
        this.orderId = orderId;
        this.text = text;
        this.datetime = new Date(timestamp);
        this.userOrderedName = userOrderedName;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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
