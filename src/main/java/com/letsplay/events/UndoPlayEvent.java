package com.letsplay.events;

import java.util.List;

import org.springframework.context.ApplicationEvent;

public class UndoPlayEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2921353301476717325L;
	private String notifyPlayer;
	private List<String> boardTile;
	private List<Integer> column;
	private List<Integer> row;
	
	public UndoPlayEvent(Object source) {
		super(source);
		// TODO Auto-generated constructor stub
	}

	public String getNotifyPlayer() {
		return notifyPlayer;
	}

	public void setNotifyPlayer(String notifyPlayer) {
		this.notifyPlayer = notifyPlayer;
	}

	public List<String> getBoardTile() {
		return boardTile;
	}

	public void setBoardTile(List<String> boardTile) {
		this.boardTile = boardTile;
	}

	public List<Integer> getColumn() {
		return column;
	}

	public void setColumn(List<Integer> column) {
		this.column = column;
	}

	public List<Integer> getRow() {
		return row;
	}

	public void setRow(List<Integer> row) {
		this.row = row;
	}

}
