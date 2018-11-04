package com.letsplay.serviceImpl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.letsplay.repository.Player;
import com.letsplay.repository.PlayerRepository;
import com.letsplay.service.SignupService;

@Service
@Transactional
@Scope("singleton")
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
		Player temp = playerRepository.findByUserName(player);
		if(temp == null)
			return false;
		else return true;
	}

	@Override
	public void registerUser(Player player) {
		player.setPassword(passwordEncoder.encode(player.getPassword()));
		playerRepository.save(player);

	}

}
