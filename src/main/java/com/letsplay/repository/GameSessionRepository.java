package com.letsplay.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameSessionRepository extends JpaRepository<GameSession, Long>{
	
	Optional<GameSession> findByPlayer1(ActivePlayer player1);
	Optional<GameSession> findByPlayer2(ActivePlayer player2);
	Optional<GameSession> findByName(String name);
	
	public void deleteByPlayer1(ActivePlayer player1);
	public void deleteByPlayer2(ActivePlayer player2);
	
	
}
