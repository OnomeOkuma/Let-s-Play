package com.letsplay.service;

import com.letsplay.repository.GameSession;
import com.letsplay.utils.GameSessionNotFoundException;

public interface GameSessionService {
	
	public GameSession findByPlayers(String player) throws GameSessionNotFoundException;
	
	public GameSession findBySessionName(String name) throws GameSessionNotFoundException;
	
	public void saveSession(GameSession session);
	
	public void deleteGameSession(GameSession session);
	
}
