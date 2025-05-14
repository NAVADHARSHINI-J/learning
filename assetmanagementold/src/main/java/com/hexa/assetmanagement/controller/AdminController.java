package com.hexa.assetmanagement.controller;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexa.assetmanagement.exception.InvalidContactException;
import com.hexa.assetmanagement.exception.InvalidIdException;
import com.hexa.assetmanagement.exception.UsernameInvalidException;
import com.hexa.assetmanagement.model.Admin;
import com.hexa.assetmanagement.service.AdminService;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:5173/")
public class AdminController {
	@Autowired
	private AdminService adminService;
	
	/* adding the admin in the signup page*/
	@PostMapping("/add")
	public Admin add(@RequestBody Admin admin) throws InvalidContactException,
	UsernameInvalidException {
		 return adminService.add(admin);
	}
	
	//getting all the admin in the table
	@GetMapping("/getall")
	public List<Admin> getall() {
		return adminService.getAll();
	}
	
	//get admin by the id
	@GetMapping("/getbyid/{adminId}")
	public Admin getById(@PathVariable int adminId) throws InvalidIdException {
		return adminService.getById(adminId);
	}
	
	//update the admin
	@PutMapping("/update/{adminId}")
	public Admin update(@RequestBody Admin admin,
			@PathVariable int adminId) throws InvalidIdException, InvalidContactException {
		return adminService.update(admin,adminId);
	}
	
	//get the admin by using the username
	@GetMapping("/byuser/{user}")
	public Admin getByUser(@PathVariable String user) {
		return adminService.getByUser(user);
	}
}