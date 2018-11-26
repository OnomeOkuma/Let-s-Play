package com.letsplay.events;

import org.springframework.context.ApplicationEvent;

public class PlayEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5012835415614390544L;
	private String notifyPlayer;
	
	public PlayEvent(Object source) {
		super(source);
	}

	public String getNotifyPlayer() {
		return notifyPlayer;
	}

	public void setNotifyPlayer(String notifyPlayer) {
		this.notifyPlayer = notifyPlayer;
	}

}
