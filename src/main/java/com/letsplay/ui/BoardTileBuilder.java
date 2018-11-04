package com.letsplay.ui;

import com.letsplay.logic.BoardPosition;
import com.letsplay.logic.Gamestate;
import com.letsplay.logic.Tilestate;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.dnd.DropTargetExtension;

public class BoardTileBuilder {
private String imageUrl; 
	
	
	private BoardTileBuilder(){
		
	}
	
	public static BoardTileBuilder get(){
		return new BoardTileBuilder();
	}
	
	public BoardTileBuilder setImageUrl(String imageUrl){
		this.imageUrl = imageUrl;
		return this;
	}
	
	public BoardTile build(){
		
		BoardTile tile = new BoardTile(this.imageUrl);
		DropTargetExtension<BoardTile> dropTarget = new DropTargetExtension<BoardTile>(tile);
		
		
		dropTarget.addDropListener(drop -> {
			
			GridLayout grid = (GridLayout) drop.getComponent().getParent();
			
			for(int column = 0; column < 15 ; column++)
				for(int row = 0; row < 15; row++){
					
					// Get the location of the tile on the layout.
					if (grid.getComponent(column, row) == tile){
						GameArea gameAreaTemp = (GameArea) UI.getCurrent().getContent();
						Gamestate state = (Gamestate) gameAreaTemp.getData();
						BoardPosition position = state.boardState.getEmptyPosition(column, row);
						
						// Place the position and tile in the temporary location for play validation.
						try {
							
							state.playChecker.makePlay(position, tile);
							grid.replaceComponent(tile, drop.getDragSourceComponent().get());
							grid.removeComponent(tile);
							
							// Update it with the TileState.
							position.setTileState((Tilestate) drop.getDragData().get());
							
							
							gameAreaTemp.setData(state);
							break;
							
						} catch (Exception e) {
							
							Notification.show("Invalid Play", Type.ERROR_MESSAGE);
							e.printStackTrace();
							break;
							
						}
						
						
					}
					
				}
			
			
		});
		
		return tile;
	}
	
	
}
