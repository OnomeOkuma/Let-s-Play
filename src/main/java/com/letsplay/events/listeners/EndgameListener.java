package com.letsplay.events.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.letsplay.events.EndgameEvent;

@Component
public class EndgameListener implements ApplicationListener<EndgameEvent> {
	
	@Autowired
	JmsTemplate messagingTemplate;
	
	@Value("${application.endgame}")
	String destination;
	
	@Override
	public void onApplicationEvent(EndgameEvent event) {
		messagingTemplate.convertAndSend(destination, event);
	}

}
