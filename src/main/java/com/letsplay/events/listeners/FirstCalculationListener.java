package com.letsplay.events.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.letsplay.events.FirstCalculationEvent;


@Component
public class FirstCalculationListener implements ApplicationListener<FirstCalculationEvent> {

	@Autowired
	JmsTemplate messagingTemplate;
	
	@Value("${application.firstcalculation}")
	String destination;
	
	@Override
	public void onApplicationEvent(FirstCalculationEvent event) {
		
		messagingTemplate.convertAndSend(destination, event);
		
	}

}
