package com.letsplay.events;

import org.springframework.context.ApplicationEvent;

public class PlayAcceptEvent extends ApplicationEvent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4292799347118994583L;
	private String notifyPlayer;
	private String gameSessionName;
	
	public PlayAcceptEvent(Object source) {
		super(source);
	}
	
	public String getNotifyPlayer() {
		return notifyPlayer;
	}

	public void setNotifyPlayer(String notifyPlayer) {
		this.notifyPlayer = notifyPlayer;
	}

	public String getGameSessionName() {
		return gameSessionName;
	}

	public void setGameSessionName(String gameSessionName) {
		this.gameSessionName = gameSessionName;
	}
	
}
