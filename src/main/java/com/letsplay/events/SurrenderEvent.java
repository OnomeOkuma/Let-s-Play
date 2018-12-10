package com.letsplay.events;

import org.springframework.context.ApplicationEvent;

public class SurrenderEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8641362541434236831L;
	private String winner;
	private String loser;
	public SurrenderEvent(Object source) {
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

}
