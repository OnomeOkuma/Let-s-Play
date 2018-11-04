package com.letsplay.ui;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Image;

public class BoardTile extends CustomComponent{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 681514117459324547L;
	public static final String TRIPLE_WORD_SCORE = "triple word score.jpg"; 
	public static final String DOUBLE_WORD_SCORE = "double word score.jpg";
	public static final String TRIPLE_LETTER_SCORE = "triple letter score.jpg";
	public static final String DOUBLE_LETTER_SCORE = "double letter score.jpg";
	public static final String CENTRE_BOARD = "centre board.jpg";
	public static final String BLANK_BOARD = "blank.jpg";
	
	
	BoardTile(String url){
		Image tile = new Image(null, new ThemeResource(url));
		tile.setHeight("36px");
		tile.setWidth("40px");
		
		setCompositionRoot(tile);
	}
}

