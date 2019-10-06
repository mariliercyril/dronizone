package com.scp.dronizone.notification;

import java.util.HashMap;
import java.util.Map;

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

		SpringApplication client = new SpringApplication(NotificationService.class);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("server.port", 9003);
		client.setDefaultProperties(map);

		client.run(args);
	}

}
