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
	private static String host;

	@Value("${server.port}")
	private static String port;

	private static final UriComponentsBuilder URI_COMPONENTS_BUILDER = UriComponentsBuilder.fromHttpUrl("http://" + host + ":" + port);	
	private static final String NOTIFICATION_SERVICE_URI = URI_COMPONENTS_BUILDER.toUriString();

	protected ResponseEntity<String> responseEntity = null;

	@Autowired
	protected RestTemplate restTemplate;

	protected void insertCustomer(Customer customer) {

		if (restTemplate == null) {
			restTemplate = new RestTemplate();
		}

		responseEntity = restTemplate.postForEntity(NOTIFICATION_SERVICE_URI + "/customers", customer, String.class);
	}

	protected void insertOrder(Order order) {

		if (restTemplate == null) {
			restTemplate = new RestTemplate();
		}

		responseEntity = restTemplate.postForEntity(NOTIFICATION_SERVICE_URI + "/orders", order, String.class);
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

		responseEntity = restTemplate.postForEntity(NOTIFICATION_SERVICE_URI + "/notifications", notification, String.class);
	}

}
