package com.letsplay;

import java.util.Map;
import java.util.Set;

import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.context.ContextLoaderListener;

import com.letsplay.events.LoginEvent;
import com.letsplay.ui.GameArea;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.WrappedHttpSession;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.VaadinSessionScope;
import com.vaadin.spring.server.SpringVaadinServlet;
import com.vaadin.ui.UI;

@SpringUI(path = "/home")
@Theme("mytheme")
@VaadinSessionScope
@PreserveOnRefresh
@Push
public class UserPage extends UI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1258878106012788721L;
	
	@Autowired
	private GameArea gameArea;
	
	@Autowired
	private Map<String, WrappedHttpSession> sessionList;
	
	@Autowired
    private ApplicationEventPublisher applicationEventPublisher;
	
	private String currentUser;
	
	private String gameSessionName;
	
	@Override
	protected void init(VaadinRequest request) {
		this.setContent(gameArea);
		
		
		WrappedHttpSession wrapper = (WrappedHttpSession) request.getWrappedSession();
		
		if(!sessionList.containsKey(request.getRemoteUser())) {
			
			this.setCurrentUser(request.getRemoteUser());
			this.setGameSessionName("");
			this.gameArea.setPlayer1Name(request.getRemoteUser());
			sessionList.put(request.getRemoteUser(), wrapper);
			LoginEvent loginEvent = new LoginEvent(this, request.getRemoteUser());
			applicationEventPublisher.publishEvent(loginEvent);
		}
	}
	
	public void updatePlayerList(Set<String> username) {
		this.gameArea.updateUsersList(username);
	}
	
	public String getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(String currentUser) {
		this.currentUser = currentUser;
	}

	public String getGameSessionName() {
		return gameSessionName;
	}

	public void setGameSessionName(String gameSessionName) {
		this.gameSessionName = gameSessionName;
	}

	@WebListener
    public static class MyContextLoaderListener extends ContextLoaderListener {
		
    }
	
	
	@WebServlet(urlPatterns = "/*", name = "UserPageUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = UserPage.class, productionMode = false)
	public static class UserPageUIServlet extends SpringVaadinServlet {

		/**
		 * 
		 */
		private static final long serialVersionUID = 2982260643338511845L;
		
	
	}
}

