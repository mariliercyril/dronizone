package com.scp.dronizone.notification.model.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The {@code Order} class represents an <b>order</b> object.
 * 
 * @author cmarilier
 */
@Document(collection = "order")
public class Order {

	@Id
	private String id;

	@Field("order_id")
	@JsonProperty("order_id")
	private Long orderId;

	@Field("customer_id")
	@JsonProperty("customer_id")
	private Long customerId;

	@Field("delivery_address")
	@JsonProperty("delivery_address")
	private String deliveryAddress;

	private List<Notification> notifications;

	@SuppressWarnings("unused")
	private Order() {}

	public Order(Long orderId, Long customerId, String deliveryAddress) {

		this.orderId = orderId;

		this.customerId = customerId;

		this.deliveryAddress = deliveryAddress;

		notifications = new ArrayList<Notification>();
	}

	public Long getOrderId() {

		return orderId;
	}

	public Long getCustomerId() {

		return customerId;
	}

	public String getDeliveryAddress() {

		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {

		this.deliveryAddress = deliveryAddress;
	}

	public List<Notification> getNotifications() {

		return notifications;
	}

	public void addNotification(Notification notification) {

		notifications.add(notification);
	}

}
