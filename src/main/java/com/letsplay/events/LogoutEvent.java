package com.letsplay.events;

import org.springframework.context.ApplicationEvent;

public class LogoutEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8893237340742969788L;
	private String username; 
	
	public LogoutEvent(Object source, String username) {
		super(source);
		this.setUsername(username);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
