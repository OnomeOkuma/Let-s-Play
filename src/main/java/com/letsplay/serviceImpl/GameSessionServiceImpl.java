package com.letsplay.serviceImpl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.letsplay.repository.ActivePlayer;
import com.letsplay.repository.GameSession;
import com.letsplay.repository.GameSessionRepository;
import com.letsplay.service.GameSessionService;
import com.letsplay.utils.GameSessionNotFoundException;

public class GameSessionServiceImpl implements GameSessionService {
	
	private Logger logger = LoggerFactory.getLogger(GameSessionServiceImpl.class);
	
	@Autowired
	GameSessionRepository gameSessionRepo;
	
	@Override
	public GameSession findByPlayers(String player) throws GameSessionNotFoundException{
		ActivePlayer temp = new ActivePlayer();
		Optional<GameSession> result = gameSessionRepo.findByPlayer1(temp);
		if (result.isPresent())
			return result.get();
		else {
			result = gameSessionRepo.findByPlayer2(temp);
			if(result.isPresent())
				return result.get();
			else 
				throw new GameSessionNotFoundException("GameSession not found");
		}
	}

	@Override
	public void saveSession(GameSession session) {
		gameSessionRepo.save(session);
		logger.info(".....................Game Session created.................");
	}

	@Override
	public void deleteGameSession(GameSession session) {
		gameSessionRepo.delete(session);
		logger.info(".....................Game Session deleted.................");
	}

	@Override
	public GameSession findBySessionName(String name) throws GameSessionNotFoundException {

		Optional<GameSession> result = gameSessionRepo.findByName(name);
		if(result.isPresent())
			return result.get();
		else
			throw new GameSessionNotFoundException("GameSession not found");

	}

}
