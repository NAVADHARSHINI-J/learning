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
import com.hexa.assetmanagement.model.Admin;
import com.hexa.assetmanagement.model.User;
import com.hexa.assetmanagement.repository.AdminRepository;

@Service
public class AdminService {
	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private UserService userService;

	Logger logger=LoggerFactory.getLogger("AdminService");
	
	/*1. get the user from admin
	 * 2. save the user using signup method
	 * 3. set the user in admin
	 * 4. check the contact length
	 * 5. save the admin*/
	public Admin add(Admin admin) 
			throws InvalidContactException, UsernameInvalidException {
		//get the user from admin
		User user=admin.getUser();
		//check the role
		user.setRole("ADMIN");
		//save this user
		userService.signup(user);
		//check the contact
		if(admin.getContact().length() != 10)
			throw new InvalidContactException("Invalid contact number...");
		//set the user in the admin
		admin.setUser(user);
		logger.info("Admin is added with username "+ user.getUsername());
		return adminRepository.save(admin);
	}

	/* get all the admin using the findAll method */
	public List<Admin> getAll() {
		//get all the admin in the db
		return adminRepository.findAll();
	}

	/*1.get the admin by using the findById method 
	 * 2.if found return it else throw the invalidIdException*/
	public Admin getById(int AdminId) throws InvalidIdException {
		//check whether the admin found with the given id
		Optional<Admin> op=adminRepository.findById(AdminId);
		if(op.isEmpty())
			throw new InvalidIdException("Admin id is invalid.....");
		logger.info("admin is found with id "+AdminId);
		return op.get();
	}

	/*1.get the admin by using the id
	 * 2.check the field of new admin is null if not null set the values for old admin
	 * 3.also check the conatct length 
	 * 4. add the admin*/
	public Admin update(Admin admin, int adminId) 
			throws InvalidIdException, InvalidContactException {
		Admin admin1=getById(adminId);
		//check whether each value in admin is null or not 
       //if not null add that in the existing admin
		if(admin.getName() != null)
			admin1.setName(admin.getName());
		if(admin.getEmail() != null)
			admin1.setEmail(admin.getEmail());
		// check whether the contact number length is 10 else throw an exception
		if(admin.getContact()!=null) {
		if(admin.getContact().length() != 10)
			throw new InvalidContactException("Invalid contact number...");
		admin1.setContact(admin.getContact());
		}
		if(admin.getAddress() != null)
			admin1.setAddress(admin.getAddress());
		
		logger.info("Admin "+admin1.getName()+" updated successfully ");
		return adminRepository.save(admin1);
	}

	/* find the admin by using the username => method findByUserUsername */
	public Admin getByUser(String user) {
		return adminRepository.findByUserUsername(user);
	}
}









