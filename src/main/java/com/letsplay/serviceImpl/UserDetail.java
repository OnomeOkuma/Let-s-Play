package com.letsplay.serviceImpl;

import java.util.ArrayList;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.letsplay.repository.Player;
import com.letsplay.repository.PlayerRepository;

@Service
@Transactional
public class UserDetail implements UserDetailsService{
	
	@Autowired
	PlayerRepository playerRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<Player> player = playerRepo.findByUserName(username);
		if(!player.isPresent())
			
			throw new UsernameNotFoundException("Invalid Username");
		
		else {
			
			ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>(2);
			authorities.add(new SimpleGrantedAuthority("PLAYER"));
			User user = new User(player.get().getUserName(), player.get().getPassword(),authorities);
			return user;
			
		}
		
	}

}
