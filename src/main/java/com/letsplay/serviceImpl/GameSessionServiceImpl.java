package com.letsplay.serviceImpl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letsplay.repository.ActivePlayer;
import com.letsplay.repository.ActivePlayerRepository;
import com.letsplay.repository.GameSession;
import com.letsplay.repository.GameSessionRepository;
import com.letsplay.service.GameSessionService;
import com.letsplay.utils.GameSessionNotFoundException;

@Service
@Transactional
public class GameSessionServiceImpl implements GameSessionService {
	
	private Logger logger = LoggerFactory.getLogger(GameSessionServiceImpl.class);
	
	@Autowired
	GameSessionRepository gameSessionRepo;
	
	@Autowired
	ActivePlayerRepository activePlayerRepo;
	
	@Override
	public GameSession findByPlayers(String player) throws GameSessionNotFoundException{
		Optional<ActivePlayer> temp = activePlayerRepo.findByName(player);

		if (temp.isPresent()) {

			Optional<GameSession> result = gameSessionRepo.findByPlayer1(temp.get());
			if (result.isPresent())
				return result.get();
			else {
				result = gameSessionRepo.findByPlayer2(temp.get());
				if (result.isPresent())
					return result.get();
				else
					throw new GameSessionNotFoundException("GameSession not found");
			}
		} else 
			 throw new GameSessionNotFoundException("GameSession not found");
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
