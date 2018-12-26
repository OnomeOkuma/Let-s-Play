package com.letsplay.events.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.letsplay.events.SecondCalculationEvent;

@Component
public class SecondCalculationListener implements ApplicationListener<SecondCalculationEvent> {
	
	@Autowired
	JmsTemplate messagingTemplate;
	
	@Value("${application.secondcalculation}")
	String destination;
	
	@Override
	public void onApplicationEvent(SecondCalculationEvent event) {
		
		messagingTemplate.convertAndSend(destination, event);
		
	}

}
