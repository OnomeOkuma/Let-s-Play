package com.letsplay.events;

import org.springframework.context.ApplicationEvent;

public class SecondCalculationEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -779638077852599171L;
	
	private int player2Score;
	private String toPlayer;
	
	public SecondCalculationEvent(Object source) {
		super(source);
	}

	public int getPlayer2Score() {
		return player2Score;
	}

	public void setPlayer2Score(int player2Score) {
		this.player2Score = player2Score;
	}

	public String getToPlayer() {
		return toPlayer;
	}

	public void setToPlayer(String toPlayer) {
		this.toPlayer = toPlayer;
	}
	
}
