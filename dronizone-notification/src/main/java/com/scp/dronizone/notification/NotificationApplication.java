package com.scp.dronizone.notification;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;

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

}
