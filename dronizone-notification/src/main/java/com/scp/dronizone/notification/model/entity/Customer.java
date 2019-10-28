package com.scp.dronizone.notification.model.entity;

import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The {@code Customer} class represents a <b>customer</b> object.
 * 
 * @author cmarilier
 */
@Document(collection = "customer")
public class Customer {

	@Id
	private String id;

	@Field("customer_id")
	@JsonProperty("customer_id")
	private Long customerId;

	private Gender gender;
	private String name;

	@SuppressWarnings("unused")
	private Customer() {}

	public Customer(Long customerId, Gender gender, String name) {

		this.customerId = customerId;

		this.gender = gender;
		this.name = name;
	}

	public Long getCustomerId() {

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
