package com.scp.dronizone.notification.service;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.scp.dronizone.notification.model.entity.Notification;

/**
 * The {@code NotificationService} as an Interface.
 * 
 * @author cmarilier
 */
public interface INotificationService {

	/**
	 * Notifies that an order will shortly be delivered [USER STORY 3].
	 * 
	 * @param orderId
	 * 
	 * @return a {@code Notification} object
	 */
	public ResponseEntity<Notification> notifyThatOrderWillShortlyBeDelivered(@PathVariable String orderId);

	/**
	 * Notifies that an order will finally not be delivered [USER STORY 6].
	 * 
	 * @param reason
	 *  why the order in question will shortly be delivered
	 * @param orderId
	 * 
	 * @return a {@code Notification} object
	 */
	/**
	 * 
	 * @param reason
	 * @param orderId
	 * @return
	 */
	public ResponseEntity<Notification> notifyThatOrderWillFinallyNotBeDelivered(@RequestBody String reason, @PathVariable String orderId);

}
