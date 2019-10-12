package com.scp.dronizone.notification;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The {@code NotificationService} class is the main class from which the <b>notification</b> service is launched.
 * 
 * @author cmarilier
 */
@SpringBootApplication
public class NotificationService {

	public static void main(String[] args) {

		SpringApplication.run(NotificationService.class, args);
	}

}
