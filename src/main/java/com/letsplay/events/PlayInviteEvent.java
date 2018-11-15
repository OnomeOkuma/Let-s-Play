package com.letsplay.events;

import org.springframework.context.ApplicationEvent;

public class PlayInviteEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7631237494887684499L;
	
	private String fromPlayer;
	private String toPlayer;
	
	public PlayInviteEvent(Object source) {
		
		super(source);
		
	}

	public String getFromPlayer() {
		return fromPlayer;
	}

	public void setFromPlayer(String fromPlayer) {
		this.fromPlayer = fromPlayer;
	}

	public String getToPlayer() {
		return toPlayer;
	}

	public void setToPlayer(String toPlayer) {
		this.toPlayer = toPlayer;
	}

}
