package com.letsplay.utils;

public class ActivePlayerNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2023859062181374499L;
	
	public ActivePlayerNotFoundException(String message) {
		super(message);
	}
}
