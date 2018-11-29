package com.letsplay.ui;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;

import com.letsplay.UserPage;
import com.letsplay.events.PlayInviteEvent;
import com.letsplay.logic.Tilebag;
import com.letsplay.repository.GameSession;
import com.letsplay.service.GameSessionService;
import com.letsplay.utils.GameSessionNotFoundException;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.VaadinSessionScope;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.RadioButtonGroup;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Notification.Type;

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
	private GameSessionService gameSessionService;
	
	@Value("${application.users}")
	String destination;
	
	@Autowired
	public GameArea(UserRack rack, Board board,GameButtons gamebuttons,
			ApplicationEventPublisher applicationEventPublisher,
			GameSessionService gameSessionService) {
		
		this.board = board;
		this.rack = rack;
		this.applicationEventPublisher = applicationEventPublisher;
		this.gameSessionService = gameSessionService;
		
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
				try {
					
					@SuppressWarnings("unused")
					GameSession session = this.gameSessionService.findByPlayers(toPlayer.get());
					Notification.show("User is currently in another game", Type.ERROR_MESSAGE);
					this.players.clear();
					
				} catch (GameSessionNotFoundException e) {
					
					PlayInviteEvent event = new PlayInviteEvent("play");
					UserPage ui = (UserPage)this.getUI();
					event.setFromPlayer(ui.getCurrentUser());
					event.setToPlayer(toPlayer.get());
					this.applicationEventPublisher.publishEvent(event);
					this.players.clear();
				
				}
				
			
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
