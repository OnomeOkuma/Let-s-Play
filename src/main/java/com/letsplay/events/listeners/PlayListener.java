package com.letsplay.events.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.letsplay.events.PlayEvent;

@Component
public class PlayListener implements ApplicationListener<PlayEvent> {
	
	@Autowired
	JmsTemplate messagingTemplate;
	
	@Value("${application.play}")
	String destination;
	
	@Override
	public void onApplicationEvent(PlayEvent event) {
		
		messagingTemplate.convertAndSend(destination, event);
		
	}

}
