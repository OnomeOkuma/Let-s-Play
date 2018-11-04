package com.letsplay.logic;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.VaadinSessionScope;

@SpringComponent
@VaadinSessionScope
public class Gamestate implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7707420706264666012L;

	@Autowired
	public Boardstate boardState;
	
	@Autowired
	public PlayChecker playChecker;
	
	@Autowired
	public Tilebag tileBag;
	
	public Gamestate(){	
	
	}
	
}

