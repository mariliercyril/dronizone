package com.scp.dronizone.notification.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The {@code NotificationController} class allows (a drone) to notify a customer.
 * 
 * @author cmarilier
 */
@RestController
@RequestMapping("/drone/delivery/notification")
public class NotificationController {

	/**
	 * Notifies an arrival message [USER STORY 3].
	 * 
	 * @param message
	 * 
	 * @return HTTP response
	 */
	@PostMapping(value="/arrival")
	public ResponseEntity<String> notifyArrival(@RequestBody String message) {

		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/**
	 * Notifies an abandonment message [USER STORY 6].
	 * 
	 * @param message
	 * 
	 * @return HTTP response
	 */
	@PostMapping(value="/abandonment")
	public ResponseEntity<String> notifyAbandonment(@RequestBody String message) {

		return new ResponseEntity<String>(HttpStatus.OK);
	}

}
