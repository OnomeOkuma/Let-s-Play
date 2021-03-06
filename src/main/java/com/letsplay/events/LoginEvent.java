package com.letsplay.events;

import org.springframework.context.ApplicationEvent;

public class LoginEvent extends ApplicationEvent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2696674071296395587L;
	private String username; 
	
	public LoginEvent(Object source, String username) {
		super(source);
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
