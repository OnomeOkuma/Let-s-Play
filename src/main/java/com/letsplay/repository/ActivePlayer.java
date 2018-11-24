package com.letsplay.repository;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ActivePlayer {
	
	@Id
	private String name;
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public boolean equals(Object activePlayer) {
		
		ActivePlayer temp = (ActivePlayer) activePlayer; 
		if(temp.name.equals(this.name))
			return true;
		
		return false;
	}
}
