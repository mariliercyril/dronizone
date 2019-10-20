package com.scp.dronizone.notification.model.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The {@code CustomerOrder} class implements the <i>order</i> of {@link Customer}.
 * 
 * @author cmarilier
 */
@Document(collection = "order")
public class Order {

	@Id
	private String _id;

	private String orderId;

	private String customerId;
	private String deliveryAddress;

	private List<Notification> notifications;

	public Order(String orderId, String customerId, String deliveryAddress) {

		this.orderId = orderId;

		this.customerId = customerId;

		notifications = new ArrayList<Notification>();
	}

	public String getOrderId() {

		return orderId;
	}

	public String getCustomerId() {

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
