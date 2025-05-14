package com.test.testproject.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.testproject.exception.InvalidIdException;
import com.test.testproject.model.Doctor;
import com.test.testproject.model.User;
import com.test.testproject.repository.DoctorRepository;
import com.test.testproject.repository.UserRepository;

@Service
public class DoctorService {
	@Autowired
	private DoctorRepository doctorRepository;
	@Autowired
	private UserRepository userRepository;

	public Doctor add(Doctor doctor, String username) {
		//get the user from the username
		User user=userRepository.findByUsername(username);
		//set the user in doctor
		doctor.setUser(user);
		//save the doctor
		return doctorRepository.save(doctor);
	}
	
	public Doctor getById(int dId) throws InvalidIdException {
		Optional<Doctor> doctor=doctorRepository.findById(dId);
		if(doctor.isEmpty())
			throw new InvalidIdException("Doctor Id is Invalid");
		return doctor.get();
	}

}
