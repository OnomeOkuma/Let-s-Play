package com.letsplay.repository;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class GameSession {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@OneToOne
	private ActivePlayer player1;
	
	@OneToOne
	private ActivePlayer player2;
	
	@OneToMany
	private List<ActivePlayer> spectators;
	
	private String name;
	
	public ActivePlayer getPlayer1() {
		return player1;
	}

	public void setPlayer1(ActivePlayer player1) {
		this.player1 = player1;
	}

	public ActivePlayer getPlayer2() {
		return player2;
	}

	public void setPlayer2(ActivePlayer player2) {
		this.player2 = player2;
	}

	public List<ActivePlayer> getSpectators() {
		return spectators;
	}

	public void setSpectators(List<ActivePlayer> spectators) {
		this.spectators = spectators;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
