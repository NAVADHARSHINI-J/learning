package com.hexa.assetmanagement.service;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hexa.assetmanagement.exception.InvalidContactException;
import com.hexa.assetmanagement.exception.InvalidIdException;
import com.hexa.assetmanagement.exception.UsernameInvalidException;
import com.hexa.assetmanagement.model.Manager;
import com.hexa.assetmanagement.model.User;
import com.hexa.assetmanagement.repository.ManagerRepository;
import com.hexa.assetmanagement.repository.UserRepository;

@Service
public class ManagerService {
	@Autowired
	private ManagerRepository managerRepository;
	@Autowired
	private UserRepository userRepository;
    @Autowired
	private UserService userService;
    
	Logger logger=LoggerFactory.getLogger("ManagerService");
	
	public Manager add(Manager manager, String username) throws InvalidContactException, UsernameInvalidException {
	    // Get the user by username
	    User user = userRepository.findByUsername(username);
	    // Validate contact number
	    if (manager.getContact().length() != 10) {
	        throw new InvalidContactException("Invalid contact number...");
	    }
	    user.setRole("MANAGER");
        user=userService.signup(user);
	    // Attach the user to manager
	    manager.setUser(user);
	    logger.info("Manager is added: " + username);
	    return managerRepository.save(manager);
	} 

	public List<Manager> getAll() {
		//get all the manager details
		return managerRepository.findAll();
	}

	public Manager getById(int ManagerId) throws InvalidIdException {
		//check whether the manager is found in the given id
		Optional<Manager> op=managerRepository.findById(ManagerId);
		if(op.isEmpty())
			throw new InvalidIdException("Manager id is invalid.....");
		logger.info("Manager is found"+ManagerId);
		return op.get();
	}

	public Manager update(Manager manager, int id) throws InvalidIdException, InvalidContactException {
		Manager manager1=getById(id);
		// check whether the values is null or not if not null update the existing manager
		if(manager.getName() != null)
			manager1.setName(manager.getName());
		if(manager.getEmail() != null)
			manager1.setEmail(manager.getEmail());
		// check whether the contact number is correct or else throw an exception
		if(manager.getContact()!=null) {
		if(manager.getContact().length() != 10)
			throw new InvalidContactException("Invalid contact number...");
		manager1.setContact(manager.getContact());
		}
		if(manager.getAddress() != null)
			manager1.setAddress(manager.getAddress());
		logger.info("Manager" +manager1.getName()+"Updated Manager sucessfully");
		return managerRepository.save(manager1);
	}
	
	
}

