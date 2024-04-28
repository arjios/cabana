package com.arjios.cabanas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
//@CrossOrigin("*")
//@EnableWebSecurity
//@EnableMethodSecurity
public class WebSecurityConfig {
	
	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf((csrf) -> csrf 
				.disable()
			);
		http.headers((headers) -> headers
				.frameOptions((frameOptions) -> frameOptions
						.disable()));
		http.cors((cors) -> cors 
				.disable()
			);		
		http.authorizeHttpRequests((auth) -> auth
				.anyRequest().permitAll()
			);
		return http.build();
	}
	
}
