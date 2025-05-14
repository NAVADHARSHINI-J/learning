package com.test.testproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.testproject.exception.InvalidIdException;
import com.test.testproject.model.DoctorPatient;
import com.test.testproject.model.Patient;
import com.test.testproject.service.AppointmentService;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {

	@Autowired
	private AppointmentService appointmentService;
	
	@PostMapping("/add/{doctorId}/{patientId}")
	public DoctorPatient addAppointment(@RequestBody DoctorPatient doctorPatient,
			@PathVariable int doctorId,@PathVariable int patientId) throws InvalidIdException {
		
		return appointmentService.addAppointment(doctorPatient,doctorId,patientId);
	}
	
	@GetMapping("/byDoctor")
	public List<Patient> filterByDoctor(@RequestParam int doctorId)
			throws InvalidIdException{
		return appointmentService.filterByDoctor(doctorId);
	}
}
