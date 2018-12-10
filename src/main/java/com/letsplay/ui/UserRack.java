package com.letsplay.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Component;
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
	
	protected void clearRack() {
		this.layout.removeAllComponents();
	}
	
	protected boolean isEmpty() {
		int tileCount = this.layout.getComponentCount();
		
		if (tileCount > 0)
			return false;
		else 
			return true;
		
	}
	
	protected List<GameTile> getTiles(){
		Iterator<Component> iterator = this.layout.iterator();
		List<GameTile> components = new ArrayList<GameTile>();
		while(iterator.hasNext())
			components.add((GameTile) iterator.next());
		
		return components;
	}
}
