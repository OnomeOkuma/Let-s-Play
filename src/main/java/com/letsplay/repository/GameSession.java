package com.letsplay.repository;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.letsplay.logic.Boardstate;
import com.letsplay.logic.PlayChecker;
import com.letsplay.logic.Tilebag;

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
	
	@Column(length=20000)
	private Boardstate boardState;
	
	@Column(length=20000)
	private PlayChecker playChecker;
	
	@Column(length=20000)
	private Tilebag tileBag;
	
	private int firstPlayerTurnsMissed;
	
	private int secondPlayerTurnsMissed;
	
	public GameSession() {
		this.firstPlayerTurnsMissed = 0;
		this.secondPlayerTurnsMissed = 0;
	}
	
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

	public Boardstate getBoardState() {
		return boardState;
	}

	public void setBoardState(Boardstate boardState) {
		this.boardState = boardState;
	}

	public PlayChecker getPlayChecker() {
		return playChecker;
	}

	public void setPlayChecker(PlayChecker playChecker) {
		this.playChecker = playChecker;
	}

	public Tilebag getTileBag() {
		return tileBag;
	}

	public void setTileBag(Tilebag tileBag) {
		this.tileBag = tileBag;
	}

	public int getFirstPlayerTurnsMissed() {
		return firstPlayerTurnsMissed;
	}

	public void setFirstPlayerTurnsMissed(int firstPlayerTurnsMissed) {
		this.firstPlayerTurnsMissed = firstPlayerTurnsMissed;
	}

	public int getSecondPlayerTurnsMissed() {
		return secondPlayerTurnsMissed;
	}

	public void setSecondPlayerTurnsMissed(int secondPlayerTurnsMissed) {
		this.secondPlayerTurnsMissed = secondPlayerTurnsMissed;
	}

}
