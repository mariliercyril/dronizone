package com.scp.dronizone.notification.model.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The {@code Customer} class implements the <i>customer</i>.
 * 
 * @author cmarilier
 */
@Document(collection = "customer")
public class Customer {

	@Id
	private String id;

	private String customerId;

	private Gender gender;
	private String name;

	private List<Order> orders;

	public Customer(String customerId, Gender gender, String name) {

		this.customerId = customerId;

		this.gender = gender;
		this.name = name;

		orders = new ArrayList<Order>();
	}

	public String getCustomerId() {

		return customerId;
	}

	public Gender getGender() {

		return gender;
	}

	public void setGender(Gender gender) {

		this.gender = gender;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public List<Order> getOrders() {

		return orders;
	}

	public void addOrder(Order order) {

		orders.add(order);
	}

	public enum Gender {

		MR("Mr"),
		MRS("Mrs");

		private String abbreviation;

		private Gender(String abbreviation) {

			this.abbreviation = abbreviation;
		}

		public String getAbbreviation() {

			return abbreviation;
		}

	}

}
