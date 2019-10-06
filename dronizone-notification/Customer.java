package com.scp.dronizone.notification.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Customer {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

    @Column(name="address", length=40)
	private String address;

    @OneToMany(mappedBy="customer")
    private List<Order> orders;

	public Customer(String address) {

		this.address = address;
	}

	public String getAddress() {

		return address;
	}

}
