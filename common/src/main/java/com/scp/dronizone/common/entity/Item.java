package com.scp.dronizone.common.entity;

import java.util.Objects;
import java.util.UUID;

public class Item {
    String idItem;
    float price;

//    public Item(float price) {
//        this.idItem = UUID.randomUUID().toString();
//        this.price = price;
//    }

    public Item(String idItem) {
        this.idItem = idItem;
    }

    public Item(){}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return idItem.equals(item.idItem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idItem);
    }

    public String getIdItem() {
        return idItem;
    }

    public void setIdItem(String idItem) {
        this.idItem = idItem;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
