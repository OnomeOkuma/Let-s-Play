package com.letsplay;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.quinto.dawg.CompressedDAWGSet;
import org.quinto.dawg.ModifiableDAWGSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.letsplay.service.GameSessionService;
import com.letsplay.ui.BoardTileBuilder;
import com.vaadin.server.WrappedHttpSession;
import com.vaadin.spring.annotation.EnableVaadin;

@Configuration
@EnableVaadin
@EnableAutoConfiguration
@EnableJpaRepositories
@EnableJms
public class ScrabbleConfig {
	
	@Value("${event.pool.max}")
	int maxPool;
	
	@Value("${event.pool.core}")
	int corePool;
	
	@Autowired
	GameSessionService gameSessionService;
	
	@Autowired
	ApplicationEventPublisher applicationEventPublisher;
	

	@Bean
	@Scope("singleton")
	public CompressedDAWGSet getDawg() {
		ModifiableDAWGSet temp = new ModifiableDAWGSet();

		try {
			temp.addAll(this.getClass().getResourceAsStream("/META-INF/WordList.txt"));
			return temp.compress();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Unable to Create Dictionary");
		}

	}
	
	@Bean
	public PasswordEncoder getEncoder() {
		
		BCryptPasswordEncoder encrypt = new BCryptPasswordEncoder(9);
		return encrypt;
		
	}
			
	@Bean
	@Scope("singleton")
	public Map<String, WrappedHttpSession> getSessionTable(){
		
		return new ConcurrentHashMap<String,WrappedHttpSession>();
	
	}
	
	@Bean
	public ApplicationEventMulticaster simpleApplicationEventMulticaster() {
		SimpleApplicationEventMulticaster multicast = new SimpleApplicationEventMulticaster();
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setMaxPoolSize(maxPool);
		executor.setCorePoolSize(corePool);
		
		multicast.setTaskExecutor(executor);
		
		return multicast;
		
	}
	
	@PostConstruct
	public void setBoardTileServices() {
		BoardTileBuilder.gameSessionService = gameSessionService;
		BoardTileBuilder.applicationEventPublisher = applicationEventPublisher;
	}
	
}

