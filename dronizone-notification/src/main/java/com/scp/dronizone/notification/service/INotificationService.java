package com.scp.dronizone.notification.service;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestBody;

import com.scp.dronizone.notification.model.entity.Notification;

/**
 * The {@code NotificationService} as an Interface...
 * 
 * @author cmarilier
 */
public interface INotificationService {

	/**
	 * Sends a <b>notification</b> message [USER STORIES 3 & 6].
	 * 
	 * @param notification
	 *  a {@code Notification} object
	 * 
	 * @return
	 *  a {@code ResponseEntity} with message (as a {@code String}) for its body, if CREATED
	 */
	public ResponseEntity<String> send(@RequestBody Notification notification);

}
