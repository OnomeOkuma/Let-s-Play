package com.letsplay.events;

import org.springframework.context.ApplicationEvent;

public class LogoutSessionEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6341889316840128455L;
	
	private String notifyPlayer;
	
	public LogoutSessionEvent(Object source) {
		super(source);
	}

	public String getNotifyPlayer() {
		return notifyPlayer;
	}

	public void setNotifyPlayer(String notifyPlayer) {
		this.notifyPlayer = notifyPlayer;
	}

}
