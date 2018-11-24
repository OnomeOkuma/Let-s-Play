package com.letsplay.ui;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;

import com.letsplay.UserPage;
import com.letsplay.logic.BoardPosition;
import com.letsplay.logic.Tilestate;
import com.letsplay.repository.GameSession;
import com.letsplay.service.GameSessionService;
import com.letsplay.utils.GameSessionNotFoundException;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.dnd.DropTargetExtension;

@SpringComponent
public class BoardTileBuilder implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 881855367056193574L;

	private String imageUrl; 
	
	@Autowired
	transient GameSessionService gameSessionService;
	
	private BoardTileBuilder(){
		
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
						UserPage userPage = (UserPage) UI.getCurrent();
						
						try {
							GameSession gameSession = gameSessionService.findBySessionName(userPage.getGameSessionName());
							BoardPosition position = gameSession.getBoardState().getEmptyPosition(column, row);
							
							// Place the position and tile in the temporary location for play validation.
							try {
								
								gameSession.getPlayChecker().makePlay(position, tile);
								grid.replaceComponent(tile, drop.getDragSourceComponent().get());
								grid.removeComponent(tile);
								
								// Update it with the TileState.
								position.setTileState((Tilestate) drop.getDragData().get());
								
								gameSessionService.saveSession(gameSession);
								break;
								
							} catch (Exception e) {
								
								Notification.show("Invalid Play", Type.ERROR_MESSAGE);
								e.printStackTrace();
								break;
								
							}
							
						} catch (GameSessionNotFoundException e1) {
							
							Notification.show("User has not entered into a Gaming Session", Type.ERROR_MESSAGE);
							
						}
						
						
					}
					
				}
			
			
		});
		
		return tile;
	}
	
	
}
