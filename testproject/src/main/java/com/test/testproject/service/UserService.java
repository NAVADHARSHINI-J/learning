package com.test.testproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.test.testproject.exception.UsernameInvalidException;
import com.test.testproject.model.User;
import com.test.testproject.repository.UserRepository;

@Service
public class UserService  {
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

