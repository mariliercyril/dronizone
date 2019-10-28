package com.scp.dronizone.notification.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.ResponseEntity;

import org.springframework.web.client.RestTemplate;

import com.scp.dronizone.notification.NotificationApplication;

import com.scp.dronizone.notification.model.entity.Customer;
import com.scp.dronizone.notification.model.entity.Notification;
import com.scp.dronizone.notification.model.entity.Order;

/**
 * The {@code NotificationControllerTest} class allows to test the <b>notification</b> service.
 * 
 * @author cmarilier
 */
@SpringBootTest(classes = NotificationApplication.class)
public class NotificationControllerTest {

	private static final String NOTIFICATION_SERVER_URL = "http://localhost:9003";

	protected ResponseEntity<String> responseEntity = null;

	@Autowired
	protected RestTemplate restTemplate;

	protected void post(Customer customer) {

		if (restTemplate == null) {
			restTemplate = new RestTemplate();
		}

		responseEntity = restTemplate.postForEntity(NOTIFICATION_SERVER_URL + "/customers", customer, String.class);
	}

	protected void post(Order order) {

		if (restTemplate == null) {
			restTemplate = new RestTemplate();
		}

		responseEntity = restTemplate.postForEntity(NOTIFICATION_SERVER_URL + "/orders", order, String.class);
	}

	/**
	 * Tests the consumption of the notification service...
	 * 
	 * @param orderId
	 * @param notificationType
	 */
	protected void send(Notification notification) {

		if (restTemplate == null) {
			restTemplate = new RestTemplate();
		}

		responseEntity = restTemplate.postForEntity(NOTIFICATION_SERVER_URL + "/notifications", notification, String.class);
	}

}
