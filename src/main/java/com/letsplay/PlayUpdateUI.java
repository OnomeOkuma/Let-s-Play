package com.letsplay;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.letsplay.events.PlayEvent;
import com.letsplay.events.UndoPlayEvent;
import com.letsplay.logic.BoardPosition;
import com.letsplay.logic.PlayChecker;
import com.letsplay.repository.GameSession;
import com.letsplay.service.GameSessionService;
import com.letsplay.ui.BoardTileBuilder;
import com.letsplay.ui.GameArea;
import com.letsplay.ui.GameTile;
import com.letsplay.ui.GameTileBuilder;
import com.letsplay.utils.GameSessionNotFoundException;
import com.vaadin.server.VaadinSession;
import com.vaadin.server.WrappedHttpSession;
import com.vaadin.ui.UI;

@Component
public class PlayUpdateUI {
	
	@Autowired
	private Map<String, WrappedHttpSession> sessionList;
	
	@Autowired
	private GameSessionService gameSessionService;
	
	@Autowired
	BoardTileBuilder boardTileBuilder;
	
	@JmsListener(destination = "${application.play}")
	public void updateBoard(PlayEvent event) {
		if(sessionList.containsKey(event.getNotifyPlayer())) {
			try {
				GameSession gameSession = gameSessionService.findByPlayers(event.getNotifyPlayer());
				WrappedHttpSession session = sessionList.get(event.getNotifyPlayer());

				Collection<VaadinSession> vaadinSessions = VaadinSession.getAllSessions(session.getHttpSession());
				VaadinSession vaaSession = vaadinSessions.iterator().next();
				Collection<UI> uis = vaaSession.getUIs();
				UserPage ui = (UserPage) uis.iterator().next();
				
				PlayChecker playChecker = gameSession.getPlayChecker();
				Set<BoardPosition> positions = playChecker.getCurrentPlay();
				
				for(BoardPosition position: positions) {
					GameTile tile = GameTileBuilder
									.get()
									.setWeight(position.getTileState())
									.build();
					ui.access(new Runnable() {

						@Override
						public void run() {
							
							GameArea gameArea = (GameArea)ui.getContent();
							gameArea.placeGameTile(tile, position.getColumn(), position.getRow());
							ui.push();
						}
						
					});
					
				}
				
			} catch (GameSessionNotFoundException e) {
	
				e.printStackTrace();
			}
			
		}
	}
	
	@JmsListener(destination = "${application.undo}")
	public void undoPlay(UndoPlayEvent event) {
		if(sessionList.containsKey(event.getNotifyPlayer())) {
			try {
				
				// Used to see if there is an active game session.
				@SuppressWarnings("unused")
				GameSession gameSession = gameSessionService.findByPlayers(event.getNotifyPlayer());
				WrappedHttpSession session = sessionList.get(event.getNotifyPlayer());

				Collection<VaadinSession> vaadinSessions = VaadinSession.getAllSessions(session.getHttpSession());
				VaadinSession vaaSession = vaadinSessions.iterator().next();
				Collection<UI> uis = vaaSession.getUIs();
				UserPage ui = (UserPage) uis.iterator().next();
				List<String> boardTiles = event.getBoardTile();
				Iterator<Integer> column = event.getColumn().iterator();
				Iterator<Integer> row = event.getRow().iterator();
				
				for(String tile: boardTiles) {
					ui.access(new Runnable() {

						@Override
						public void run() {
							
							GameArea gameArea = (GameArea)ui.getContent();
							gameArea.undoPlay(boardTileBuilder
												.setImageUrl(tile)
												.build(), column.next(), row.next());
							ui.push();
						}
						
					});
				}
				
			} catch (GameSessionNotFoundException e) {

				e.printStackTrace();
			}
		}
	}
}
