package com.scp.dronizone.entities;
//
//import javax.persistence.Entity;
//import javax.persistence.Id;
import java.util.Objects;
import java.util.UUID;

//@Entity
public class Customer {
//    @Id
    String idCustomer;
    String name;
    String nickname;
    String address;

    public Customer() {
    }

    public Customer(String name, String nickname, String address) {
        this.idCustomer = UUID.randomUUID().toString();
        this.name = name;
        this.nickname = nickname;
        this.address = address;
    }

    public String getIdCustomer() {
        return idCustomer;
    }

    public String getName() {
        return name;
    }

    public String getNickname() {
        return nickname;
    }

    public String getAddress() {
        return address;
    }

    public void setIdCustomer(String idCustomer) {
        this.idCustomer = idCustomer;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return idCustomer.equals(customer.idCustomer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCustomer);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "idCustomer='" + idCustomer + '\'' +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
