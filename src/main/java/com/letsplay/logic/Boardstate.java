package com.letsplay.logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.VaadinSessionScope;

@SpringComponent
@VaadinSessionScope
public class Boardstate implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8718727645310343380L;
	/**
	 * 
	 */

	private ArrayList<BoardPosition> emptyPositions;
	private ArrayList<BoardPosition> occupiedPositions;
	
	
	
	public Boardstate(){
		this.emptyPositions = new ArrayList<BoardPosition>(225);
		this.occupiedPositions = new ArrayList<BoardPosition>(110);
	
		for(int column = 0; column < 15; column++)
			for(int row = 0; row < 15; row++) {
				switch (row){
				// Note: Some repetition occurs.
				
				// Creates row 0 of the board.
				case 0:	switch (column){
								case 0: BoardPosition position = new BoardPosition(column, row); position.setWordScore(BoardPosition.TRIPLE_WORD); this.emptyPositions.add(position);break;
								case 3: BoardPosition position1 = new BoardPosition(column, row); position1.setLetterScore(BoardPosition.DOUBLE_LETTER); this.emptyPositions.add(position1);break;
								case 7: BoardPosition position2 = new BoardPosition(column, row); position2.setWordScore(BoardPosition.TRIPLE_WORD); this.emptyPositions.add(position2);break;
								case 11: BoardPosition position3 = new BoardPosition(column, row); position3.setLetterScore(BoardPosition.DOUBLE_LETTER); this.emptyPositions.add(position3);break;
								case 14: BoardPosition position4 = new BoardPosition(column, row); position4.setWordScore(BoardPosition.TRIPLE_WORD); this.emptyPositions.add(position4);break;
								default: BoardPosition position5 = new BoardPosition(column, row); this.emptyPositions.add(position5);break;					
								} break;
								
				case 1: switch (column){
								case 1: BoardPosition position6 = new BoardPosition(column, row); position6.setWordScore(BoardPosition.DOUBLE_WORD); this.emptyPositions.add(position6);break;
								case 5:	BoardPosition position7 = new BoardPosition(column, row); position7.setLetterScore(BoardPosition.TRIPLE_LETTER); this.emptyPositions.add(position7);break;
								case 9: BoardPosition position8 = new BoardPosition(column, row); position8.setLetterScore(BoardPosition.TRIPLE_LETTER); this.emptyPositions.add(position8);break;
								case 13: BoardPosition position9 = new BoardPosition(column, row); position9.setWordScore(BoardPosition.DOUBLE_WORD); this.emptyPositions.add(position9);break;
								default: BoardPosition position10 = new BoardPosition(column, row); this.emptyPositions.add(position10);break;
								} break;
								
				case 2: switch (column){
								case 2: BoardPosition position = new BoardPosition(column, row); position.setWordScore(BoardPosition.DOUBLE_WORD); this.emptyPositions.add(position);break;
								case 6: BoardPosition position2 = new BoardPosition(column, row); position2.setLetterScore(BoardPosition.DOUBLE_LETTER); this.emptyPositions.add(position2);break;
								case 8: BoardPosition position3 = new BoardPosition(column, row); position3.setLetterScore(BoardPosition.DOUBLE_LETTER); this.emptyPositions.add(position3);break;
								case 12: BoardPosition position4 = new BoardPosition(column, row); position4.setWordScore(BoardPosition.DOUBLE_WORD); this.emptyPositions.add(position4);break;
								default: BoardPosition position5 = new BoardPosition(column, row); this.emptyPositions.add(position5);break;
								} break;
				
				case 3: switch (column){
								case 0:  BoardPosition position = new BoardPosition(column, row); position.setLetterScore(BoardPosition.DOUBLE_LETTER); this.emptyPositions.add(position);break;
								case 3:  BoardPosition position2 = new BoardPosition(column, row); position2.setWordScore(BoardPosition.DOUBLE_WORD); this.emptyPositions.add(position2);break;
								case 7:  BoardPosition position3 = new BoardPosition(column, row); position3.setLetterScore(BoardPosition.DOUBLE_LETTER); this.emptyPositions.add(position3);break;
								case 11:  BoardPosition position4 = new BoardPosition(column, row); position4.setWordScore(BoardPosition.DOUBLE_WORD); this.emptyPositions.add(position4);break;
								case 14:  BoardPosition position5 = new BoardPosition(column, row); position5.setLetterScore(BoardPosition.DOUBLE_LETTER); this.emptyPositions.add(position5);break;
								default: BoardPosition position6 = new BoardPosition(column, row); this.emptyPositions.add(position6);break;				
								} break;
								
				case 4: switch (column){
								case 4: BoardPosition position = new BoardPosition(column, row); position.setWordScore(BoardPosition.DOUBLE_WORD); this.emptyPositions.add(position);break;
								case 10: BoardPosition position2 = new BoardPosition(column, row); position2.setWordScore(BoardPosition.DOUBLE_WORD); this.emptyPositions.add(position2);break;
								default: BoardPosition position3 = new BoardPosition(column, row); this.emptyPositions.add(position3);break;
								} break;
								
				case 5: switch (column){
								case 1: BoardPosition position = new BoardPosition(column, row); position.setLetterScore(BoardPosition.TRIPLE_LETTER); this.emptyPositions.add(position);break;
								case 5: BoardPosition position2 = new BoardPosition(column, row); position2.setLetterScore(BoardPosition.TRIPLE_LETTER); this.emptyPositions.add(position2);break;
								case 9: BoardPosition position3 = new BoardPosition(column, row); position3.setLetterScore(BoardPosition.TRIPLE_LETTER); this.emptyPositions.add(position3);break;
								case 13: BoardPosition position4 = new BoardPosition(column, row); position4.setLetterScore(BoardPosition.TRIPLE_LETTER); this.emptyPositions.add(position4);break;
								default: BoardPosition position5 = new BoardPosition(column, row); this.emptyPositions.add(position5);break;
								} break;
								
				case 6: switch (column){
								case 2: BoardPosition position = new BoardPosition(column, row); position.setLetterScore(BoardPosition.DOUBLE_LETTER); this.emptyPositions.add(position);break;
								case 6: BoardPosition position2 = new BoardPosition(column, row); position2.setLetterScore(BoardPosition.DOUBLE_LETTER); this.emptyPositions.add(position2);break;
								case 8: BoardPosition position3 = new BoardPosition(column, row); position3.setLetterScore(BoardPosition.DOUBLE_LETTER); this.emptyPositions.add(position3);break;
								case 12: BoardPosition position4 = new BoardPosition(column, row); position4.setLetterScore(BoardPosition.DOUBLE_LETTER); this.emptyPositions.add(position4);break;
								default: BoardPosition position5 = new BoardPosition(column, row); this.emptyPositions.add(position5);break;
								} break;
								
				case 7: switch (column){
								case 0: BoardPosition position = new BoardPosition(column, row); position.setWordScore(BoardPosition.TRIPLE_WORD); this.emptyPositions.add(position);break;
								case 3: BoardPosition position2 = new BoardPosition(column, row); position2.setLetterScore(BoardPosition.DOUBLE_LETTER); this.emptyPositions.add(position2);break;
								case 7: BoardPosition position3 = new BoardPosition(column, row); position3.setWordScore(BoardPosition.DOUBLE_WORD); this.emptyPositions.add(position3);break;
								case 11:BoardPosition position4 = new BoardPosition(column, row); position4.setLetterScore(BoardPosition.DOUBLE_LETTER); this.emptyPositions.add(position4);break;
								case 14: BoardPosition position5 = new BoardPosition(column, row); position5.setWordScore(BoardPosition.TRIPLE_WORD); this.emptyPositions.add(position5);break;
								default: BoardPosition position6 = new BoardPosition(column, row); this.emptyPositions.add(position6);break;
								} break;
								
				case 8: switch (column){
								case 2: BoardPosition position = new BoardPosition(column, row); position.setLetterScore(BoardPosition.DOUBLE_LETTER); this.emptyPositions.add(position);break;
								case 6: BoardPosition position2 = new BoardPosition(column, row); position2.setLetterScore(BoardPosition.DOUBLE_LETTER); this.emptyPositions.add(position2);break;
								case 8: BoardPosition position3 = new BoardPosition(column, row); position3.setLetterScore(BoardPosition.DOUBLE_LETTER); this.emptyPositions.add(position3);break;
								case 12: BoardPosition position4 = new BoardPosition(column, row); position4.setLetterScore(BoardPosition.DOUBLE_LETTER); this.emptyPositions.add(position4);break;
								default: BoardPosition position5 = new BoardPosition(column, row); this.emptyPositions.add(position5);break;
								} break;
				
				case 9: switch (column){
								case 1: BoardPosition position = new BoardPosition(column, row); position.setLetterScore(BoardPosition.TRIPLE_LETTER); this.emptyPositions.add(position);break;
								case 5: BoardPosition position2 = new BoardPosition(column, row); position2.setLetterScore(BoardPosition.TRIPLE_LETTER); this.emptyPositions.add(position2);break;
								case 9: BoardPosition position3 = new BoardPosition(column, row); position3.setLetterScore(BoardPosition.TRIPLE_LETTER); this.emptyPositions.add(position3);break;
								case 13: BoardPosition position4 = new BoardPosition(column, row); position4.setLetterScore(BoardPosition.TRIPLE_LETTER); this.emptyPositions.add(position4);break;
								default: BoardPosition position5 = new BoardPosition(column, row); this.emptyPositions.add(position5);break;
								} break;
								
				case 10: switch (column){
								case 4: BoardPosition position = new BoardPosition(column, row); position.setWordScore(BoardPosition.DOUBLE_WORD); this.emptyPositions.add(position);break;
								case 10: BoardPosition position2 = new BoardPosition(column, row); position2.setWordScore(BoardPosition.DOUBLE_WORD); this.emptyPositions.add(position2);break;
								default: BoardPosition position3 = new BoardPosition(column, row); this.emptyPositions.add(position3);break;
								} break;
				
				case 11: switch (column){
								case 0:  BoardPosition position = new BoardPosition(column, row); position.setLetterScore(BoardPosition.DOUBLE_LETTER); this.emptyPositions.add(position);break;
								case 3:  BoardPosition position2 = new BoardPosition(column, row); position2.setWordScore(BoardPosition.DOUBLE_WORD); this.emptyPositions.add(position2);break;
								case 7:  BoardPosition position3 = new BoardPosition(column, row); position3.setLetterScore(BoardPosition.DOUBLE_LETTER); this.emptyPositions.add(position3);break;
								case 11:  BoardPosition position4 = new BoardPosition(column, row); position4.setWordScore(BoardPosition.DOUBLE_WORD); this.emptyPositions.add(position4);break;
								case 14:  BoardPosition position5 = new BoardPosition(column, row); position5.setLetterScore(BoardPosition.DOUBLE_LETTER); this.emptyPositions.add(position5);break;
								default: BoardPosition position6 = new BoardPosition(column, row); this.emptyPositions.add(position6);break;				
								} break;
				
								
				case 12: switch (column){
								case 2: BoardPosition position = new BoardPosition(column, row); position.setWordScore(BoardPosition.DOUBLE_WORD); this.emptyPositions.add(position);break;
								case 6: BoardPosition position2 = new BoardPosition(column, row); position2.setLetterScore(BoardPosition.DOUBLE_LETTER); this.emptyPositions.add(position2);break;
								case 8: BoardPosition position3 = new BoardPosition(column, row); position3.setLetterScore(BoardPosition.DOUBLE_LETTER); this.emptyPositions.add(position3);break;
								case 12: BoardPosition position4 = new BoardPosition(column, row); position4.setWordScore(BoardPosition.DOUBLE_WORD); this.emptyPositions.add(position4);break;
								default: BoardPosition position5 = new BoardPosition(column, row); this.emptyPositions.add(position5);break;
								} break;
				
				case 13: switch (column){
								case 1: BoardPosition position6 = new BoardPosition(column, row); position6.setWordScore(BoardPosition.DOUBLE_WORD); this.emptyPositions.add(position6);break;
								case 5:	BoardPosition position7 = new BoardPosition(column, row); position7.setLetterScore(BoardPosition.TRIPLE_LETTER); this.emptyPositions.add(position7);break;
								case 9: BoardPosition position8 = new BoardPosition(column, row); position8.setLetterScore(BoardPosition.TRIPLE_LETTER); this.emptyPositions.add(position8);break;
								case 13: BoardPosition position9 = new BoardPosition(column, row); position9.setWordScore(BoardPosition.DOUBLE_WORD); this.emptyPositions.add(position9);break;
								default: BoardPosition position10 = new BoardPosition(column, row); this.emptyPositions.add(position10);break;
								} break;
								
				case 14: switch (column){
								case 0: BoardPosition position = new BoardPosition(column, row); position.setWordScore(BoardPosition.TRIPLE_WORD); this.emptyPositions.add(position);break;
								case 3: BoardPosition position1 = new BoardPosition(column, row); position1.setLetterScore(BoardPosition.DOUBLE_LETTER); this.emptyPositions.add(position1);break;
								case 7: BoardPosition position2 = new BoardPosition(column, row); position2.setWordScore(BoardPosition.TRIPLE_WORD); this.emptyPositions.add(position2);break;
								case 11: BoardPosition position3 = new BoardPosition(column, row); position3.setLetterScore(BoardPosition.DOUBLE_LETTER); this.emptyPositions.add(position3);break;
								case 14: BoardPosition position4 = new BoardPosition(column, row); position4.setWordScore(BoardPosition.TRIPLE_WORD); this.emptyPositions.add(position4);break;
								default: BoardPosition position5 = new BoardPosition(column, row); this.emptyPositions.add(position5);break;					
					} break;
				}
		
			}
	
	}
	
	public BoardPosition getEmptyPosition(int column, int row){
		
		BoardPosition position = new BoardPosition(column, row);
		BoardPosition temp = null;
		
		Iterator<BoardPosition> iterator = this.emptyPositions.iterator();
		while(iterator.hasNext()) {
			BoardPosition temp2 = iterator.next();
			if (position.equals(temp2))
				temp = temp2;
		}
		
		this.emptyPositions.remove(temp);
		
		return temp;
		
	}
	
	public void setEmptyPosition(int column, int row) {
		BoardPosition position = new BoardPosition(column, row);
		this.emptyPositions.add(position);
	}
	
	public BoardPosition getOccupiedPosition(int column, int row){
		BoardPosition position = new BoardPosition(column, row);
		BoardPosition temp = null;
		
		Iterator<BoardPosition> iterator = this.occupiedPositions.iterator();
		
		while(iterator.hasNext()) {
			BoardPosition temp2 = iterator.next();
			if (position.equals(temp2))
				temp = temp2;
		}
		
		this.emptyPositions.remove(temp);
		
		return temp;
	}
	
	public boolean isOccupied(int column, int row){
		BoardPosition temp = new BoardPosition(column, row);
		
		return this.occupiedPositions.contains(temp);
		
	}
	
	
	public void updateState(List<BoardPosition> postions){
		
		this.occupiedPositions.addAll(postions);

	}
	
}
