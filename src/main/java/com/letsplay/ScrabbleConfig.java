package com.letsplay;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.quinto.dawg.CompressedDAWGSet;
import org.quinto.dawg.ModifiableDAWGSet;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.vaadin.server.WrappedHttpSession;
import com.vaadin.spring.annotation.EnableVaadin;

@Configuration
@EnableVaadin
@EnableAutoConfiguration
@EnableJpaRepositories
@EnableJms
public class ScrabbleConfig {
	
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
	public Collection<WrappedHttpSession> getSessionList(){
		return new ConcurrentLinkedQueue<WrappedHttpSession>();
	}
		
	@Bean
	@Scope("singleton")
	public Map<String, WrappedHttpSession> getSessionTable(){
		
		return new ConcurrentHashMap<String,WrappedHttpSession>();
	
	}
}
