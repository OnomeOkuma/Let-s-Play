package com.letsplay.events.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.letsplay.events.UndoPlayEvent;

@Component
public class UndoPlayListener implements ApplicationListener<UndoPlayEvent> {

	@Autowired
	JmsTemplate messagingTemplate;
	
	@Value("${application.undo}")
	String destination;
	
	@Override
	public void onApplicationEvent(UndoPlayEvent event) {
		// TODO Auto-generated method stub
		messagingTemplate.convertAndSend(destination, event);
		
	}

}
