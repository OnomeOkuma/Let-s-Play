package com.letsplay.events;

import org.springframework.context.ApplicationEvent;

public class FirstCalculationEvent extends ApplicationEvent{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 493598747872240933L;
	private int player1Score;
	private String toPlayer;
	private String fromPlayer;
	
	public FirstCalculationEvent(Object source) {
		super(source);
	}
	
	public int getPlayer1Score() {
		return player1Score;
	}
	public void setPlayer1Score(int player1Score) {
		this.player1Score = player1Score;
	}
	public String getToPlayer() {
		return toPlayer;
	}
	public void setToPlayer(String toPlayer) {
		this.toPlayer = toPlayer;
	}

	public String getFromPlayer() {
		return fromPlayer;
	}

	public void setFromPlayer(String fromPlayer) {
		this.fromPlayer = fromPlayer;
	}
}
