package com.letsplay.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivePlayerRepository extends JpaRepository<ActivePlayer, Long> {
	
	public Optional<ActivePlayer> findByName(String name);
	public void deleteByName(String name);
	
}
