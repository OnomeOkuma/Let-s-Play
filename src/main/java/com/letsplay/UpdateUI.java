package com.letsplay;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.vaadin.dialogs.ConfirmDialog;

import com.letsplay.events.PlayInviteEvent;
import com.letsplay.repository.ActivePlayer;
import com.letsplay.service.ActivePlayerService;
import com.vaadin.server.VaadinSession;
import com.vaadin.server.WrappedHttpSession;
import com.vaadin.ui.UI;

@Component
public class UpdateUI {

	@Autowired
	Map<String, WrappedHttpSession> sessionList;

	@Autowired
	ActivePlayerService activePlayers;

	@JmsListener(destination = "users")
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
	
	
	@JmsListener(destination = "logout")
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
	
	@JmsListener(destination = "invite")
	public void sentInvite(PlayInviteEvent event) {
		
		WrappedHttpSession session = sessionList.get(event.getToPlayer());
		Collection<VaadinSession> vaadinSessions = VaadinSession.getAllSessions(session.getHttpSession());
		
		VaadinSession vaaSession = vaadinSessions.iterator().next();
		Collection<UI> uis = vaaSession.getUIs();
		UserPage ui = (UserPage) uis.iterator().next();
		
		ConfirmDialog dialog = new ConfirmDialog();
		
		dialog.setMessage(event.getFromPlayer() + " wants to play");
		
		dialog.show(ui, listener -> {}, true);
		
	}
}
