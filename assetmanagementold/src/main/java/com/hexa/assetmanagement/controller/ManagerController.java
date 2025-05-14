package com.hexa.assetmanagement.controller;

import java.security.Principal;
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
import com.hexa.assetmanagement.model.Manager;
import com.hexa.assetmanagement.service.ManagerService;

@RestController
@RequestMapping("/api/manager")
@CrossOrigin(origins = {"http://localhost:5173"})
public class ManagerController {
	
    @Autowired
	private ManagerService managerService;
	
    @PostMapping("/add")
    public Manager add(@RequestBody Manager manager, Principal principal) throws InvalidContactException, UsernameInvalidException {
        // Get username of logged-in user
        String username = principal.getName();
        return managerService.add(manager, username);
    }

	
	@GetMapping("/getall")
	public List<Manager> getall() {
		//get all manager details
		return managerService.getAll();
	}
	
	@GetMapping("/getbyid/{ManagerId}")
	public Manager getById(@PathVariable int ManagerId) throws InvalidIdException {
		// get manager based on the id
		return managerService.getById(ManagerId);
	}
	
	@PutMapping("/update/{ManagerId}")
	public Manager update(@RequestBody Manager manager,
			@PathVariable int ManagerId) throws InvalidIdException, InvalidContactException {
		//update manager details based in the manager id
		return managerService.update(manager,ManagerId);
	}
}










