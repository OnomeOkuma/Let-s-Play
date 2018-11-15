package com.letsplay.events.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.jms.core.JmsTemplate;

import com.letsplay.events.PlayInviteEvent;

public class PlayInviteListener implements ApplicationListener<PlayInviteEvent> {
	
	@Autowired
	JmsTemplate messagingTemplate;
	
	@Value("${application.logout}")
	String destination;
	
	@Override
	public void onApplicationEvent(PlayInviteEvent event) {
		
		
	}

}
