package ru.ubunterro.servicehelper;

import java.util.ArrayList;
import java.util.List;

public class RepairsStorage {
    public static void setRepairs(List<Repair> repairs) {
        RepairsStorage.repairs = repairs;
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

    public static List<Repair> repairs = new ArrayList<>();
}
