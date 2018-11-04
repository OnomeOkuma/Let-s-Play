package com.letsplay.ui;

import com.letsplay.logic.Tilestate;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Image;

public class GameTile extends CustomComponent{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7803555788347507564L;

	GameTile(Tilestate tileState){

		this.setData(tileState);
		String url = tileState.getLetter().concat(".jpg");
		Image image = new Image(null, new ThemeResource(url));
		image.setHeight("36px");
		image.setWidth("40px");
		
		setCompositionRoot(image);
	}
	
}
