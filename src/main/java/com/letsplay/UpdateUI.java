package com.letsplay;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.dialogs.DefaultConfirmDialogFactory;

import com.letsplay.events.PlayAcceptEvent;
import com.letsplay.events.PlayInviteEvent;
import com.letsplay.logic.Boardstate;
import com.letsplay.logic.PlayChecker;
import com.letsplay.logic.Tilebag;
import com.letsplay.repository.ActivePlayer;
import com.letsplay.repository.GameSession;
import com.letsplay.service.ActivePlayerService;
import com.letsplay.service.GameSessionService;
import com.letsplay.ui.GameArea;
import com.letsplay.utils.ActivePlayerNotFoundException;
import com.letsplay.utils.EmptyTileBagException;
import com.letsplay.utils.GameSessionNotFoundException;
import com.vaadin.server.VaadinSession;
import com.vaadin.server.WrappedHttpSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Notification.Type;

@Component
public class UpdateUI {

	@Autowired
	private Map<String, WrappedHttpSession> sessionList;

	@Autowired
	private ActivePlayerService activePlayers;

	@Autowired
	private GameSessionService gameSessionService;
	
	@Autowired
    private ApplicationEventPublisher applicationEventPublisher;
	
	@JmsListener(destination = "${application.users}")
	public void updatePlayerList(String user) {

		Set<String> players = new HashSet<>();
		List<ActivePlayer> active = activePlayers.findAll();

		for (ActivePlayer player : active) {
			players.add(player.getName());
		}

		for (String userSession : sessionList.keySet()) {

				WrappedHttpSession session = sessionList.get(userSession);

				Collection<VaadinSession> vaadinSessions = VaadinSession.getAllSessions(session.getHttpSession());
				VaadinSession vaaSession = vaadinSessions.iterator().next();
				Collection<UI> uis = vaaSession.getUIs();
				UserPage ui = (UserPage) uis.iterator().next();
				
				Set<String> playerListToSend = new HashSet<>(players);
				playerListToSend.remove(userSession);
				
				ui.access(new Runnable() {

					@Override
					public void run() {
						ui.updatePlayerList(playerListToSend);
						ui.push();
					}

				});

		} 
		
	}
	
	
	@JmsListener(destination = "${application.logout}")
	public void removePlayerFromList(String user) {
		Set<String> players = new HashSet<>();
		List<ActivePlayer> active = activePlayers.findAll();

		for (ActivePlayer player : active) {
			players.add(player.getName());
		}

		for (String userSession : sessionList.keySet()) {

				WrappedHttpSession session = sessionList.get(userSession);

				Collection<VaadinSession> vaadinSessions = VaadinSession.getAllSessions(session.getHttpSession());
				VaadinSession vaaSession = vaadinSessions.iterator().next();
				Collection<UI> uis = vaaSession.getUIs();
				UserPage ui = (UserPage) uis.iterator().next();
				
				Set<String> playerListToSend = new HashSet<>(players);
				playerListToSend.remove(userSession);
				
				ui.access(new Runnable() {

					@Override
					public void run() {
						ui.updatePlayerList(playerListToSend);
						ui.push();
					}

				});

		}
	}
	
