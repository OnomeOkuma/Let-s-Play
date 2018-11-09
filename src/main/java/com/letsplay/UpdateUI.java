package com.letsplay;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.letsplay.repository.ActivePlayer;
import com.letsplay.service.ActivePlayerService;
import com.vaadin.server.VaadinSession;
import com.vaadin.server.WrappedHttpSession;
import com.vaadin.ui.UI;


@Component
public class UpdateUI {
	
	@Autowired
	Collection<WrappedHttpSession> sessions;
	
	@Autowired
	ActivePlayerService activePlayers;
	
	@JmsListener(destination = "users")
	public void updatePlayerList(String user) {
		
		Set<String> players = new HashSet<>();
		List<ActivePlayer> active = activePlayers.findAll();
		
		for(ActivePlayer player : active) {
			players.add(player.getName());
		}
		
		for (WrappedHttpSession session : sessions) {
			Collection<VaadinSession> vaadinSessions = VaadinSession.getAllSessions(session.getHttpSession());
			VaadinSession vaaSession = vaadinSessions.iterator().next();
			Collection<UI> uis = vaaSession.getUIs();
			UserPage ui = (UserPage) uis.iterator().next();
			ui.access(new Runnable() {

				@Override
				public void run() {
					ui.updatePlayerList(players);
					ui.push();
				}
				
			});
			
		}
	}
}
