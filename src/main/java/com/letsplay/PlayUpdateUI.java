package com.letsplay;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.letsplay.events.EndgameEvent;
import com.letsplay.events.FinalScoreEvent;
import com.letsplay.events.LogoutSessionEvent;
import com.letsplay.events.PlayEvent;
import com.letsplay.events.ScoreEvent;
import com.letsplay.events.SurrenderEvent;
import com.letsplay.events.UndoPlayEvent;
import com.letsplay.logic.BoardPosition;
import com.letsplay.logic.PlayChecker;
import com.letsplay.logic.Tilestate;
import com.letsplay.repository.GameSession;
import com.letsplay.repository.Player;
import com.letsplay.service.GameSessionService;
import com.letsplay.service.SignupService;
import com.letsplay.ui.BoardTileBuilder;
import com.letsplay.ui.GameArea;
import com.letsplay.ui.GameTile;
import com.letsplay.ui.GameTileBuilder;
import com.letsplay.utils.GameSessionNotFoundException;
import com.letsplay.utils.PlayerNotFoundException;
import com.vaadin.server.VaadinSession;
import com.vaadin.server.WrappedHttpSession;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Notification.Type;

@Component
public class PlayUpdateUI {
	
	@Autowired
	private Map<String, WrappedHttpSession> sessionList;
	
	@Autowired
	private GameSessionService gameSessionService;
	
	@Autowired
	private SignupService playerService;
	
	@Autowired
	private BoardTileBuilder boardTileBuilder;
	
	@Autowired
    private ApplicationEventPublisher applicationEventPublisher;
	
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
	
