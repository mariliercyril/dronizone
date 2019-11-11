package com.scp.dronizone.notification.model.entity;

import java.text.SimpleDateFormat;

import java.util.Date;

import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The {@code Notification} class represents a <b>notification</b> object.
 * 
 * @author cmarilier
 */
@Document(collection = "notification")
public class Notification  {

	public static final String MESSAGE_HEAD_FORMAT = "Dear %s %s,";

	// Example of date with this format: "2019-10-14 20:05:00"
	private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

	@Id
	private String id;

	@Field("order_id")
	@JsonProperty("order_id")
	private Long orderId;

	private Type type;

	private String date;

	private String reason;

	private String message;

	@SuppressWarnings("unused")
	private Notification() {}

	public Notification(Long orderId, Type type) {

		date = SIMPLE_DATE_FORMAT.format(new Date());

		this.orderId = orderId;
		this.type = type;

		message = new String();
	}

	public Notification(Long orderId, Type type, String reason) {

		this(orderId, type);

		this.reason = reason;
	}

	public String getDate() {

		return date;
	}

	public Long getOrderId() {

		return orderId;
	}

	public Type getType() {

		return type;
	}

	public String getReason() {

		return reason;
	}

	public String getMessage() {

		return message;
	}

	public void setMessage(String message) {

		this.message = message;
	}

	public enum Type {

		WILL_SHORTLY_BE_DELIVERED("the drone delivering your order %s will arrive shortly at the address %s"),
		WILL_FINALLY_NOT_BE_DELIVERED("due to %s, the drone assigned to deliver your order %s is no longer able to fulfill its mission");

		private String messageBodyFormat;

		private Type(String messageBodyFormat) {

			this.messageBodyFormat = messageBodyFormat;
		}

		public String getMessageBodyFormat() {

			return messageBodyFormat;
		}

	}

}
