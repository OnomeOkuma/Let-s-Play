package com.letsplay.events;

import org.springframework.context.ApplicationEvent;

public class EndgameEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 206004647016856563L;
	
	private String winner;
	private int winnerScore;
	
	private String loser;
	private int loserScore;
	
	public EndgameEvent(Object source) {
		super(source);
	}

	public String getWinner() {
		return winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}

	public String getLoser() {
		return loser;
	}

	public void setLoser(String loser) {
		this.loser = loser;
	}

	public int getWinnerScore() {
		return winnerScore;
	}

	public void setWinnerScore(int winnerScore) {
		this.winnerScore = winnerScore;
	}

	public int getLoserScore() {
		return loserScore;
	}

	public void setLoserScore(int loserScore) {
		this.loserScore = loserScore;
	}

}
