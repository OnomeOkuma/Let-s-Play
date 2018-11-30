package com.letsplay.events.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.letsplay.events.ScoreEvent;

@Component
public class ScoreListener implements ApplicationListener<ScoreEvent> {
	

	@Autowired
	JmsTemplate messagingTemplate;
	
	@Value("${application.score}")
	String destination;
	
	
	@Override
	public void onApplicationEvent(ScoreEvent event) {
		
		messagingTemplate.convertAndSend(destination, event);
		
	}

}
