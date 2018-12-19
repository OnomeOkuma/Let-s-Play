package com.letsplay.events.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.letsplay.events.FinalScoreEvent;

@Component
public class FinalScoreListener implements ApplicationListener<FinalScoreEvent> {
	
	@Autowired
	JmsTemplate messagingTemplate;
	
	@Value("${application.finalscore}")
	String destination;
	
	@Override
	public void onApplicationEvent(FinalScoreEvent event) {
		messagingTemplate.convertAndSend(destination, event);
	}

}
