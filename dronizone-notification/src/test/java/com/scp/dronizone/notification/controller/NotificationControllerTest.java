package com.scp.dronizone.notification.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.ResponseEntity;

import org.springframework.web.client.RestTemplate;

import org.springframework.web.util.UriComponentsBuilder;

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

	@Value("${server.host}")
	private String host;

	@Value("${server.port}")
	private String port;

	private UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl("http://" + host + ":" + port);	
	private String notificationServiceUri = uriComponentsBuilder.toUriString();
	//
	private String uri = "http://localhost:9003";

	protected ResponseEntity<String> responseEntity = null;

	@Autowired
	protected RestTemplate restTemplate;

	protected void insertCustomer(Customer customer) {

		if (restTemplate == null) {
			restTemplate = new RestTemplate();
		}

		responseEntity = restTemplate.postForEntity(uri + "/customers", customer, String.class);
	}

	protected void insertOrder(Order order) {

		if (restTemplate == null) {
			restTemplate = new RestTemplate();
		}

		responseEntity = restTemplate.postForEntity(uri + "/orders", order, String.class);
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

		responseEntity = restTemplate.postForEntity(uri + "/notifications", notification, String.class);
	}

}
