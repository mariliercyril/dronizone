package com.scp.dronizone.notification.dao.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

/**
 * The {@code CustomerOrder} class implements the <i>order</i> of {@link Customer}.
 * 
 * @author cmarilier
 */
@Data
@Entity
public class CustomerOrder {

	@Id
	private String id;

	@ManyToOne
	private Customer customer;

	// Not only must an order have an ID, but it must also be "created" (constructed) for a customer.
	@SuppressWarnings("unused")
	private CustomerOrder() {}

	public CustomerOrder(String id, Customer customer) {

		this.id = id;

		this.customer = customer;
	}

	public Customer getCustomer() {

		return customer;
	}

}
