package com.letsplay.logic;

import java.io.Serializable;

public class BoardPosition implements Comparable<BoardPosition>, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1336445930209590849L;
	public static final int TRIPLE_WORD = 3;
	public static final int DOUBLE_WORD = 2;
	public static final int TRIPLE_LETTER = 3;
	public static final int DOUBLE_LETTER = 2;
	
	private final int column;
	private final int row;
	private Tilestate tile;
	private int letterScore;
	private int wordScore;
	
	public BoardPosition(int column, int row){
		this.column = column;
		this.row = row;	
		this.letterScore = 1;
		this.wordScore = 1;
	}
	
	
	public int getColumn(){
		return this.column;
	}
	
	public int getLetterScore(){
		return this.letterScore;
	}
	
	public int getRow(){
		return this.row;
	}
	
	public Tilestate getTileState(){
		return this.tile;
	}
	
	public int getWordScore(){
		return this.wordScore;
	}
	
	public void setLetterScore(int score) {
		this.letterScore = score;
	}
	
	
	public void setTileState(Tilestate tile){
		this.tile = tile;
	}
	
	public void setWordScore(int score) {
		this.wordScore = score;
	}
	
	@Override
	public boolean equals(Object position){
		
		return this.getColumn() == ((BoardPosition) position).getColumn() && this.getRow() == ((BoardPosition) position).getRow();	
		
	}


	@Override
	public int compareTo(BoardPosition o) throws ClassCastException{
		
		if (this.column == o.column){
			
			if(this.row > o.row)
				return 1;
			else if(this.row == o.row)
				return 0;
			else
				return -1;
			
		}else if (this.row == o.row){
			
			if(this.column > o.column)
				return 1;
			else if(this.column == o.column)
				return 0;
			else
				return -1;
		}
		
		// Thrown to indicate that the object being compared to is not in the same row nor column.
		throw new ClassCastException("Invalid Play");
		
		
	}
	
	@Override 
	public String toString(){
		return ("row = " + this.row + " column = " + this.column + " Letter Score = " + this.letterScore + " Word Score = " + this.wordScore + "\n");
	}
}
