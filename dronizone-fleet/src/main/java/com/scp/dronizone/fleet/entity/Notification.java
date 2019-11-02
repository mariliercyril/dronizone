package com.scp.dronizone.fleet.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Notification {
	// Example of date with this format: "2019-10-14 20:05:00"
	private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

	@JsonProperty("order_id")
	private Long orderId;

	private Type type;

	private String date;

	@SuppressWarnings("unused")
	private Notification() {}

	public Notification(Long orderId, Type type) {

		date = SIMPLE_DATE_FORMAT.format(new Date());

		this.orderId = orderId;
		this.type = type;
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
