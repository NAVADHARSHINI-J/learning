package com.casestudy.mocktest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.casestudy.mocktest.model.User;
import com.casestudy.mocktest.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public void add(User user) {
		//check the user is new
		User user1=userRepository.findByUsername(user.getUsername());
		if(user1 != null) {
			throw new RuntimeException("username already exists....");
		}
		//encode the password
		String password=encoder.encode(user.getPassword());
		//set the password in user
		user.setPassword(password);
		
		userRepository.save(user);
	}


}
