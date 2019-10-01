package com.scp.dronizone.common.entity;

import java.util.ArrayList;
import java.util.List;

public class Warehouse {
    List<Item> items;

    public Warehouse() {
        items = new ArrayList<>();
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Order createOrder(String itemId) {
        Order newOrder = new Order();
        for (Item item : items) {
            if (item.getIdItem().equals(itemId)) {
                newOrder.addItem(item);
            }
        }

        return newOrder;
    }

    public void addItem(Item newItem) {
        this.items.add(newItem);
    }

}
