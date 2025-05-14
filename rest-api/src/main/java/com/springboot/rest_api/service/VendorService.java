package com.springboot.rest_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.rest_api.exception.InvalidIdException;
import com.springboot.rest_api.exception.UsernameInvalidException;
import com.springboot.rest_api.model.User;
import com.springboot.rest_api.model.Vendor;
import com.springboot.rest_api.repository.VendorRepository;

@Service
public class VendorService {
	@Autowired
	private UserService userService;
	 
	@Autowired
	private VendorRepository vendorRepository;
	public Vendor add(Vendor vendor) throws UsernameInvalidException {
		//get the user
		User user=vendor.getUser();
		user.setRole("VENDOR");
		//save the user
		user=userService.signup(user);
		//set in vendor
		vendor.setUser(user);
		return vendorRepository.save(vendor);
	}

	public List<Vendor> getAll() {
		return vendorRepository.findAll();
	}

	public Vendor getById(int vId) throws InvalidIdException {
		Optional<Vendor> optional = vendorRepository.findById(vId);
		if(optional.isEmpty())
			throw new InvalidIdException("Vendor Id is invalid...");
		return optional.get();
	}

	public Vendor getByUsername(String username) {
		return vendorRepository.findByUserUsername(username);
	}

}
