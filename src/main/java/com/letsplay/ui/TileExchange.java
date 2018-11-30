package com.letsplay.ui;

import java.util.Optional;

import com.letsplay.UserPage;
import com.letsplay.repository.GameSession;
import com.letsplay.utils.EmptyTileBagException;
import com.letsplay.utils.GameSessionNotFoundException;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.dnd.DropTargetExtension;

public class TileExchange extends Window{
		
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2977180198891696652L;
	private int counter;
	
	public TileExchange() {
		super("Exchange Tile");
		
		this.counter = 7;
		UserPage userPage = (UserPage) UI.getCurrent();
		GameArea gameArea = (GameArea)userPage.getContent();
		
		TextArea dropArea = new TextArea();
		dropArea.setPlaceholder("Drop Tiles here to exchange");
		dropArea.setSizeFull();
		
		DropTargetExtension<TextArea> de = new DropTargetExtension<TextArea>(dropArea);
		de.addDropListener(drop -> {
			if (this.counter > 0) {
				Optional<AbstractComponent> gameTile = null;
				if (drop.getDragSourceComponent().isPresent())
					gameTile = drop.getDragSourceComponent();
				try {
					GameSession gameSession = BoardTileBuilder.gameSessionService.findByPlayers(userPage.getCurrentUser());
					gameSession.getTileBag().returnTile(gameTile);
					gameArea.removeTileFromRack((GameTile)gameTile.get());
					try {
						gameArea.addTileToRack(gameSession.getTileBag().getTile());
						this.counter--;
					} catch (EmptyTileBagException e) {
						Notification.show(e.getMessage(), Type.ERROR_MESSAGE);
						e.printStackTrace();
					}
					
					
				} catch (GameSessionNotFoundException e) {
					// TODO Auto-generated catch block
					Notification.show("User has not entered into a Gaming Session", Type.ERROR_MESSAGE);
				}
				
			}
		});
		
		this.setResizable(false);
		setHeight("200px");
		setWidth("300px");
		setContent(dropArea);
		setModal(false);
		center();
	}
	
	
}

