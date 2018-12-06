package com.letsplay.ui;

import java.util.Map;

import org.quinto.dawg.CompressedDAWGSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.vaadin.spring.security.shared.VaadinSharedSecurity;
import org.vaadin.teemu.switchui.Switch;

import com.letsplay.UserPage;
import com.letsplay.events.LogoutEvent;
import com.letsplay.events.LogoutSessionEvent;
import com.letsplay.events.ScoreEvent;
import com.letsplay.events.UndoPlayEvent;
import com.letsplay.repository.ActivePlayer;
import com.letsplay.repository.GameSession;
import com.letsplay.service.ActivePlayerService;
import com.letsplay.service.GameSessionService;
import com.letsplay.utils.GameSessionNotFoundException;
import com.vaadin.server.WrappedHttpSession;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Notification.Type;

@SpringComponent
@UIScope
public class GameButtons extends CustomComponent {

	private Logger logger = LoggerFactory.getLogger(GameButtons.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = -9182052284893399264L;
	private Scoreboard scoreBoard1;
	private Scoreboard scoreBoard2;
	private Switch turn;
	@Autowired
	VaadinSharedSecurity vaadinSecurity;

	@Autowired
	Map<String, WrappedHttpSession> sessionList;

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	@Autowired
	ActivePlayerService activePlayerService;

	@Autowired
	CompressedDAWGSet wordChecker;

	@Autowired
	GameSessionService gameSessionService;

	public GameButtons() {

		VerticalLayout layout = new VerticalLayout();
		this.scoreBoard1 = new Scoreboard();
		this.scoreBoard2 = new Scoreboard();

		Button playButton = new Button("Play");
		this.turn = new Switch("Your Turn");

		turn.setReadOnly(true);
		turn.setAnimationEnabled(true);
		turn.setStyleName("compact");

		playButton.setWidth("160px");
		playButton.addClickListener(click -> {
			UserPage userPage = (UserPage) UI.getCurrent();
			GameArea gameArea = (GameArea) userPage.getContent();
			if (gameArea.isYourTurn()) {

				try {

					GameSession gameSession = gameSessionService.findBySessionName(userPage.getGameSessionName());
					try {
						if (gameSession.getPlayChecker().check(wordChecker, gameSession.getBoardState())) {

							int score = gameSession.getPlayChecker().calculatePlay(gameSession.getBoardState());
							scoreBoard1.setScore(score);
							gameSession.getPlayChecker().finalizePlay(gameSession.getBoardState(),
									gameSession.getTileBag());

							gameArea.notYourTurn();

							ScoreEvent event = new ScoreEvent("Score");

							if (gameSession.getPlayer1().getName().equals(userPage.getCurrentUser()))
								event.setNotifyPlayer(gameSession.getPlayer2().getName());
							else
								event.setNotifyPlayer(gameSession.getPlayer1().getName());

							event.setScore(score);

							applicationEventPublisher.publishEvent(event);

						} else {

							UndoPlayEvent event = gameSession.getPlayChecker().undoPlay(gameSession.getBoardState());
							if (gameSession.getPlayer1().getName().equals(userPage.getCurrentUser()))
								event.setNotifyPlayer(gameSession.getPlayer2().getName());
							else
								event.setNotifyPlayer(gameSession.getPlayer1().getName());

							applicationEventPublisher.publishEvent(event);
						}
					} catch (Exception e) {
						Notification.show(e.getMessage(), Type.ERROR_MESSAGE);
					}

					gameSessionService.saveSession(gameSession);

				} catch (GameSessionNotFoundException e) {

					Notification.show("User has not entered into a Gaming Session", Type.ERROR_MESSAGE);

				}
			} else {
				Notification.show("It is not your turn", Type.ERROR_MESSAGE);
			}
		});

		Button exchangeTileButton = new Button("Exchange Tile");
		exchangeTileButton.setWidth("160px");
		exchangeTileButton.addClickListener(click -> {
			UserPage userPage = (UserPage) UI.getCurrent();
			GameArea gameArea = (GameArea) userPage.getContent();
			if (gameArea.isYourTurn()) {

				TileExchange exchange = new TileExchange();
				UI.getCurrent().addWindow(exchange);

			} else {
				Notification.show("It is not your turn", Type.ERROR_MESSAGE);
			}
		});

		Button skipTurnButton = new Button("Pass");
		skipTurnButton.setWidth("160px");
		skipTurnButton.addClickListener(click -> {

			UserPage userPage = (UserPage) UI.getCurrent();
			GameArea gameArea = (GameArea) userPage.getContent();
			if (gameArea.isYourTurn()) {
				try {
					GameSession gameSession = gameSessionService.findBySessionName(userPage.getGameSessionName());
					ScoreEvent event = new ScoreEvent("Score");
					if (gameSession.getPlayer1().getName().equals(userPage.getCurrentUser()))
						event.setNotifyPlayer(gameSession.getPlayer2().getName());
					else
						event.setNotifyPlayer(gameSession.getPlayer1().getName());

					event.setScore(0);
					applicationEventPublisher.publishEvent(event);
					gameArea.notYourTurn();

				} catch (GameSessionNotFoundException e) {

					Notification.show("User has not entered into a Gaming Session", Type.ERROR_MESSAGE);

				}

			} else {
				Notification.show("It is not your turn", Type.ERROR_MESSAGE);
			}

		});
		Button surrenderButton = new Button("Surrender");
		surrenderButton.setWidth("160px");

		Button logoutButton = new Button("Log Out");
		logoutButton.addClickListener(listener -> {

			String userToLogout = vaadinSecurity.getAuthentication().getName();
			sessionList.remove(userToLogout);
			UserPage userPage = (UserPage) UI.getCurrent();
			if (!userPage.getGameSessionName().equals("")) {
				try {

					GameSession gameSession = gameSessionService.findBySessionName(userPage.getGameSessionName());
					ActivePlayer player1 = gameSession.getPlayer1();
					ActivePlayer player2 = gameSession.getPlayer2();

					LogoutSessionEvent event = new LogoutSessionEvent("logout");
					if (player1.getName().equals(userPage.getCurrentUser()))
						event.setNotifyPlayer(player2.getName());
					else
						event.setNotifyPlayer(player1.getName());

					gameSessionService.deleteGameSession(gameSession);
					applicationEventPublisher.publishEvent(event);

				} catch (GameSessionNotFoundException e) {
					
					e.printStackTrace();
				}
			}
			vaadinSecurity.logout();
			activePlayerService.deleteByName(userToLogout);
			logger.info("Logout complete.............");

			LogoutEvent event = new LogoutEvent(this, userToLogout);
			applicationEventPublisher.publishEvent(event);

		});

		logoutButton.setWidth("160px");

		layout.addComponents(this.scoreBoard1, this.scoreBoard2, turn, playButton, exchangeTileButton, skipTurnButton,
				surrenderButton, logoutButton);

		setCompositionRoot(layout);

	}

	public void setPlayer1Name(String name) {
		this.scoreBoard1.setName(name);
	}

	public void setPlayer2Name(String name) {
		this.scoreBoard2.setName(name);
	}

	public void setPlayer2Score(int score) {
		this.scoreBoard2.setScore(score);
	}

	public void yourTurn() {
		this.turn.setValue(true);
	}

	public void notYourTurn() {
		this.turn.setValue(false);
	}

	public boolean isYourTurn() {
		return this.turn.getValue();
	}
}
