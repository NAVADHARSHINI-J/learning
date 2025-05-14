package com.springboot.rest_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.rest_api.exception.UsernameInvalidException;
import com.springboot.rest_api.model.User;
import com.springboot.rest_api.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder encoder;
	public User signup(User user) throws UsernameInvalidException {
		//check if user is present in the table or not
		User user1=userRepository.findByUsername(user.getUsername());
		if(user1!=null) {
			throw new UsernameInvalidException("Username already exists...");
		}
		//encode the password
		String pass=encoder.encode(user.getPassword());
		//set the encoded password in user
		user.setPassword(pass);
		return userRepository.save(user);
	}

}
