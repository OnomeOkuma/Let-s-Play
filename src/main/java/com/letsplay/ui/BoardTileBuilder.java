package com.letsplay.ui;

import java.io.Serializable;

import org.springframework.context.ApplicationEventPublisher;

import com.letsplay.UserPage;
import com.letsplay.events.PlayEvent;
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
	

	public static transient GameSessionService gameSessionService;
	

	public static transient ApplicationEventPublisher applicationEventPublisher;
	
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
			UserPage userPage = (UserPage) UI.getCurrent();
			
			GameArea gameArea = (GameArea) userPage.getContent();
			if(gameArea.isYourTurn()) {
			GridLayout grid = (GridLayout) drop.getComponent().getParent();
			
			for(int column = 0; column < 15 ; column++)
				for(int row = 0; row < 15; row++){
					
					// Get the location of the tile on the layout.
					if (grid.getComponent(column, row) == tile){
						
						
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
								PlayEvent event = new PlayEvent("play made");
								
								if(gameSession.getPlayer1().getName().equals(userPage.getCurrentUser()))
									event.setNotifyPlayer(gameSession.getPlayer2().getName());
								else
									event.setNotifyPlayer(gameSession.getPlayer1().getName());
								
								applicationEventPublisher.publishEvent(event);
								
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
			
			}else {
				Notification.show("It is not your turn", Type.ERROR_MESSAGE);
			}
		});
		
		return tile;
	}
	
	
}
