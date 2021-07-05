package ru.ubunterro.servicehelper.engine;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

import ru.ubunterro.servicehelper.models.Order;
import ru.ubunterro.servicehelper.models.Part;
import ru.ubunterro.servicehelper.models.Repair;

public class Storage {
    public static List<Repair> repairs = new ArrayList<>();
    public static List<Part> parts = new ArrayList<>();
    public static List<Order> orders = new ArrayList<>();

    public static Bitmap uploadingImage;

    public static void setRepairs(List<Repair> repairs) {
        Storage.repairs = repairs;
    }

    public static Repair getRepair(int id){
        for (Repair repair: repairs) {
              if (repair.getId() == id){
                  return repair;
              }
        }

        return new Repair();
    }

    public static List<Repair> getRepairs() {
        return repairs;
    }

    public static List<Part> getParts() {
        return parts;
    }

    public static void setParts(List<Part> parts) {
        Storage.parts = parts;
    }

    public static Part getPart(int id){
        for (Part part: parts) {
            if (part.getId() == id){
                return part;
            }
        }

        return new Part();
    }

    public static List<Order> getOrders() {
        return orders;
    }

    public static void setOrders(List<Order> orders) {
        Storage.orders = orders;
    }

    public static Order getOrder(int id){
        for (Order order: orders) {
            if (order.getId() == id){
                return order;
            }
        }

        return new Order();
    }




}
