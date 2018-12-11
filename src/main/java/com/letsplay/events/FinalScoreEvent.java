package com.letsplay.events;

import org.springframework.context.ApplicationEvent;

public class FinalScoreEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8615530851068165650L;
	
	private String winner;
	private int winnerScore;
	
	private String loser;
	private int loserScore;
	
	public FinalScoreEvent(Object source) {
		super(source);
	}

	public String getWinner() {
		return winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}

	public int getWinnerScore() {
		return winnerScore;
	}

	public void setWinnerScore(int winnerScore) {
		this.winnerScore = winnerScore;
	}

	public String getLoser() {
		return loser;
	}

	public void setLoser(String loser) {
		this.loser = loser;
	}

	public int getLoserScore() {
		return loserScore;
	}

	public void setLoserScore(int loserScore) {
		this.loserScore = loserScore;
	}

}
