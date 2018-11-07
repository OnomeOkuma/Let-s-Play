package com.letsplay.ui;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.annotation.VaadinSessionScope;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;

@SpringComponent
@UIScope
public class UserRack extends CustomComponent {
	
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 3371371446163902598L;
	private HorizontalLayout layout;
	
	public UserRack(){
		this.layout = new HorizontalLayout();
		setCompositionRoot(this.layout);
	}
	
	protected void addTile(GameTile gameTile) {
		this.layout.addComponent(gameTile);
	}
	
	protected void removeTile(GameTile gameTile) {
		this.layout.removeComponent(gameTile);
	}
}
