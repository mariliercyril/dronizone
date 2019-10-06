package com.scp.dronizone.notification.dao.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

/**
 * The {@code Customer} class implements the <i>customer</i>.
 * 
 * @author cmarilier
 */
@Data
@Entity
public class Customer {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(length=16)
	private String name;

	@Column(length=64)
	private String address;

	@OneToMany(mappedBy="customer")
	private List<CustomerOrder> customerOrders;

	// Creating a customer can not be done without his name.
	@SuppressWarnings("unused")
	private Customer() {}

	public Customer(String name) {

		this.name = name;
		customerOrders = new ArrayList<>();
	}

	public void setAddress(String address) {

		this.address = address;
	}

	public String getAddress() {

		return address;
	}

	public void add(CustomerOrder order) {

		customerOrders.add(order);
	}

}
