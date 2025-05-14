package com.test.testproject.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.testproject.model.Doctor;
import com.test.testproject.service.DoctorService;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController {

	@Autowired
	private DoctorService doctorService;
	
	@PostMapping("/add")
	public Doctor add(@RequestBody Doctor doctor,Principal principal) {
		//get the username from principal
		String username=principal.getName();
		return doctorService.add(doctor,username);
	}
}