	@JmsListener(destination = "${application.score}")
	public void setScoreAndTurn(ScoreEvent event) {
		
		if(sessionList.containsKey(event.getNotifyPlayer())) {
			try {
				@SuppressWarnings("unused")
				GameSession gameSession = gameSessionService.findByPlayers(event.getNotifyPlayer());
				WrappedHttpSession session = sessionList.get(event.getNotifyPlayer());
				
				Collection<VaadinSession> vaadinSessions = VaadinSession.getAllSessions(session.getHttpSession());
				VaadinSession vaaSession = vaadinSessions.iterator().next();
				Collection<UI> uis = vaaSession.getUIs();
				UserPage ui = (UserPage) uis.iterator().next();
				
				ui.access(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						
						GameArea gameArea = (GameArea)ui.getContent();
						gameArea.setPlayer2Score(event.getScore());
						gameArea.yourTurn();
						
						ui.push();
					}
					
				});
				
				
			} catch (GameSessionNotFoundException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	@JmsListener(destination = "${application.pass}")
	public void setTurn(ScoreEvent event) {
		
		if(sessionList.containsKey(event.getNotifyPlayer())) {
			try {
				@SuppressWarnings("unused")
				GameSession gameSession = gameSessionService.findByPlayers(event.getNotifyPlayer());
				WrappedHttpSession session = sessionList.get(event.getNotifyPlayer());
				
				Collection<VaadinSession> vaadinSessions = VaadinSession.getAllSessions(session.getHttpSession());
				VaadinSession vaaSession = vaadinSessions.iterator().next();
				Collection<UI> uis = vaaSession.getUIs();
				UserPage ui = (UserPage) uis.iterator().next();
				
				ui.access(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						
						GameArea gameArea = (GameArea)ui.getContent();
						gameArea.yourTurn();
						
						ui.push();
					}
					
				});
				
				
			} catch (GameSessionNotFoundException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	@JmsListener(destination = "${application.endgameplay}")
	public void logoutListener(LogoutSessionEvent event) {
		if(sessionList.containsKey(event.getNotifyPlayer())) {
			WrappedHttpSession session = sessionList.get(event.getNotifyPlayer());
			
			Collection<VaadinSession> vaadinSessions = VaadinSession.getAllSessions(session.getHttpSession());
			VaadinSession vaaSession = vaadinSessions.iterator().next();
			Collection<UI> uis = vaaSession.getUIs();
			UserPage ui = (UserPage) uis.iterator().next();
			
			ui.access(new Runnable() {

				@Override
				public void run() {
					GameArea gameArea = (GameArea)ui.getContent();
					gameArea.reset();
					gameArea.notYourTurn();
					gameArea.resetScorePlayer1();
					gameArea.setPlayer2Name("");
					gameArea.resetScorePlayer2();
					ui.setGameSessionName("");
					Notification.show("Opponent is no longer online", Type.ERROR_MESSAGE);
					
					ui.push();
				}
				
			});
		}
	}	
	
	@JmsListener(destination = "${application.surrender}")
	public void surrender(SurrenderEvent event) {
		if (sessionList.containsKey(event.getWinner())) {
			WrappedHttpSession session = sessionList.get(event.getWinner());

			Collection<VaadinSession> vaadinSessions = VaadinSession.getAllSessions(session.getHttpSession());
			VaadinSession vaaSession = vaadinSessions.iterator().next();
			Collection<UI> uis = vaaSession.getUIs();
			UserPage ui = (UserPage) uis.iterator().next();
			
			try {
				
				Player player1 = playerService.getPlayer(event.getLoser());
				Player player2 = playerService.getPlayer(event.getWinner());
				
				int wins = player2.getWins();
				wins++;
				player2.setWins(wins);
				
				int losses = player1.getLoses();
				if(losses > 0) {
					losses--;
					player1.setLoses(losses);
				}
				
				playerService.updatePlayer(player1);
				playerService.updatePlayer(player2);
				
			} catch (PlayerNotFoundException e) {
				
				e.printStackTrace();
			}
			
			
			ui.access(new Runnable() {

				@Override
				public void run() {
					GameArea gameArea = (GameArea) ui.getContent();
					gameArea.reset();
					gameArea.notYourTurn();
					gameArea.resetScorePlayer1();
					gameArea.setPlayer2Name("");
					gameArea.resetScorePlayer2();
					ui.setGameSessionName("");
					Notification.show("Opponent surrendered. \n You were too awesome for him.", Type.ERROR_MESSAGE);

					ui.push();
				}

			});
		}
	}
	
	@JmsListener(destination = "${application.endgame}")
	public void endgame(EndgameEvent event) {
		if (sessionList.containsKey(event.getLoser())) {
			WrappedHttpSession session = sessionList.get(event.getLoser());
			Collection<VaadinSession> vaadinSessions = VaadinSession.getAllSessions(session.getHttpSession());
			VaadinSession vaaSession = vaadinSessions.iterator().next();
			Collection<UI> uis = vaaSession.getUIs();
			UserPage ui = (UserPage) uis.iterator().next();
			GameArea gameArea = (GameArea)ui.getContent();
			
			List<GameTile> tiles = gameArea.getTilesRemaining();
			int scoreToAddForWinner = 0;
			
			for(GameTile tile : tiles) {
				Tilestate state = tile.getTileState();
				scoreToAddForWinner += state.getWeight();
			}
			
			int winnerScore = event.getWinnerScore() + (2 * scoreToAddForWinner);
			ui.access(new Runnable() {

				@Override
				public void run() {
					gameArea.overWritePlayer2Score(winnerScore);
					Notification.show(event.getWinner() + " is the winner.", Type.ERROR_MESSAGE);
				
				}
				
			});
			FinalScoreEvent finalEvent = new FinalScoreEvent("final score");
			finalEvent.setWinner(event.getWinner());
			finalEvent.setWinnerScore(winnerScore);
			finalEvent.setLoser(event.getLoser());
			finalEvent.setLoserScore(event.getLoserScore());
			
			applicationEventPublisher.publishEvent(finalEvent);
		}
		
	}
	
	@JmsListener(destination = "${application.finalscore}")
	public void finalscore(FinalScoreEvent event) {
		if (sessionList.containsKey(event.getWinner())) {
			WrappedHttpSession session = sessionList.get(event.getWinner());
			Collection<VaadinSession> vaadinSessions = VaadinSession.getAllSessions(session.getHttpSession());
			VaadinSession vaaSession = vaadinSessions.iterator().next();
			Collection<UI> uis = vaaSession.getUIs();
			UserPage ui = (UserPage) uis.iterator().next();
			GameArea gameArea = (GameArea)ui.getContent();
			gameArea.overWritePlayer1Score(event.getWinnerScore());
			gameArea.overWritePlayer2Score(event.getLoserScore());
			
			ui.access(new Runnable() {

				@Override
				public void run() {
					Notification.show("Congratulations \n you are the winner.", Type.ERROR_MESSAGE);
				}
				
			});
		}
	}
}
