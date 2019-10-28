package com.scp.dronizone.notification.controller;

import static org.hamcrest.MatcherAssert.assertThat;

//Method "is(T value)" (is a shortcut to "is(equalTo(T value))")...
//(Crossed out because the method "is(java.lang.Class<T> type)" is deprecated.)
import static org.hamcrest.Matchers.is;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.scp.dronizone.notification.model.entity.Customer;
import com.scp.dronizone.notification.model.entity.Notification;
import com.scp.dronizone.notification.model.entity.Order;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;

/**
 * The {@code WillFinallyNotBeDeliveredStepDefinitions} class defines the Gherkin steps
 * (in the scenarios of the Cucumber <i>feature</i> "Does the Fleet Service notify a customer?"
 * [USERS STORIES 3 & 6]) as Java methods.
 * 
 * @author cmarilier
 */
public class DoesTheFleetServiceNotifyACustomerStepDefinitions extends NotificationControllerTest {

	private Customer customer;
	private Order order;

	@Given("I a customer with an order")
	public void i_a_customer_with_an_order() {}

	@And("^I am \"([\\S]+)\" Alexandre \"([^\"]*)\" and my customer number is equal to (\\d+)$")
	public void i_am_alexandre_and_my_customer_number_is_equal_to(Customer.Gender customerGender, String customerName, Long customerId) throws Throwable {

		customer = new Customer(customerId, customerGender, customerName);
		//insertCustomer(customer);
	}

	@And("^I am ([\\S]+) ([^\"]*) and my customer number is (\\d+)$")
	public void i_am_and_my_customer_number_is(Customer.Gender customerGender, String customerName, Long customerId) throws Throwable {

		customer = new Customer(customerId, customerGender, customerName);
		//insertCustomer(customer);
	}

	@When("^the drone assigned to deliver my order (\\d+) at the address \"([^\"]*)\" is no longer able to fulfill its mission$")
	public void the_drone_assigned_to_deliver_my_order_at_the_address_is_no_longer_able_to_fulfill_its_mission(Long orderId, String deliveryAddress) throws Throwable {

		order = new Order(orderId, customer.getCustomerId(), deliveryAddress);
		//insertOrder(order);
	}

	@When("^the drone delivering my order (\\d+) will arrive shortly at the address ([^\"]*)$")
	public void the_drone_delivering_my_order_will_arrive_shortly_at_the_address(Long orderId, String deliveryAddress) throws Throwable {

		order = new Order(orderId, customer.getCustomerId(), deliveryAddress);
		//insertOrder(order);
	}

	@Then("^due to \"([^\"]*)\" the Fleet Service notifies me that my order (\\d+) will finally not be delivered$")
	public void due_to_the_fleet_service_notifies_me_that_my_order_will_finally_not_be_delivered(String reason, Long orderId) throws Throwable {

		Notification notification = new Notification(orderId, Notification.Type.WILL_FINALLY_NOT_BE_DELIVERED, reason);
		send(notification);

		String notificationMessage = responseEntity.getBody();
		System.out.println(notificationMessage);
		assertTrue(notificationMessage.contains((customer.getGender()).getAbbreviation() + " " + customer.getName()));
	}

	@Then("^the Fleet Service notifies me that my order (\\d+) will shortly be delivered and status code of (\\d+)$")
	public void the_fleet_service_notifies_me_that_my_order_will_shortly_be_delivered_and_status_code_of(Long orderId, Integer statusCode) throws Throwable {

		Notification notification = new Notification(orderId, Notification.Type.WILL_SHORTLY_BE_DELIVERED);
		send(notification);

		String notificationMessage = responseEntity.getBody();
		System.out.println(notificationMessage);
		assertThat("Status code is correct: " + notificationMessage, responseEntity.getStatusCodeValue(), is(statusCode));
	}

}
