package com.test.testproject.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.testproject.dto.PatientDto;
import com.test.testproject.exception.InvalidIdException;
import com.test.testproject.model.MedicalHistory;
import com.test.testproject.model.Patient;
import com.test.testproject.model.User;
import com.test.testproject.repository.PatientRepository;
import com.test.testproject.repository.UserRepository;

@Service
public class PatientService {
	@Autowired
	private PatientRepository patientRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private MedicalHistoryService historyService;

	public Patient addPatient(PatientDto patientDto, String username) {
		//get the user from the username
		User user=userRepository.findByUsername(username);
		//add the field of medical history
		MedicalHistory history=new MedicalHistory();
		history.setIllness(patientDto.getIllness());
		history.setNum_of_years(patientDto.getNum_of_years());
		history.setCurrent_medication(patientDto.getCurrent_medication());
		//save the medical history 
		historyService.add(history);
		//set the history in patient
		//set the values for the patient
		Patient patient=new Patient();
		patient.setName(patientDto.getName());
		patient.setAge(patientDto.getAge());
		//set the user and the medical history
		patient.setUser(user);
		patient.setMedicalHistory(history);
		//save the patient
		return patientRepository.save(patient);
	}
	
	public Patient getById(int pId) throws InvalidIdException {
		Optional<Patient> patient=patientRepository.findById(pId);
		if(patient.isEmpty())
			throw new InvalidIdException("Patient Id is Invalid");
		return patient.get();
	}

}
