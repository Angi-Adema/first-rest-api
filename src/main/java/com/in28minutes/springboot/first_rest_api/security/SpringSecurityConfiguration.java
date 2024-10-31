package com.in28minutes.springboot.first_rest_api.security;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfiguration {
	//LDAP or Database
	//In Memory 
	
	//InMemoryUserDetailsManager
	//InMemoryUserDetailsManager(UserDetails... users)
	
	@Bean
	public InMemoryUserDetailsManager createUserDetailsManager() {
		
		UserDetails userDetails1 = createNewUser("admin", "password");
		UserDetails userDetails2 = createNewUser("ranga", "dummydummy");
		
		// This is what configures the Spring security users for us. (configuration for in memory users)
		return new InMemoryUserDetailsManager(userDetails1, userDetails2);
	}

	// Here we use BCrypt to encode the user login info.
	private UserDetails createNewUser(String username, String password) {
		Function<String, String> passwordEncoder
		= input -> passwordEncoder().encode(input);

		UserDetails userDetails = User.builder()
									.passwordEncoder(passwordEncoder)
									.username(username)
									.password(password)
									.roles("USER","ADMIN")
									.build();
		return userDetails;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	//All URLs are protected
	//A login form is shown for unauthorized requests
	//CSRF disable
	//Frames
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {   // The filter chain works by intercepting every request and executes a filter chain then executes the request.
		
		// Authenticate every http request.
		http.authorizeHttpRequests(
				auth -> auth.anyRequest().authenticated());
		
		// Instead of http.formLogin() we use httpBasic.
		http.httpBasic(withDefaults());
		
		http.csrf(csrf -> csrf.disable());  // This will cause our POST and PUT requests to be disabled so we comment this out.
		
		// If using an h2 console, we do not want to use frameOptions either so we will disable this as well.
		http.headers(headers -> headers.frameOptions(frameOptionsConfig-> frameOptionsConfig.disable()));
		
		return http.build();
	}
	
	
	
	
	
}