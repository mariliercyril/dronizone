package com.scp.dronizone.notification.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import com.scp.dronizone.notification.model.entity.Customer;
import com.scp.dronizone.notification.model.entity.Order;

import com.scp.dronizone.notification.model.repository.CustomerRepository;
import com.scp.dronizone.notification.model.repository.OrderRepository;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;

/**
 * The {@code willShortlyBeDeliveredStepDefinitions} class defines the Gherkin steps
 * (in the scenarios of the Cucumber <i>feature</i> "Does the drone delivering our
 * order notify us that it will arrive shortly?" [USER STORY 3]) as Java methods.
 * 
 * @author cmarilier
 */
public class willShortlyBeDeliveredStepDefinitions extends NotificationControllerTest {

	private static final String ROUTE = "/orders/{id}/will-shortly-be-delivered";

	private static String customerId = "0";

	@Autowired private CustomerRepository customerRepository;
	@Autowired private OrderRepository orderRepository;

	@Given("^I am ([\\S]+) ([^\"]*)$")
	public void i_am(Customer.Gender customerGender, String customerName) {

		int id = Integer.parseInt(customerId);

		customer = new Customer(String.valueOf(++id), customerGender, customerName);
		//customerRepository.save(customer);
	}

	@When("^the drone delivering my order (\\d+) will arrive shortly at the address \"([^\"]*)\"$")
	public void the_drone_delivering_my_order_will_arrive_shortly_at_the_address(int orderId, String deliveryAddress) {

		order = new Order(String.valueOf(orderId), customerId, deliveryAddress);
		//orderRepository.save(order);
	}

	@Then("^the Fleet Service notifies me that my order (\\d+) vill shortly be delivered$")
	public void the_Fleet_Service_notifies_me_that_my_order_vill_shortly_be_delivered(int orderId) {

		/*ResponseEntity<?> responseEntity = notifyThatOrderWillShortlyBeDelivered(ROUTE, String.valueOf(orderId));
		Object responseBody = responseEntity.getBody();
		System.out.println(responseBody.toString());*/
	}

}
