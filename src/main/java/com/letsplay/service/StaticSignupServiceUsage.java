package com.letsplay.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.letsplay.repository.Player;

@Component
public class StaticSignupServiceUsage {
	
	private static SignupService signupStaticService;

	@Autowired 
	private SignupService signupService;
	
	
	@PostConstruct
    public void init() {
		
		StaticSignupServiceUsage.signupStaticService = signupService;

    }
	
	public static void registerUser(Player player) {
		StaticSignupServiceUsage.signupStaticService.registerUser(player);
	}
	
	
}