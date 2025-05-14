package com.test.testproject.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.testproject.config.JwtUtil;
import com.test.testproject.dto.TokenDto;
import com.test.testproject.exception.UsernameInvalidException;
import com.test.testproject.model.User;
import com.test.testproject.service.MyService;
import com.test.testproject.service.UserService;


@RestController
@RequestMapping("/api/auth")
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
	
//	This api is used to generate the token
//	In the first step we have to make a authentication ref and put the username details in it
//	and then we have to authenticate the details for that we have to use the authentication
//	manager first we have to create a bean of authenticationManager in securityconfig file
//	by reference of the AuthenticationConfig
	@PostMapping("/token/generate")
 	public TokenDto generateToken(@RequestBody User user,TokenDto dto) {
 		/*Step 1. Build authentication ref based on username,passord given*/
 		Authentication auth = 
 		new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
 	
 		authenticationManager.authenticate(auth);
 		
 		/*Step 2: Generate the token since we know that credentials are correct */
 		String token =  jwtUtil.generateToken(user.getUsername()); 
 		dto.setToken(token);
 		dto.setUsername(user.getUsername());
 		dto.setExpiry(jwtUtil.extractExpiration(token).toString());
 		return dto; 
 	}
	
	@GetMapping("/private")
	public void sayHelloPrivate() {
		System.out.println("Hello in private....");
	}
	
	@GetMapping("/public")
	public void sayHelloPublic() {
		System.out.println("Hello in public....");
	}
	
}