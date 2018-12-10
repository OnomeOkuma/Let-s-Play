package com.letsplay.service;

import com.letsplay.repository.Player;
import com.letsplay.utils.PlayerNotFoundException;

public interface SignupService{
	
	public boolean isUserRegistered(String player);
	public void registerUser(Player player);
	public Player getPlayer(String username) throws PlayerNotFoundException; 
	public void updatePlayer(Player player);
}
