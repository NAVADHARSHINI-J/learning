package com.test.testproject.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.testproject.exception.InvalidIdException;
import com.test.testproject.model.Doctor;
import com.test.testproject.model.DoctorPatient;
import com.test.testproject.model.Patient;
import com.test.testproject.repository.AppointmentRepository;


@Service
public class AppointmentService {

	@Autowired
	private AppointmentRepository appointmentRepository;
	@Autowired
	private DoctorService doctorService;
	@Autowired
	private PatientService patientService;

	public DoctorPatient addAppointment(DoctorPatient doctorPatient, int doctorId,
			int patientId) throws InvalidIdException {
		//check the doctor id is valid
		Doctor doctor = doctorService.getById(doctorId);
		//check the patient id
		Patient patient=patientService.getById(patientId);
		//check that the date is given if not make it today date
		if(doctorPatient.getAppointment_date()==null)
			doctorPatient.setAppointment_date(LocalDate.now());
		//set the doctor and patient in appointment
		doctorPatient.setDoctor(doctor);
		doctorPatient.setPatient(patient);
		//save doctorPatient
		return appointmentRepository.save(doctorPatient);
	}

	public List<Patient> filterByDoctor(int doctorId) 
			throws InvalidIdException {
		//get  the doctor from id
		Doctor doctor = doctorService.getById(doctorId);
		//get all the appointment from the doctor
		List<DoctorPatient> appointments=appointmentRepository.findByDoctor(doctor);
		//get all the patient from the appointments
		List<Patient> patients=appointments.stream().map(p->p.getPatient()).toList();
		return patients;
	}
}



