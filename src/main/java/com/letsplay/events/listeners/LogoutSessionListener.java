package com.letsplay.events.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.letsplay.events.LogoutSessionEvent;

@Component
public class LogoutSessionListener implements ApplicationListener<LogoutSessionEvent> {
	
	@Autowired
	JmsTemplate messagingTemplate;
	
	@Value("${application.endgameplay}")
	String destination;
	
	@Override
	public void onApplicationEvent(LogoutSessionEvent event) {
		
		messagingTemplate.convertAndSend(destination, event);
		
	}

}
