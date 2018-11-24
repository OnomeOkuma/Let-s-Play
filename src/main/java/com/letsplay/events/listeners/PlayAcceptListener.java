package com.letsplay.events.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.letsplay.events.PlayAcceptEvent;

@Component
public class PlayAcceptListener implements ApplicationListener<PlayAcceptEvent> {
	
	@Autowired
	JmsTemplate messagingTemplate;
	
	@Value("${application.accept}")
	String destination;
	
	@Override
	public void onApplicationEvent(PlayAcceptEvent event) {
		
		messagingTemplate.convertAndSend(destination, event);
		
	}

}
