package com.letsplay.ui;

import java.util.Map;

import org.quinto.dawg.CompressedDAWGSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.vaadin.spring.security.shared.VaadinSharedSecurity;

import com.letsplay.UserPage;
import com.letsplay.events.LogoutEvent;
import com.letsplay.events.UndoPlayEvent;
import com.letsplay.repository.GameSession;
import com.letsplay.service.ActivePlayerService;
import com.letsplay.service.GameSessionService;
import com.letsplay.utils.GameSessionNotFoundException;
import com.vaadin.server.WrappedHttpSession;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SpringComponent
@UIScope
public class GameButtons extends CustomComponent {

	private Logger logger = LoggerFactory.getLogger(GameButtons.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = -9182052284893399264L;
	private Scoreboard scoreBoard;
	
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
	
	@Autowired
	public GameButtons(Scoreboard scoreBoard) {

		VerticalLayout layout = new VerticalLayout();
		this.scoreBoard = scoreBoard;
		Button playButton = new Button("Play");
		playButton.setWidth("160px");
		playButton.addClickListener(click -> {
			
			UserPage userPage = (UserPage) UI.getCurrent();
			try {
				
				GameSession gameSession = gameSessionService.findBySessionName(userPage.getGameSessionName());
				if (gameSession.getPlayChecker().check(wordChecker, gameSession.getBoardState())) {
					scoreBoard.setScore(gameSession.getPlayChecker().calculatePlay(gameSession.getBoardState()));
					gameSession.getPlayChecker().finalizePlay(gameSession.getBoardState(), gameSession.getTileBag());
				} else {
					
					UndoPlayEvent event = gameSession.getPlayChecker().undoPlay(gameSession.getBoardState());
					if(gameSession.getPlayer1().getName().equals(userPage.getCurrentUser()))
						event.setNotifyPlayer(gameSession.getPlayer2().getName());
					else
						event.setNotifyPlayer(gameSession.getPlayer1().getName());
					
					applicationEventPublisher.publishEvent(event);
				}
				
				gameSessionService.saveSession(gameSession);
				
			} catch (GameSessionNotFoundException e) {

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
			
			String userToLogout = vaadinSecurity.getAuthentication().getName();
			sessionList.remove(userToLogout);
			activePlayerService.deleteByName(userToLogout);
			vaadinSecurity.logout();
			
			logger.info("Logout complete.............");
			
			LogoutEvent event = new LogoutEvent(this, userToLogout);
			applicationEventPublisher.publishEvent(event);
			
		});

		logoutButton.setWidth("160px");
		
		layout.addComponents(scoreBoard, playButton, exchangeTileButton, skipTurnButton, surrenderButton, logoutButton);

		setCompositionRoot(layout);

	}

	public void setPlayerName(String name) {
		this.scoreBoard.setName(name);
	}
}
