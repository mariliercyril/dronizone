package com.scp.dronizone.entities;

//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
import java.util.Objects;

//@Entity
public class Item {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    String idItem;


    float price;

//    public Item(float price) {
//        this.idItem = UUID.randomUUID().toString();
//        this.price = price;
//    }

    public Item(String idItem) {
        this.idItem = idItem;
    }

    public Item() {
    }

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

    @Override
    public String toString() {
        return "Item{" +
                "idItem='" + idItem + '\'' +
                ", price=" + price +
                '}';
    }
}
