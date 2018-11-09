package com.letsplay.service;

import java.util.List;

import com.letsplay.repository.ActivePlayer;
import com.letsplay.utils.ActivePlayerNotFoundException;

public interface ActivePlayerService {
	
	public void create(String name);
	
	public ActivePlayer findByName(String name) throws ActivePlayerNotFoundException;
	
	public List<ActivePlayer> findAll();
	
	public void deleteByName(String name);
	
}
