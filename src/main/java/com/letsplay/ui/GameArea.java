package com.letsplay.ui;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;

import com.letsplay.UserPage;
import com.letsplay.events.PlayInviteEvent;
import com.letsplay.logic.Gamestate;
import com.letsplay.logic.Tilebag;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.VaadinSessionScope;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.RadioButtonGroup;
import com.vaadin.ui.VerticalLayout;

@SpringComponent
@VaadinSessionScope
public class GameArea extends CustomComponent{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1445297031956063093L;
	public Board board;
	private HorizontalLayout mainLayout;
	private RadioButtonGroup<String> players;
	

	private UserRack rack;
	private ApplicationEventPublisher applicationEventPublisher;
	
	@Value("${application.users}")
	String destination;
	
	@Autowired
	public GameArea(UserRack rack, Board board,GameButtons gamebuttons,
			ApplicationEventPublisher applicationEventPublisher) {
		
		this.board = board;
		this.rack = rack;
		this.applicationEventPublisher = applicationEventPublisher;
		
		Tilebag tileBag = new Tilebag();
		
		for (int temp = 0; temp < 7; temp++){
			GameTile gameTile = tileBag.getTile();
			this.rack.addTile(gameTile);
		}
		
		this.players = new RadioButtonGroup<String>();
		this.players.setCaption("Players Online");
		this.players.setWidth("200px");
		this.players.addSelectionListener(listener -> {
			
			Optional<String> toPlayer = this.players.getSelectedItem();
			if(toPlayer.isPresent()) {
				PlayInviteEvent event = new PlayInviteEvent("play");
				UserPage ui = (UserPage)this.getUI();
				event.setFromPlayer(ui.getCurrentUser());
				event.setToPlayer(toPlayer.get());
				this.applicationEventPublisher.publishEvent(event);
				this.players.clear();
			}
		});
		
		GameButtons buttonArea = gamebuttons;
		this.mainLayout = new HorizontalLayout();
		VerticalLayout layout = new VerticalLayout(this.board, this.rack);
		this.mainLayout.addComponents(this.players, layout, buttonArea);

		setCompositionRoot(this.mainLayout);

	}

	public void addTileToRack(GameTile gameTile) {
		this.rack.addTile(gameTile);
	}

	public void removeTileFromRack(GameTile gameTile) {
		this.rack.removeTile(gameTile);
	}

	public void undoPlay(BoardTile boardTile, int column, int row) {

		this.board.removeTile(column, row);
		this.board.placeBoardTile(boardTile, column, row);

	}
	
	public void placeGameTile(GameTile gameTile, int column, int row) {
		this.board.placeGameTile(gameTile, column, row);
	}
	
	public void updateUsersList(Set<String> username) {

		this.players.setItems(username);
		
	}
}
