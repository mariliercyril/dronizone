package com.scp.dronizone.notification.model.entity.exception;

public class CustomerNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomerNotFoundException(String id) {

		super("Could not find customer " + id + ".");
	}

}
