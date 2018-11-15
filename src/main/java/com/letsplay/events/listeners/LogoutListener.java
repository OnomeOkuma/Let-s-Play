package com.letsplay.events.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.letsplay.events.LogoutEvent;


@Component
public class LogoutListener implements ApplicationListener<LogoutEvent> {
	
	@Autowired
	JmsTemplate messagingTemplate;
	
	@Value("${application.logout}")
	String destination;
	
	
	@Override
	public void onApplicationEvent(LogoutEvent event) {
		messagingTemplate.convertAndSend(destination, event.getUsername());
	}
		
}
