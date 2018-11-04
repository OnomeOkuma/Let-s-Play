package com.letsplay.ui;

import com.letsplay.logic.Tilestate;
import com.vaadin.ui.dnd.DragSourceExtension;

public class GameTileBuilder {
	private Tilestate tileState;
	
	private GameTileBuilder(){
	}
	
	
	public static GameTileBuilder get(){
		return new GameTileBuilder();
	}
	
	public GameTileBuilder setWeight(Tilestate tileState){
		this.tileState = tileState;
		return this;
	}
	
	public GameTile build(){
		GameTile tile = new GameTile(this.tileState);
		DragSourceExtension<GameTile> dragSource = new DragSourceExtension<GameTile>(tile);
		dragSource.setDragData(tile.getData());
		return tile;
	}
}
