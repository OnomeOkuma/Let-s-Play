package com.letsplay;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

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

import com.vaadin.spring.annotation.EnableVaadin;

@Configuration
@EnableVaadin
@EnableAutoConfiguration
@EnableJpaRepositories
@EnableJms
public class ScrabbleConfig {

	private static final Map<String, HttpSession> sessions = new HashMap<>();
	 
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
		
		System.out.println(encrypt.encode("admin"));
		
		return new BCryptPasswordEncoder(9);
	}
	
	@Bean
    public HttpSessionListener httpSessionListener() {
        return new HttpSessionListener() {
            @Override
            public void sessionCreated(HttpSessionEvent hse) {
                sessions.put(hse.getSession().getId(), hse.getSession());
            }

            @Override
            public void sessionDestroyed(HttpSessionEvent hse) {
                sessions.remove(hse.getSession().getId());
            }
        };
    }
}

