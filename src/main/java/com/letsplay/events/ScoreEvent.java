package com.letsplay.events;

import org.springframework.context.ApplicationEvent;

public class ScoreEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4910281504642131792L;
	private String notifyPlayer;
	private int Score;
	
	public ScoreEvent(Object source) {
		super(source);
		// TODO Auto-generated constructor stub
	}

	public String getNotifyPlayer() {
		return notifyPlayer;
	}

	public void setNotifyPlayer(String notifyPlayer) {
		this.notifyPlayer = notifyPlayer;
	}

	public int getScore() {
		return Score;
	}

	public void setScore(int score) {
		Score = score;
	}

}
