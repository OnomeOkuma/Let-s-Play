package com.letsplay.serviceImpl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letsplay.repository.ActivePlayer;
import com.letsplay.repository.ActivePlayerRepository;
import com.letsplay.service.ActivePlayerService;
import com.letsplay.utils.ActivePlayerNotFoundException;

@Service
@Transactional
public class ActivePlayerServiceImpl implements ActivePlayerService{
	
	@Autowired
	ActivePlayerRepository activePlayerRepo;
	
	@Override
	public ActivePlayer findByName(String name) throws ActivePlayerNotFoundException {
		Optional<ActivePlayer> player = activePlayerRepo.findByName(name);
		if (player.isPresent())
			return player.get();
		else
			throw new ActivePlayerNotFoundException("Player is offline");
	}

	@Override
	public List<ActivePlayer> findAll() {
		
		return activePlayerRepo.findAll();
		
	}

	@Override
	public void deleteByName(String name) {
		
		activePlayerRepo.deleteByName(name);
		
	}

	@Override
	public void create(String name) {
		
		ActivePlayer activePlayer = new ActivePlayer();
		activePlayer.setName(name);
		activePlayerRepo.save(activePlayer);
		
	}

}
