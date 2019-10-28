package com.scp.dronizone.notification;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;

import com.scp.dronizone.notification.model.entity.Customer;
import com.scp.dronizone.notification.model.entity.Order;

import com.scp.dronizone.notification.model.repository.CustomerRepository;
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

	@Bean
	public CommandLineRunner initializeCustomerRepository(CustomerRepository customerRepository) {

		return (args) -> {
			customerRepository.save(new Customer(0L, Customer.Gender.MRS,  "Kovalevskaïa"));
		};
	}

	@Bean
	public CommandLineRunner initializeOrderRepository(OrderRepository orderRepository) {

		return (args) -> {
			orderRepository.save(new Order(41L, 0L, "sentier de la toupie"));
		};
	}

}
