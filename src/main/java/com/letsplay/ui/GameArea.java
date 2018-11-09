package com.letsplay.ui;


import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.letsplay.logic.Gamestate;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.VerticalLayout;

@SpringComponent
@UIScope
public class GameArea extends CustomComponent{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1445297031956063093L;
	public Board board;
	private HorizontalLayout mainLayout;
	private ListSelect<String> players;
	

	private UserRack rack;
	
	@Value("${application.users}")
	String destination;
	
	@Autowired
	public GameArea(Gamestate gamestate,UserRack rack, Board board,GameButtons gamebuttons) {
		this.setData(gamestate);
		
		this.board = board;
		this.rack = rack;
		for (int temp = 0; temp < 7; temp++){
			Gamestate tempstate = (Gamestate)this.getData();
			GameTile gameTile = tempstate.tileBag.getTile();
			this.rack.addTile(gameTile);
		}
		
		this.players = new ListSelect<String>();
		this.players.setCaption("Players Online");
		this.players.setWidth("200px");
		this.players.addSelectionListener(listener -> {
			
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
	
	public void updateUsersList(Set<String> username) {

		this.players.setItems(username);
		
	}
}
