package com.casestudy.mocktest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.casestudy.mocktest.config.JwtUtil;
import com.casestudy.mocktest.dto.TokenDto;
import com.casestudy.mocktest.model.User;
import com.casestudy.mocktest.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody User user) {
		userService.add(user);
		return ResponseEntity.ok("User added...");
	}

	@PostMapping("/token/generate")
	public TokenDto generateToken(@RequestBody User user,TokenDto dto) {
		// save the credentials in the authenticationas ref
		Authentication auth = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

		// authenticate the credentials
		authenticationManager.authenticate(auth);
		// generate the token
		String token = jwtUtil.generateToken(user.getUsername());
		dto.setToken(token);
		dto.setExpiry(jwtUtil.extractExpiration(token).toString());
		return dto;
	}
}