	@JmsListener(destination = "${application.invite}")
	public void sentInvite(PlayInviteEvent event) {

		if (sessionList.containsKey(event.getToPlayer())) {
			WrappedHttpSession session = sessionList.get(event.getToPlayer());
			Collection<VaadinSession> vaadinSessions = VaadinSession.getAllSessions(session.getHttpSession());

			VaadinSession vaaSession = vaadinSessions.iterator().next();
			Collection<UI> uis = vaaSession.getUIs();
			UserPage ui = (UserPage) uis.iterator().next();
			DefaultConfirmDialogFactory df = new DefaultConfirmDialogFactory();
			ConfirmDialog cd = df.create("Play Invite", event.getFromPlayer() + " wants to play.", null, null,
					null);

			Button okayButton = cd.getOkButton();

			okayButton.addClickListener(listener -> {

				try {

					ActivePlayer player1 = activePlayers.findByName(event.getFromPlayer());
					ActivePlayer player2 = activePlayers.findByName(event.getToPlayer());
					GameSession gameSession = new GameSession();
					PlayChecker checker = new PlayChecker();
					Boardstate boardState = new Boardstate();
					Tilebag tileBag = new Tilebag();
					
					
					gameSession.setPlayer1(player1);
					gameSession.setPlayer2(player2);
					gameSession.setName(player1.getName() + " vs " + player2.getName());
					gameSession.setBoardState(boardState);
					gameSession.setPlayChecker(checker);
					gameSession.setTileBag(tileBag);
					
					gameSessionService.saveSession(gameSession);
					ui.setGameSessionName(gameSession.getName());
					
					// Add UI GameArea setup logic to synchronize state between both players.
					ui.access(new Runnable() {

						@Override
						public void run() {
							try {
								
								GameSession gameSession = gameSessionService.findByPlayers(ui.getCurrentUser());
								GameArea gameArea = (GameArea)ui.getContent();
								gameArea.clearPlayerRack();
								for(int counter = 0; counter != 7; counter++ ) {
									try {
										gameArea.addTileToRack(gameSession.getTileBag().getTile());
									} catch (EmptyTileBagException e) {
										Notification.show(e.getMessage(), Type.ERROR_MESSAGE);
									}
								}
								
								gameArea.setPlayer2Name(event.getFromPlayer());
								
								gameSessionService.saveSession(gameSession);
								PlayAcceptEvent acceptEvent = new PlayAcceptEvent("Accept");
								acceptEvent.setNotifyPlayer(event.getFromPlayer());
								acceptEvent.setFromPlayer(event.getToPlayer());
								acceptEvent.setGameSessionName(gameSession.getName());
								
								Notification.show("Game, Set, Match", Type.ERROR_MESSAGE);
								applicationEventPublisher.publishEvent(acceptEvent);
								
								ui.push();
							} catch (GameSessionNotFoundException e) {
								
								Notification.show(e.getMessage(), Type.ERROR_MESSAGE);
								
							}
						
						}

					});

					
				} catch (ActivePlayerNotFoundException e) {

					e.printStackTrace();

				}

			});

			ui.access(new Runnable() {

				@Override
				public void run() {
					cd.show(ui, listener -> {}, true);
					ui.push();
				}

			});
		}
	}
	
	@JmsListener(destination = "${application.accept}")
	public void inviteAccepted(PlayAcceptEvent event) {

		if (sessionList.containsKey(event.getNotifyPlayer())) {
			WrappedHttpSession session = sessionList.get(event.getNotifyPlayer());
			Collection<VaadinSession> vaadinSessions = VaadinSession.getAllSessions(session.getHttpSession());

			VaadinSession vaaSession = vaadinSessions.iterator().next();
			Collection<UI> uis = vaaSession.getUIs();
			UserPage ui = (UserPage) uis.iterator().next();
			ui.setGameSessionName(event.getGameSessionName());
			
			// Add UI GameArea setup logic to synchronize state between both players.
			ui.access(new Runnable() {

				@Override
				public void run() {
					GameSession gameSession;
					try {
						gameSession = gameSessionService.findByPlayers(ui.getCurrentUser());
						GameArea gameArea = (GameArea)ui.getContent();
						gameArea.clearPlayerRack();
						for(int counter = 0; counter != 7; counter++ ) {
							try {
								gameArea.addTileToRack(gameSession.getTileBag().getTile());
							} catch (EmptyTileBagException e) {
								Notification.show(e.getMessage(), Type.ERROR_MESSAGE);
							}
						}
						gameSessionService.saveSession(gameSession);
						gameArea.setPlayer2Name(event.getFromPlayer());
						gameArea.yourTurn();
						
					} catch (GameSessionNotFoundException e1) {
						// TODO Auto-generated catch block
						Notification.show(e1.getMessage(), Type.ERROR_MESSAGE);
					}
					
					Notification.show("Game, Set, Match", Type.ERROR_MESSAGE);
					ui.push();

				}

			});
		}
	}
	
	
}
