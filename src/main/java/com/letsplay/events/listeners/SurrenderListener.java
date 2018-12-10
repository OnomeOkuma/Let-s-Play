package com.letsplay.events.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.letsplay.events.SurrenderEvent;


@Component
public class SurrenderListener implements ApplicationListener<SurrenderEvent> {
	
	@Autowired
	JmsTemplate messagingTemplate;
	
	@Value("${application.surrender}")
	String destination;
	
	@Override
	public void onApplicationEvent(SurrenderEvent event) {
		
		messagingTemplate.convertAndSend(destination, event);
		
	}

}
