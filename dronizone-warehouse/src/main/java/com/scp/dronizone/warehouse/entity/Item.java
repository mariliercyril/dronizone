package com.scp.dronizone.warehouse.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Objects;

@Document(collection = "item")
public class Item {
    @Id
    private String id;

    @Field("item_id")
    int idItem;

    @Field("item_price")
    double price;

//    public Item(float price) {
//        this.idItem = UUID.randomUUID().toString();
//        this.price = price;
//    }

    public Item(int idItem) {
        this.idItem = idItem;
    }

    public Item(int idItem, double price) {
        this.idItem = idItem;
        this.price = price;
    }

    public Item() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return idItem == item.idItem;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idItem);
    }

    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Item{" +
                "idItem='" + idItem + '\'' +
                ", price=" + price +
                '}';
    }
}
