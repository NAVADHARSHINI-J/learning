package com.hexa.assetmanagement.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.hexa.assetmanagement.exception.InvalidIdException;
import com.hexa.assetmanagement.exception.UsernameInvalidException;
import com.hexa.assetmanagement.model.User;
import com.hexa.assetmanagement.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	Logger logger=LoggerFactory.getLogger("UserService");

	/*1. check whether the user already exists
	 * 2. check the role if not present then set role as EMPLOYEE
	 * 3. encode the password
	 * 4. set password in user
	 * 5. save the user*/
	public User signup(User user) throws UsernameInvalidException {
		// check if user is present in the table or not
		User user1 = userRepository.findByUsername(user.getUsername());
		if (user1 != null) {
			throw new UsernameInvalidException("Username already exists...");
		}
		// check the role is given or not
		if (user.getRole() == null)
			user.setRole("EMPLOYEE");
		// encode the password
		String pass = encoder.encode(user.getPassword());
		// set the encoded password in user
		user.setPassword(pass);
		return userRepository.save(user);
	}

	public User getById(int id) throws InvalidIdException {
		// get the user from id
		Optional<User> user = userRepository.findById(id);
		if (user.isEmpty())
			throw new InvalidIdException("the user id is invalid....");
		return user.get();
	}

	public void reset(String username, User user) {
		//find the user by using the username from the db 
	   User user1=userRepository.findByUsername(username);
	   //bcrypt the new password
	   String password = encoder.encode(user.getPassword());
	   //update the old password with new password
	   user1.setPassword(password);
	   logger.info("The password is reseted for user "+ username);
	   //save the user
	   userRepository.save(user1);
	}

	public void deleteUser(User user) {
		userRepository.delete(user);
		
	}

}







