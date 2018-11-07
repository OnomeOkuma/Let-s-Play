package com.letsplay.ui;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.annotation.VaadinSessionScope;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;

@SpringComponent
@UIScope
public class Board extends CustomComponent {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8029716554160486282L;
	GridLayout layout;
	public Board(){
		this.layout = new GridLayout(15,15);
		this.layout.setMargin(false);
		this.layout.setSpacing(false);
		
		for(int rowindex = 0; rowindex < 15; rowindex++){
			for(int columnindex = 0; columnindex < 15; columnindex++){
				switch (rowindex){
				// Note: Some repetition occurs.
				
				// Creates row 0 of the board.
				case 0:	switch (columnindex){
								case 0: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.TRIPLE_WORD_SCORE).build(), columnindex, rowindex);break;
								case 3: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.DOUBLE_LETTER_SCORE).build(), columnindex, rowindex);break;
								case 7: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.TRIPLE_WORD_SCORE).build(), columnindex, rowindex);break;
								case 11: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.DOUBLE_LETTER_SCORE).build(), columnindex, rowindex);break;
								case 14: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.TRIPLE_WORD_SCORE).build(), columnindex, rowindex);break;
								default: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.BLANK_BOARD).build(), columnindex, rowindex); break;					
								} break;
								
				case 1: switch (columnindex){
								case 1: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.DOUBLE_WORD_SCORE).build(), columnindex, rowindex);break;
								case 5:	this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.TRIPLE_LETTER_SCORE).build(), columnindex, rowindex);break;
								case 9: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.TRIPLE_LETTER_SCORE).build(), columnindex, rowindex);break;
								case 13: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.DOUBLE_WORD_SCORE).build(), columnindex, rowindex);break;
								default: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.BLANK_BOARD).build(), columnindex, rowindex); break;	
								} break;
								
				case 2: switch (columnindex){
								case 2: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.DOUBLE_WORD_SCORE).build(), columnindex, rowindex);break;
								case 6: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.DOUBLE_LETTER_SCORE).build(), columnindex, rowindex);break;
								case 8: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.DOUBLE_LETTER_SCORE).build(), columnindex, rowindex);break;
								case 12: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.DOUBLE_WORD_SCORE).build(), columnindex, rowindex);break;
								default: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.BLANK_BOARD).build(), columnindex, rowindex); break;
								} break;
				
				case 3: switch (columnindex){
								case 0: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.DOUBLE_LETTER_SCORE).build(), columnindex, rowindex);break;
								case 3: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.DOUBLE_WORD_SCORE).build(), columnindex, rowindex);break;
								case 7: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.DOUBLE_LETTER_SCORE).build(), columnindex, rowindex);break;
								case 11: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.DOUBLE_WORD_SCORE).build(), columnindex, rowindex);break;
								case 14: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.DOUBLE_LETTER_SCORE).build(), columnindex, rowindex);break;
								default: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.BLANK_BOARD).build(), columnindex, rowindex); break;				
								} break;
								
				case 4: switch (columnindex){
								case 4: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.DOUBLE_WORD_SCORE).build(), columnindex, rowindex);break;
								case 10: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.DOUBLE_WORD_SCORE).build(), columnindex, rowindex);break;
								default: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.BLANK_BOARD).build(), columnindex, rowindex); break;
								} break;
								
				case 5: switch (columnindex){
								case 1: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.TRIPLE_LETTER_SCORE).build(), columnindex, rowindex);break;
								case 5: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.TRIPLE_LETTER_SCORE).build(), columnindex, rowindex);break;
								case 9: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.TRIPLE_LETTER_SCORE).build(), columnindex, rowindex);break;
								case 13: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.TRIPLE_LETTER_SCORE).build(), columnindex, rowindex);break;
								default: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.BLANK_BOARD).build(), columnindex, rowindex); break;
								} break;
								
				case 6: switch (columnindex){
								case 2: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.DOUBLE_LETTER_SCORE).build(), columnindex, rowindex);break;
								case 6: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.DOUBLE_LETTER_SCORE).build(), columnindex, rowindex);break;
								case 8: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.DOUBLE_LETTER_SCORE).build(), columnindex, rowindex);break;
								case 12: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.DOUBLE_LETTER_SCORE).build(), columnindex, rowindex);break;
								default: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.BLANK_BOARD).build(), columnindex, rowindex); break;
								} break;
								
				case 7: switch (columnindex){
								case 0: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.TRIPLE_WORD_SCORE).build(), columnindex, rowindex);break;
								case 3: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.DOUBLE_LETTER_SCORE).build(), columnindex, rowindex);break;
								case 7: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.CENTRE_BOARD).build(), columnindex, rowindex);break;
								case 11:this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.DOUBLE_LETTER_SCORE).build(), columnindex, rowindex);break;
								case 14: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.TRIPLE_WORD_SCORE).build(), columnindex, rowindex);break;
								default: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.BLANK_BOARD).build(), columnindex, rowindex); break;	
								} break;
								
				case 8: switch (columnindex){
								case 2: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.DOUBLE_LETTER_SCORE).build(), columnindex, rowindex);break;
								case 6: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.DOUBLE_LETTER_SCORE).build(), columnindex, rowindex);break;
								case 8: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.DOUBLE_LETTER_SCORE).build(), columnindex, rowindex);break;
								case 12: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.DOUBLE_LETTER_SCORE).build(), columnindex, rowindex);break;
								default: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.BLANK_BOARD).build(), columnindex, rowindex); break;
								} break;
				
				case 9: switch (columnindex){
								case 1: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.TRIPLE_LETTER_SCORE).build(), columnindex, rowindex);break;
								case 5: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.TRIPLE_LETTER_SCORE).build(), columnindex, rowindex);break;
								case 9: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.TRIPLE_LETTER_SCORE).build(), columnindex, rowindex);break;
								case 13: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.TRIPLE_LETTER_SCORE).build(), columnindex, rowindex);break;
								default: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.BLANK_BOARD).build(), columnindex, rowindex); break;
								} break;
				case 10: switch (columnindex){
								case 4: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.DOUBLE_WORD_SCORE).build(), columnindex, rowindex);break;
								case 10: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.DOUBLE_WORD_SCORE).build(), columnindex, rowindex);break;
								default: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.BLANK_BOARD).build(), columnindex, rowindex); break;
								} break;
				
				case 11: switch (columnindex){
								case 0: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.DOUBLE_LETTER_SCORE).build(), columnindex, rowindex);break;
								case 3: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.DOUBLE_WORD_SCORE).build(), columnindex, rowindex);break;
								case 7: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.DOUBLE_LETTER_SCORE).build(), columnindex, rowindex);break;
								case 11: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.DOUBLE_WORD_SCORE).build(), columnindex, rowindex);break;
								case 14: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.DOUBLE_LETTER_SCORE).build(), columnindex, rowindex);break;
								default: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.BLANK_BOARD).build(), columnindex, rowindex); break;				
								} break;
								
				case 12: switch (columnindex){
								case 2: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.DOUBLE_WORD_SCORE).build(), columnindex, rowindex);break;
								case 6: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.DOUBLE_LETTER_SCORE).build(), columnindex, rowindex);break;
								case 8: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.DOUBLE_LETTER_SCORE).build(), columnindex, rowindex);break;
								case 12: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.DOUBLE_WORD_SCORE).build(), columnindex, rowindex);break;
								default: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.BLANK_BOARD).build(), columnindex, rowindex); break;
								} break;
				
				case 13: switch (columnindex){
								case 1: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.DOUBLE_WORD_SCORE).build(), columnindex, rowindex);break;
								case 5:	this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.TRIPLE_LETTER_SCORE).build(), columnindex, rowindex);break;
								case 9: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.TRIPLE_LETTER_SCORE).build(), columnindex, rowindex);break;
								case 13: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.DOUBLE_WORD_SCORE).build(), columnindex, rowindex);break;
								default: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.BLANK_BOARD).build(), columnindex, rowindex); break;	
								} break;
								
				case 14:	switch (columnindex){
								case 0: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.TRIPLE_WORD_SCORE).build(), columnindex, rowindex);break;
								case 3: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.DOUBLE_LETTER_SCORE).build(), columnindex, rowindex);break;
								case 7: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.TRIPLE_WORD_SCORE).build(), columnindex, rowindex);break;
								case 11: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.DOUBLE_LETTER_SCORE).build(), columnindex, rowindex);break;
								case 14: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.TRIPLE_WORD_SCORE).build(), columnindex, rowindex);break;
								default: this.layout.addComponent(BoardTileBuilder.get().setImageUrl(BoardTile.BLANK_BOARD).build(), columnindex, rowindex); break;					
								} break;
				}
			}
		}
	
		setCompositionRoot(this.layout);
	}
	
	protected void removeTile(int column, int row) {
		this.layout.removeComponent(column, row);
	}
	
	protected void placeBoardTile(BoardTile boardTile, int column, int row) {
		this.layout.addComponent(boardTile, column, row);
	}
}
