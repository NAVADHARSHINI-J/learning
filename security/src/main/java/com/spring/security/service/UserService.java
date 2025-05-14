package com.spring.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.security.exception.InvalidUsernameException;
import com.spring.security.model.User;
import com.spring.security.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public User signup(User user) throws InvalidUsernameException {
		User user1=userRepository.findByUsername(user.getUsername());
		if(user1 != null)
			throw new InvalidUsernameException("Username already Exists.....");
		//encode the password
		String enpass=encoder.encode(user.getPassword());
		//set in user
		user.setPassword(enpass);
		return userRepository.save(user);
	}

}
