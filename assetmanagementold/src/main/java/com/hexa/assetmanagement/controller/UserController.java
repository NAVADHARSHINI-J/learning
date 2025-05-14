package com.hexa.assetmanagement.controller;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexa.assetmanagement.config.JwtUtil;
import com.hexa.assetmanagement.dto.TokenDto;
import com.hexa.assetmanagement.exception.InvalidIdException;
import com.hexa.assetmanagement.exception.UsernameInvalidException;
import com.hexa.assetmanagement.model.User;
import com.hexa.assetmanagement.service.MyService;
import com.hexa.assetmanagement.service.UserService;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = {"http://localhost:5173"})
public class UserController {
	@Autowired
    private UserService userService;
	@Autowired
	private MyService myService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtil jwtUtil;
	
	@PostMapping("/signup")
	public User signup(@RequestBody User user) throws UsernameInvalidException {
		return userService.signup(user);
	}
	
	@PostMapping("/login")
 	public UserDetails login(Principal principal) {
 		/* Make this login as Authenticated API 
 		 * If this method is called, it means that Spring Filter alreeady
 		 * has correct username/password
 		 * 
 		 * Can i ask spring filter to share these username and password  with me?
 		 * -- yes but only username, spring filter never ever shares user password 
 		 * */
 		String username = principal.getName();
 		return myService.loadUserByUsername(username);
 	}
	@GetMapping("/getbyid/{id}")
	public User getById(@PathVariable int id) throws InvalidIdException {
		return userService.getById(id);
	}
	
	@PostMapping("/token/generate")
	public TokenDto generateToken(@RequestBody User user,TokenDto dto) {
		Authentication auth=
			new UsernamePasswordAuthenticationToken(user.getUsername(), 
					user.getPassword());
		//verify the auth
		authenticationManager.authenticate(auth);
		
		//create a token for userrname
		String token=jwtUtil.generateToken(user.getUsername());
		//set the dto values
		dto.setToken(token);
		dto.setUsername(jwtUtil.extractUsername(token));
		dto.setExpiry(jwtUtil.extractExpiration(token).toString());
		return dto;
	}
	
	@GetMapping("/user/details")
 	public UserDetails getUserDetails(Principal principal) {
 		String username = principal.getName();
 		return myService.loadUserByUsername(username);
 	}
	
	//api to reset the password
	@PostMapping("/reset")
	public ResponseEntity<?> reset(@RequestBody User user,Principal principal) {
		//get the username from pricipal and fetch the info from db
		String username = principal.getName();
 		userService.reset(username,user);
 		return ResponseEntity.ok("Password reseted successfully...");
	}
}






