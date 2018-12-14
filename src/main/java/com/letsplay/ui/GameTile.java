package com.letsplay.ui;

import java.util.Iterator;
import java.util.Optional;

import com.letsplay.UserPage;
import com.letsplay.logic.Tilestate;
import com.vaadin.server.Extension;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Image;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.dnd.DragSourceExtension;

public class GameTile extends CustomComponent{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7803555788347507564L;
	
	public boolean isBlank;
	
	private Tilestate previousState;
	
	private Tilestate tileState;


	GameTile(Tilestate tileState){

		this.tileState = tileState;
		String url = tileState.getLetter().concat(".jpg");
		Image image = new Image(null, new ThemeResource(url));
		image.setHeight("36px");
		image.setWidth("40px");
		
		setCompositionRoot(image);
		if (tileState.getWeight() == Tilestate.BLANK)
			isBlank = true;
		this.addContextClickListener(listener -> {
			UserPage userPage = (UserPage) UI.getCurrent();
			GameArea gameArea = (GameArea)userPage.getContent();
			if(this.isBlank && gameArea.isYourTurn()) {
				
				ComboBox<String> dropdown = new ComboBox<String>();
				dropdown.setItems("A","B","C","D","E","F","G","H","I"
						,"J","K","L","M","N","O","P","Q","R","S","T"
						,"U","V","W","X","Y","Z");
				dropdown.addSelectionListener(listener2 -> {
					Optional<String> selected = listener2.getSelectedItem();
					if(selected.isPresent())
						this.updateBlankImage(selected.get());
				});
				
				VerticalLayout layout = new VerticalLayout();
				layout.addComponent(dropdown);
				Window window = new Window("Select Letter");
				window.setHeight("100px");
				window.setWidth("200px");
				window.setModal(true);
				window.setResizable(false);
				window.setContent(layout);
				window.center();
				UI.getCurrent().addWindow(window);
				

			}
		});
	}
	
	
	public void updateBlankImage(String letter) {
		
		this.previousState = this.tileState;
		
		Tilestate state = new Tilestate(letter.toUpperCase(), 0);
		this.tileState = state;
		
		String url = state.getLetter().concat(".jpg");
		Image image = new Image(null, new ThemeResource(url));
		image.setHeight("36px");
		image.setWidth("40px");
		this.setCompositionRoot(image);
		Iterator<Extension> iterator = this.getExtensions().iterator();

		while(iterator.hasNext()) {
			Extension next = iterator.next();
			if(next instanceof DragSourceExtension)
				this.getExtensions().remove(next);
		}
			
		DragSourceExtension<GameTile> dragSource = new DragSourceExtension<GameTile>(this);
		dragSource.setDragData(this.getTileState());

	}
	
	public void undoBlankPlay() {
		
		this.tileState = this.previousState;
		String url = this.previousState.getLetter().concat(".jpg");
		Image image = new Image(null, new ThemeResource(url));
		image.setHeight("36px");
		image.setWidth("40px");
		
		setCompositionRoot(image);
	}
	

	public Tilestate getTileState() {
		return tileState;
	}


	public void setTileState(Tilestate tileState) {
		this.tileState = tileState;
	}

}
