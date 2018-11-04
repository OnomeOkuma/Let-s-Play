package com.letsplay.service;

import com.letsplay.repository.Player;

public interface SignupService{
	
	public boolean isUserRegistered(String player);
	public void registerUser(Player player);
	
}
