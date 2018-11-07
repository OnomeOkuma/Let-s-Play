package com.letsplay;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;


@Component
public class UpdateUI {
	
	@JmsListener(destination = "users")
	public void updatePlayerList(String user) {
		
	}
}
