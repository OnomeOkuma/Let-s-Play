package com.letsplay.serviceImpl;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.letsplay.repository.Player;
import com.letsplay.repository.PlayerRepository;
import com.letsplay.service.SignupService;
import com.letsplay.utils.PlayerNotFoundException;

@Service
@Transactional
public class SignupServiceImpl implements SignupService, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1575591976048820427L;

	@Autowired
	private PlayerRepository playerRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public boolean isUserRegistered(String player) {
		Optional<Player> temp = playerRepository.findByUserName(player);
		return temp.isPresent();
	}

	@Override
	public void registerUser(Player player) {
		player.setPassword(passwordEncoder.encode(player.getPassword()));
		playerRepository.save(player);

	}

	@Override
	public Player getPlayer(String username) throws PlayerNotFoundException {
		Optional<Player> temp = playerRepository.findByUserName(username);
		if(temp.isPresent())
			return temp.get();
		else
			throw new PlayerNotFoundException("Player not found");

	}

	@Override
	public void updatePlayer(Player player) {
		
		playerRepository.save(player);
		
	}
	
}
