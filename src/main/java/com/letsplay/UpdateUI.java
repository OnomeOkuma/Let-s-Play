package com.letsplay;

import java.util.Collection;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.vaadin.server.VaadinSession;
import com.vaadin.server.WrappedHttpSession;
import com.vaadin.ui.UI;


@Component
public class UpdateUI {
	
	@Autowired
	Collection<WrappedHttpSession> sessions;
	
	@JmsListener(destination = "users")
	public void updatePlayerList(String user) {
		for (WrappedHttpSession session : sessions) {
			Collection<VaadinSession> vaadinSessions = VaadinSession.getAllSessions(session.getHttpSession());
			VaadinSession vaaSession = vaadinSessions.iterator().next();
			Collection<UI> uis = vaaSession.getUIs();
			System.out.print("");
		}
	}
}
