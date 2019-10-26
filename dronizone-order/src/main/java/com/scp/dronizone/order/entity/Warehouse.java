package com.scp.dronizone.order.entity;

import com.scp.dronizone.order.states.ProcessingState;

import java.util.ArrayList;
import java.util.List;

public class Warehouse {
    static List<Item> items = new ArrayList<>();

    private Warehouse() {
    }

    public static List<Item> getItems() {
        return items;
    }

    public static void setItems(List<Item> newItems) {
        items = newItems;
    }

    public static void addItem(Item newItem) {
        items.add(newItem);
    }

    public static void resetItemList() {
        items.clear();
    }

}
