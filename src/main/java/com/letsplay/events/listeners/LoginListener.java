package com.letsplay.events.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.letsplay.events.LoginEvent;

@Component
public class LoginListener implements ApplicationListener<LoginEvent>{
	
	@Autowired
	JmsTemplate messagingTemplate;
	
	@Value("${application.users}")
	String destination;
	
	@Override
	public void onApplicationEvent(LoginEvent event) {
		messagingTemplate.convertAndSend(destination, event.getUsername());
	}

}
