package com.letsplay.logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.TreeMap;

import org.quinto.dawg.CompressedDAWGSet;
import org.springframework.beans.factory.annotation.Autowired;

import com.letsplay.ui.BoardTile;
import com.letsplay.ui.GameArea;
import com.letsplay.ui.GameTileBuilder;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.VaadinSessionScope;
import com.vaadin.ui.UI;

@SpringComponent
@VaadinSessionScope
public class PlayChecker implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9197730325854787651L;
	private TreeMap<BoardPosition, BoardTile> playHolder;
	private Boolean isColumn;
	
	@Autowired
	private CompressedDAWGSet wordChecker;
	
	private Boolean isFirstPlay;
	
	public PlayChecker(){
		
		this.playHolder = new TreeMap<BoardPosition, BoardTile>();
		this.isColumn = true;
		this.isFirstPlay = true;
		
	}
	
	private int calculateMultiplePlay() {
		NavigableSet<BoardPosition> tiles = this.playHolder.navigableKeySet();
		int score = 0;
		int wordScoreMultiplier = 1;
		
		GameArea gameAreaTemp = (GameArea) UI.getCurrent().getContent();
		Gamestate state = (Gamestate) gameAreaTemp.getData();
		
		if (this.isColumn) {
			int limit = tiles.first().getRow() + (tiles.last().getRow() - tiles.first().getRow());
			for(int rowCounter = tiles.first().getRow(); rowCounter <= limit; rowCounter++){
				
				if(!state.boardState.isOccupied(tiles.first().getColumn() , rowCounter)){
				
					BoardPosition temp2 = new BoardPosition(tiles.first().getColumn() , rowCounter);
					Iterator<BoardPosition> iterator = tiles.iterator();
				
					while(iterator.hasNext()){
						BoardPosition temp3 = iterator.next();
						
						if(temp3.equals(temp2)){
							

							wordScoreMultiplier *= temp3.getWordScore();
							score += temp3.getTileState().getWeight() * temp3.getLetterScore();

							}
					
						}
					}else{
						
						BoardPosition temp3 = state.boardState.getOccupiedPosition(tiles.first().getColumn() , rowCounter);
						score += temp3.getTileState().getWeight();
						
					}
				}
			
			for(int prefixCounter = tiles.first().getRow() - 1; state.boardState.isOccupied(tiles.first().getColumn(), prefixCounter); prefixCounter--) {
				BoardPosition temp3 = state.boardState.getOccupiedPosition(tiles.first().getColumn() , prefixCounter);
				score += temp3.getTileState().getWeight();
			}
			
			for(int suffixCounter = tiles.last().getRow() + 1; state.boardState.isOccupied(tiles.first().getColumn(), suffixCounter); suffixCounter++) {
				BoardPosition temp3 = state.boardState.getOccupiedPosition(tiles.first().getColumn() , suffixCounter);
				score += temp3.getTileState().getWeight();
			}

			
			}else {
				int limit = tiles.first().getColumn() + (tiles.last().getColumn() - tiles.first().getColumn());
				for(int columnCounter = tiles.first().getColumn(); columnCounter <= limit; columnCounter++){
					
					if(!state.boardState.isOccupied(columnCounter , tiles.first().getRow())){
					
						BoardPosition temp2 = new BoardPosition(columnCounter , tiles.first().getRow());
						Iterator<BoardPosition> iterator = tiles.iterator();
					
						while(iterator.hasNext()){
							BoardPosition temp3 = iterator.next();
							
							if(temp3.equals(temp2)){
								
								wordScoreMultiplier *= temp3.getWordScore();
								score += temp3.getTileState().getWeight() * temp3.getLetterScore();

								}
						
							}
						}else{
							
							BoardPosition temp3 = state.boardState.getOccupiedPosition(columnCounter , tiles.first().getRow());
							score += temp3.getTileState().getWeight();
							
						}
					}
				
				for(int prefixCounter = tiles.first().getColumn() - 1; state.boardState.isOccupied(prefixCounter, tiles.first().getRow()); prefixCounter--) {
					BoardPosition temp3 = state.boardState.getOccupiedPosition(prefixCounter, tiles.first().getRow());
					score += temp3.getTileState().getWeight();
				}
				
				for(int suffixCounter = tiles.last().getColumn() + 1; state.boardState.isOccupied(suffixCounter, tiles.first().getRow()); suffixCounter++) {
					BoardPosition temp3 = state.boardState.getOccupiedPosition(suffixCounter, tiles.first().getRow());
					score += temp3.getTileState().getWeight();
					temp3.toString();
				}
			}
		

		return wordScoreMultiplier * score;
	}
	
	
	public int calculatePlay() {
		
		if(this.playHolder.size() > 1)
			return this.calculateMultiplePlay();
		
		return this.calculateSinglePlay();
		
	}
	
	
	private int calculateSinglePlay() {
		GameArea gameAreaTemp = (GameArea) UI.getCurrent().getContent();
		Gamestate state = (Gamestate) gameAreaTemp.getData();
		
		BoardPosition singleTile = this.playHolder.firstKey();
		
		int wordScoreMultipier = singleTile.getWordScore();
		int score = singleTile.getLetterScore() * singleTile.getTileState().getWeight();
		
		for (int prefixCounter = singleTile.getColumn() - 1; state.boardState.isOccupied(prefixCounter, singleTile.getRow()); prefixCounter--)
			score += state.boardState.getOccupiedPosition(prefixCounter, singleTile.getRow()).getTileState().getWeight();
		
		for (int suffixCounter = singleTile.getColumn() + 1; state.boardState.isOccupied(suffixCounter, singleTile.getRow()); suffixCounter++)
			score += state.boardState.getOccupiedPosition(suffixCounter, singleTile.getRow()).getTileState().getWeight();
		
		for (int upperCounter = singleTile.getRow() - 1; state.boardState.isOccupied(singleTile.getColumn(), upperCounter); upperCounter--)
			score += state.boardState.getOccupiedPosition(singleTile.getColumn(), upperCounter).getTileState().getWeight();
		
		for (int lowerCounter = singleTile.getRow() + 1; state.boardState.isOccupied(singleTile.getColumn(), lowerCounter); lowerCounter++)
			score += state.boardState.getOccupiedPosition(singleTile.getColumn(), lowerCounter).getTileState().getWeight();
		
		return wordScoreMultipier * score;
	}
	
	// Checks if the play made is correct.
	public boolean check(){
		if(this.playHolder.navigableKeySet().size() > 1) {
			if(this.isFirstPlay)
				return (this.checkSingleFile() && this.checkWord() && this.checkFirstPlay());
			else
				return (this.checkConnectedPlay() && this.checkSingleFile() && this.checkWord() && this.checkWordAdjacent());
		}else
			return this.singleTileCheck();
	}
	
	// Checks if the play made is connected to a tile on the board.
	private boolean checkConnectedPlay() {
		Iterator<BoardPosition> iterator = this.playHolder.navigableKeySet().iterator();
		
		GameArea gameAreaTemp = (GameArea) UI.getCurrent().getContent();
		Gamestate state = (Gamestate) gameAreaTemp.getData();
		
		if(this.isColumn) {
			while(iterator.hasNext()){
				
				// Get the current position and create positions above and below it. 
				BoardPosition position = iterator.next();
				
				// if the position before or after it is occupied, then it is connected to a tile on the board.
				if(state.boardState.isOccupied(position.getColumn() - 1, position.getRow()) || state.boardState.isOccupied(position.getColumn() + 1, position.getRow()))
					return true;
				
				// if the position directly above or below it is occupied, then it is connected to a tile on the board.
				else if(state.boardState.isOccupied(position.getColumn(), position.getRow() - 1) || state.boardState.isOccupied(position.getColumn(), position.getRow() + 1))
					return true;
			}
			
			
		}else {
			while(iterator.hasNext()){
				// Get the current position and create positions above and below it. 
				BoardPosition position = iterator.next();
				
				// if the position before or after it is occupied, then it is connected to a tile on the board.
				if(state.boardState.isOccupied(position.getColumn(), position.getRow() - 1) || state.boardState.isOccupied(position.getColumn(), position.getRow() + 1))
					return true;
				
				// if the position directly above or below it is occupied, then it is connected to a tile on the board.
				else if(state.boardState.isOccupied(position.getColumn() - 1, position.getRow()) || state.boardState.isOccupied(position.getColumn() + 1, position.getRow()))
					return true;
			}
		}
		
		// if all these conditions are not met.
		return false;
	}
	
	private boolean checkFirstPlay() {
		
		NavigableSet<BoardPosition> tiles = this.playHolder.navigableKeySet();
		BoardPosition temp = new BoardPosition(7, 7); 
		
		return tiles.contains(temp);
	}
	
	
	// Checks if play was made in a single unbroken file of positions.
	private boolean checkSingleFile(){
		
		NavigableSet<BoardPosition> tiles = this.playHolder.navigableKeySet();
		
		GameArea gameAreaTemp = (GameArea) UI.getCurrent().getContent();
		Gamestate state = (Gamestate) gameAreaTemp.getData();
		
		if(this.isColumn){
			
			// Calculate the limit of the loop
			int limit = tiles.first().getRow() + (tiles.last().getRow() - tiles.first().getRow());
			
			for(int counter = tiles.first().getRow(); counter <= limit; counter++){
				
				BoardPosition temp = new BoardPosition(tiles.first().getColumn(), counter);
				
				// If the position is not occupied and it was not played, return false 
				if(!state.boardState.isOccupied(tiles.first().getColumn(), counter) && !tiles.contains(temp))
						return false;
				
			}
		}else{
			
			// Calculate the limit of the loop
			int limit = tiles.first().getColumn() + (tiles.last().getColumn() - tiles.first().getColumn());
			
			for(int counter = tiles.first().getColumn(); counter <= limit; counter++){
				
				BoardPosition temp = new BoardPosition(counter , tiles.first().getRow());
				
				// If the position is not occupied and it was not played, return false 
				if(!state.boardState.isOccupied(counter , tiles.first().getRow()) && !tiles.contains(temp))
						return false;
				
			}
		}
		
		
		return true;
		
	}
	
	// Checks if the word played is correct.
	private boolean checkWord(){
		
		NavigableSet<BoardPosition> tiles = this.playHolder.navigableKeySet();
		StringBuilder word = new StringBuilder();
		StringBuilder prefix = new StringBuilder();
		StringBuilder suffix = new StringBuilder();
		
		GameArea gameAreaTemp = (GameArea) UI.getCurrent().getContent();
		Gamestate state = (Gamestate) gameAreaTemp.getData();

		if (this.isColumn){
	
		//////////////////////////////////////////////////////////////////////////////
		// Create the word formed on that single file using the total number of tiles played as limit.
			int limit = tiles.first().getRow() + (tiles.last().getRow() - tiles.first().getRow());
			for(int counter = tiles.first().getRow(); counter <= limit; counter++){

				
				if(!state.boardState.isOccupied(tiles.first().getColumn(), counter)){
					
					BoardPosition temp2 = new BoardPosition(tiles.first().getColumn(), counter);
					Iterator<BoardPosition> iterator = tiles.iterator();
					
					while(iterator.hasNext()){
						BoardPosition temp3 = iterator.next();
						
						if(temp3.equals(temp2)){
							
							word.append(temp3.getTileState().getLetter());
							
						}
					
					}
					
					
				}else{
				
					word.append(state.boardState.getOccupiedPosition(tiles.first().getColumn(), counter).getTileState().getLetter());

				}
					
			}
		////////////////////////////////////////////////////////////////////////////////////
			
			// Create the suffix already on the board.
			for(int row = tiles.last().getRow() + 1; state.boardState.isOccupied(tiles.last().getColumn(), row); row++) {
				BoardPosition temp = state.boardState.getOccupiedPosition(tiles.last().getColumn(), row);
				suffix.append(temp.getTileState().getLetter());
			}
			
			// Create the prefix in reverse order already on the board.
			for(int row = tiles.first().getRow() - 1; state.boardState.isOccupied(tiles.first().getColumn(), row); row--) {
				BoardPosition temp = state.boardState.getOccupiedPosition(tiles.first().getColumn(), row);
				prefix.append(temp.getTileState().getLetter());
			}
			
			prefix.reverse();
			word = prefix.append(word).append(suffix);
			
			
		}else{
			
			//////////////////////////////////////////////////////////////////////////////
			// Create the word formed on that single file using the total number of tiles played as limit.
			int limit = tiles.first().getColumn() + (tiles.last().getColumn() - tiles.first().getColumn());
			
			for(int counter = tiles.first().getColumn(); counter <= limit; counter++){

				
				if(!state.boardState.isOccupied(counter , tiles.first().getRow())){
					
					BoardPosition temp2 = new BoardPosition(counter , tiles.first().getRow());
					Iterator<BoardPosition> iterator = tiles.iterator();
					
					while(iterator.hasNext()){
						BoardPosition temp3 = iterator.next();
						
						if(temp3.equals(temp2)){
							
							word.append(temp3.getTileState().getLetter());
						}
					
					}
					
					
				}else{
				
					word.append(state.boardState.getOccupiedPosition(counter , tiles.first().getRow()).getTileState().getLetter());
			
				}
					
			}
			
			/////////////////////////////////////////////////////////////////////////////////
			
			// Create the suffix already on the board.
			for(int column = tiles.last().getColumn() + 1; state.boardState.isOccupied(column, tiles.last().getRow()); column++) {
				BoardPosition temp = state.boardState.getOccupiedPosition(column, tiles.last().getRow());
				suffix.append(temp.getTileState().getLetter());
			}
			
			// Create the prefix in reverse order already on the board.
			for(int column = tiles.first().getColumn() - 1; state.boardState.isOccupied(column, tiles.first().getRow()); column--) {
				BoardPosition temp = state.boardState.getOccupiedPosition(column, tiles.first().getRow());
				prefix.append(temp.getTileState().getLetter());
			}
			
			prefix.reverse();
			word = prefix.append(word).append(suffix);
		}
		
		return this.wordChecker.contains(word.toString());
	}
	
	// Checks if the adjacent words created are correct.
	private boolean checkWordAdjacent() {
		NavigableSet<BoardPosition> tiles = this.playHolder.navigableKeySet();
		GameArea gameAreaTemp = (GameArea) UI.getCurrent().getContent();
		Gamestate state = (Gamestate) gameAreaTemp.getData();
		Iterator<BoardPosition> iterator = tiles.iterator();
		
		if(this.isColumn) {
			while (iterator.hasNext()) {
				BoardPosition position = iterator.next();
				StringBuilder word = new StringBuilder();
				word.append(position.getTileState().getLetter());
				
				StringBuilder prefix = new StringBuilder();
				
				// Create the word formed.
				for(int column = position.getColumn() + 1; state.boardState.isOccupied(column, position.getRow()); column++) {
					BoardPosition tempPosition = state.boardState.getOccupiedPosition(column, position.getRow());
					word.append(tempPosition.getTileState().getLetter());
					
				}
				
				// Create the prefix formed.
				for(int column = position.getColumn() - 1; state.boardState.isOccupied(column, position.getRow()); column--) {
					BoardPosition tempPosition = state.boardState.getOccupiedPosition(column, position.getRow());
					prefix.append(tempPosition.getTileState().getLetter());
				}
				
				// Reverse and append the word to it.
				prefix.reverse();
				word = prefix.append(word);
				
				if(word.length() > 1 && !this.wordChecker.contains(word.toString())) {
					return false;
				}
				
			}
		
			
		}else {
			while (iterator.hasNext()) {
				BoardPosition position = iterator.next();
				StringBuilder word = new StringBuilder();
				word.append(position.getTileState().getLetter());
				StringBuilder prefix = new StringBuilder();
				
				// Create the word formed.
				for(int row = position.getRow() + 1; state.boardState.isOccupied(position.getColumn(), row); row++) {
					BoardPosition tempPosition = state.boardState.getOccupiedPosition(position.getColumn(), row);
					word.append(tempPosition.getTileState().getLetter());
				}
				
				// Create the prefix formed.
				for(int row = position.getRow() - 1; state.boardState.isOccupied(position.getColumn(), row); row--) {
					BoardPosition tempPosition = state.boardState.getOccupiedPosition(position.getColumn(), row);
					prefix.append(tempPosition.getTileState().getLetter());
				}
				
				// Reverse and append the word to it.
				prefix.reverse();
				word = prefix.append(word);
				
				if(word.length() > 1 && !this.wordChecker.contains(word.toString())) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	// Makes play permanent by updating the boardState. 
	public void finalizePlay() {
		
		NavigableSet<BoardPosition> tiles = this.playHolder.navigableKeySet();
		
		GameArea gameAreaTemp = (GameArea) UI.getCurrent().getContent();
		Gamestate state = (Gamestate) gameAreaTemp.getData();
		
		ArrayList<BoardPosition> temp = new ArrayList<BoardPosition>();
		temp.addAll(tiles);
		
		state.boardState.updateState(temp);
		
// Updating the rack here because i could not do it on the button that triggered this action
		GameArea gameArea = (GameArea)UI.getCurrent().getContent();
		for(int counter = 0; counter < this.playHolder.size(); counter++)
			if(state.tileBag.tilesRemaining() > 0) {
				gameArea.addTileToRack(state.tileBag.getTile());
			}
		
		
		this.playHolder.clear();
		this.isFirstPlay = false;
		
	}
	
	
	public void makePlay(BoardPosition position, BoardTile tile) throws ClassCastException{
		
		try{
			
			this.playHolder.put(position, tile);
			BoardPosition first = this.playHolder.firstKey();
			BoardPosition second = this.playHolder.lastKey();
			
			if(first.getRow() == second.getRow() && this.playHolder.size() > 1)
				this.isColumn = false; 
			else if(first.getColumn() == second.getColumn() && this.playHolder.size() > 1)
				this.isColumn = true;
			
		}catch(ClassCastException e){
			
			throw e;
			
		}
	}
	
	
	private boolean singleTileCheck() {
		
		GameArea gameAreaTemp = (GameArea) UI.getCurrent().getContent();
		Gamestate state = (Gamestate) gameAreaTemp.getData();
		
		BoardPosition singleTile = this.playHolder.firstKey();
		
		StringBuilder prefixWord = new StringBuilder();
		StringBuilder suffixWord = new StringBuilder();
		suffixWord.append(singleTile.getTileState().getLetter());
		
		StringBuilder upperWord = new StringBuilder();
		StringBuilder lowerWord = new StringBuilder();
		lowerWord.append(singleTile.getTileState().getLetter());
		
		for (int prefixCounter = singleTile.getColumn() - 1; state.boardState.isOccupied(prefixCounter, singleTile.getRow()); prefixCounter--)
			prefixWord.append(state.boardState.getOccupiedPosition(prefixCounter, singleTile.getRow()).getTileState().getLetter());
		
		for (int suffixCounter = singleTile.getColumn() + 1; state.boardState.isOccupied(suffixCounter, singleTile.getRow()); suffixCounter++)
			suffixWord.append(state.boardState.getOccupiedPosition(suffixCounter, singleTile.getRow()).getTileState().getLetter());
		
		for (int upperCounter = singleTile.getRow() - 1; state.boardState.isOccupied(singleTile.getColumn(), upperCounter); upperCounter--)
			upperWord.append(state.boardState.getOccupiedPosition(singleTile.getColumn(), upperCounter).getTileState().getLetter());
		
		for (int lowerCounter = singleTile.getRow() + 1; state.boardState.isOccupied(singleTile.getColumn(), lowerCounter); lowerCounter++)
			upperWord.append(state.boardState.getOccupiedPosition(singleTile.getColumn(), lowerCounter).getTileState().getLetter());
	
		prefixWord.reverse();
		upperWord.reverse();
		
		prefixWord.append(suffixWord);
		upperWord.append(lowerWord);
		
		return this.wordChecker.contains(prefixWord.toString()) || this.wordChecker.contains(upperWord.toString());		
		
	}
		
	
	public void undoPlay() {
		GameArea gameArea = (GameArea)UI.getCurrent().getContent();
		Iterator<BoardPosition> iterator = this.playHolder.keySet().iterator();
		
		while(iterator.hasNext()) {
			
			BoardPosition position = iterator.next();
			Tilestate tileState = position.getTileState();
			
			BoardTile boardTile = this.playHolder.get(position);
			
			gameArea.undoPlay(boardTile, position.getColumn(), position.getRow());
			gameArea.addTileToRack(GameTileBuilder.get().setWeight(tileState).build());
			
			GameArea gameAreaTemp = (GameArea) UI.getCurrent().getContent();
			Gamestate state = (Gamestate) gameAreaTemp.getData();
			
			state.boardState.setEmptyPosition(position.getColumn(), position.getRow());
			
		}
		
		this.playHolder.clear();
		this.isColumn = true;
	}
}

