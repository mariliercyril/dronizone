package com.scp.dronizone.notification;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;

import com.scp.dronizone.notification.model.entity.Customer;
import com.scp.dronizone.notification.model.entity.Notification;
import com.scp.dronizone.notification.model.entity.Order;

import com.scp.dronizone.notification.model.repository.CustomerRepository;
import com.scp.dronizone.notification.model.repository.NotificationRepository;
import com.scp.dronizone.notification.model.repository.OrderRepository;

/**
 * The {@code NotificationApplication} class is the class of the main method
 * from which the <b>notification</b> service is run.
 * 
 * @author cmarilier
 */
@SpringBootApplication
public class NotificationApplication {

	public static void main(String[] args) {

		SpringApplication.run(NotificationApplication.class, args);
	}

	/**
	 * For simulating the generation of customers by another service.
	 */
	@Bean
	public CommandLineRunner initializeCustomerRepository(CustomerRepository customerRepository) {

		return (args) -> {
			customerRepository.save(new Customer(0L, Customer.Gender.MRS,  "Kovalevskaïa"));

			customerRepository.save(new Customer(1L, Customer.Gender.MR,  "Grothendieck"));
			customerRepository.save(new Customer(2L, Customer.Gender.MR,  "Abel"));
			customerRepository.save(new Customer(3L, Customer.Gender.MR,  "Galois"));
			customerRepository.save(new Customer(4L, Customer.Gender.MR,  "Riemann"));
		};
	}

	/**
	 * For simulating the generation of orders by another service.
	 */
	@Bean
	public CommandLineRunner initializeOrderRepository(OrderRepository orderRepository) {

		return (args) -> {
			orderRepository.save(new Order(41L, 0L, "sentier de la toupie"));

			orderRepository.save(new Order(86L, 1L, "rue de la cohomologie cristalline"));
			orderRepository.save(new Order(26L, 2L, "Norway street"));
			orderRepository.save(new Order(20L, 3L, "group street"));
			orderRepository.save(new Order(39L, 4L, "surface street"));
		};
	}

	/**
	 * For initializing the {@code NotificationRepository}.
	 */
	@Bean
	public CommandLineRunner initializeNotificationRepository(NotificationRepository notificationRepository) {

		return (args) -> {
			notificationRepository.save(new Notification(41L, Notification.Type.WILL_SHORTLY_BE_DELIVERED));
		};
	}

}
