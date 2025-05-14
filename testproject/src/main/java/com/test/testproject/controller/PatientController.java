package com.test.testproject.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.testproject.dto.PatientDto;
import com.test.testproject.model.Patient;
import com.test.testproject.service.PatientService;

@RestController
@RequestMapping("/api/patient")
public class PatientController {
	@Autowired
	private PatientService patientService;

	@PostMapping("/add")
	public Patient addPatient(@RequestBody PatientDto patientDto,Principal principal) {
		//get the username
		String username=principal.getName();
		return patientService.addPatient(patientDto,username);
	}
}
