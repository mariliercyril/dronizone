package com.scp.dronizone.notification.model.entity;

import java.text.SimpleDateFormat;

import java.util.Date;

import lombok.Getter;

@Getter
public class Notification  {

	public static final String MESSAGE_HEAD_FORMAT = "Dear %s %s,";

	// Example of date with this format: "2019-10-14T20:05:00Z"
	private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-ddThh:mm:ssZ");

	@SuppressWarnings("unused")
	private String date;
	@SuppressWarnings("unused")
	private String message;

	public Notification(String message) {

		date = SIMPLE_DATE_FORMAT.format(new Date());

		this.message = message;
	}

	public enum Type {

		WILL_SHORTLY_BE_DELIVERED("the drone delivering your order %s will arrive shortly at the address %s"),
		WILL_FINALLY_NOT_BE_DELIVERED("due to %s, the drone assigned to deliver your order %s is no longer able to fulfill its mission");

		private String messageBodyFormat;

		private Type(String messageBodyFormat) {

			this.messageBodyFormat = messageBodyFormat;
		}

		public String getMessageFormat() {

			return messageBodyFormat;
		}

	}

}
