package com.letsplay.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.security.shared.VaadinSharedSecurity;

import com.letsplay.logic.Gamestate;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SpringComponent
@UIScope
public class GameButtons extends CustomComponent {


	/**
	 * 
	 */
	private static final long serialVersionUID = -9182052284893399264L;
	private Scoreboard scoreBoard;
	
	@Autowired
	VaadinSharedSecurity vaadinSecurity;
	
	@Autowired
	public GameButtons(Scoreboard scoreBoard) {

		VerticalLayout layout = new VerticalLayout();
		this.scoreBoard = scoreBoard;
		Button playButton = new Button("Play");
		playButton.setWidth("160px");
		playButton.addClickListener(click -> {

			GameArea gameAreaTemp = (GameArea) UI.getCurrent().getContent();
			Gamestate state = (Gamestate) gameAreaTemp.getData();
			if (state.playChecker.check()) {
				scoreBoard.setScore(state.playChecker.calculatePlay());
				state.playChecker.finalizePlay();
			} else {
				state.playChecker.undoPlay();
			}

		});

		Button exchangeTileButton = new Button("Exchange Tile");
		exchangeTileButton.setWidth("160px");
		exchangeTileButton.addClickListener(click -> {
				
			UI.getCurrent().addWindow(new TileExchange());

		});

		Button skipTurnButton = new Button("Pass");
		skipTurnButton.setWidth("160px");

		Button surrenderButton = new Button("Surrender");
		surrenderButton.setWidth("160px");

		Button logoutButton = new Button("Log Out");
		logoutButton.addClickListener(listener -> {
			
			vaadinSecurity.logout();
			
		});

		logoutButton.setWidth("160px");
		
		layout.addComponents(scoreBoard, playButton, exchangeTileButton, skipTurnButton, surrenderButton, logoutButton);

		setCompositionRoot(layout);

	}

	public void setPlayerName(String name) {
		this.scoreBoard.setName(name);
	}
}
