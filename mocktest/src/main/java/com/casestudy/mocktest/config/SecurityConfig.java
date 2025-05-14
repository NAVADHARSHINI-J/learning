package com.casestudy.mocktest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	private JwtFilter jwtFilter;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
		 //cross site reference forgery to run post we have to disable this
		.csrf((csrf)->csrf.disable())     
			.authorizeHttpRequests((authorize) -> authorize
				.requestMatchers("/api/user/token/generate").permitAll()
				.requestMatchers("/api/user/signup").permitAll()
				.requestMatchers("/api/author/add/{uId}").hasAuthority("ADMIN")
				.requestMatchers("/api/book/add").hasAuthority("AUTHOR")
				
				.anyRequest().permitAll()
			)
			.sessionManagement(session->session
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	
	@Bean
    BCryptPasswordEncoder encoder() {
    	return new BCryptPasswordEncoder();
    }
	
	@Bean
	AuthenticationManager getManager(AuthenticationConfiguration auth) throws Exception {
		return auth.getAuthenticationManager();
	}
}








