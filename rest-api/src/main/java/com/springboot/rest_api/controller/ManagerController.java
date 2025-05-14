package com.springboot.rest_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.rest_api.exception.InvalidContactException;
import com.springboot.rest_api.exception.InvalidIdException;
import com.springboot.rest_api.model.Manager;
import com.springboot.rest_api.model.ManagerService;


@RestController
@RequestMapping("/api/manager")
public class ManagerController {
	@Autowired
	private ManagerService managerService;

	// add the record
	@PostMapping("/add")
	public Manager add(@RequestBody Manager manager) throws InvalidContactException {
		return managerService.add(manager);
	}
	
	//get the record by id
	@GetMapping("/get/{id}")
	public Manager getbyId(@PathVariable int id) throws InvalidIdException {
           return managerService.getbyId(id);
	}
	
	// get all the record
	@GetMapping("/getall")
	public List<Manager> getall() {
		return managerService.getAll();
	}
}
