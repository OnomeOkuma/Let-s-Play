package com.letsplay.ui;

import java.util.Optional;

import com.letsplay.logic.Gamestate;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
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
		GameArea gameArea = (GameArea) UI.getCurrent().getContent();
		Gamestate gameState = (Gamestate) gameArea.getData();
		
		TextArea dropArea = new TextArea();
		dropArea.setPlaceholder("Drop Tiles here to exchange");
		dropArea.setSizeFull();
		
		DropTargetExtension<TextArea> de = new DropTargetExtension<TextArea>(dropArea);
		de.addDropListener(drop -> {
			if (this.counter > 0) {
				Optional<AbstractComponent> gameTile = null;
				if (drop.getDragSourceComponent().isPresent())
					gameTile = drop.getDragSourceComponent();
				gameState.tileBag.returnTile(gameTile);
				
				gameArea.removeTileFromRack((GameTile)gameTile.get());
				gameArea.addTileToRack(gameState.tileBag.getTile());
				this.counter--;
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

