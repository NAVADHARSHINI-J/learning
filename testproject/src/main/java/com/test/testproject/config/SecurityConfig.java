package com.test.testproject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.test.testproject.service.MyService;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private MyService myService;
	@Autowired
	private JwtFilter jwtFilter;
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
		 //cross site reference forgery to run post we have to disable this
				.csrf(csrf ->csrf.disable())
				.authorizeHttpRequests((authorize) -> authorize
				.requestMatchers("/api/auth/token/generate").permitAll()
				.requestMatchers("/api/auth/signup").permitAll()
				.requestMatchers("/api/auth/login").authenticated()
				.requestMatchers("/api/auth/private").authenticated()
				.requestMatchers("/api/auth/public").permitAll()
				.requestMatchers("/api/patient/add").hasAuthority("PATIENT")
				.requestMatchers("/api/doctor/add").hasAuthority("DOCTOR")
				.requestMatchers("/api/appointment/add/{doctorId}/{patientId}").permitAll()
				.requestMatchers("/api/appointment/byDoctor").hasAuthority("DOCTOR")
				.anyRequest().authenticated()
			)
				.sessionManagement(session->session
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	AuthenticationProvider myAuth() {
		DaoAuthenticationProvider dao=new DaoAuthenticationProvider();
		dao.setPasswordEncoder(encodePass());
		dao.setUserDetailsService(myService);
		return dao ;
	}
	@Bean
	BCryptPasswordEncoder encodePass(){
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager getAuthentication(AuthenticationConfiguration auth) 
			throws Exception {
		return auth.getAuthenticationManager();
	}
}
