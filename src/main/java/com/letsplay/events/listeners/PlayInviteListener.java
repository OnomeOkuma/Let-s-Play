package com.letsplay.events.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.letsplay.events.PlayInviteEvent;

@Component
public class PlayInviteListener implements ApplicationListener<PlayInviteEvent> {
	
	@Autowired
	JmsTemplate messagingTemplate;
	
	@Value("${application.invite}")
	String destination;
	
	@Override
	public void onApplicationEvent(PlayInviteEvent event) {
		
		messagingTemplate.convertAndSend(destination, event);
		
	}

}
