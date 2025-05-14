package com.test.testproject.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.test.testproject.enums.Speciality;
import com.test.testproject.exception.InvalidIdException;
import com.test.testproject.model.Doctor;
import com.test.testproject.model.DoctorPatient;
import com.test.testproject.model.Patient;
import com.test.testproject.repository.AppointmentRepository;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AppointmentServiceTest {
	@InjectMocks
	private AppointmentService appointmentService;
	@Mock
	private AppointmentRepository appointmentRepository;
	@Mock
	private DoctorService doctorService;
	@Mock
	private PatientService patientService;
	
	Doctor d1,d2;
	DoctorPatient dp1,dp2,dp3,dp4;
	Patient p1,p2,p3;
	
	@BeforeEach
	public void init() {
		d1=new Doctor(1,"doctor1",Speciality.GYNAC);
		d2=new Doctor(2,"doctor2",Speciality.ORTHO);
		p1=new Patient(1,"patient1",20);
		p2=new Patient(2,"patient2",22);
		p3=new Patient(3,"patient3",25);
		dp1 = new DoctorPatient(1,LocalDate.of(2025, 04, 15), d1, p1);
		dp2 = new DoctorPatient(2,LocalDate.of(2025, 04, 15), d1, p2);
		dp3 = new DoctorPatient(3,LocalDate.of(2025, 04, 15), d2, p3);	
		dp4 = new DoctorPatient(3,null, null, null);	
	}

	@Test
	public void filterByDoctorTest() {
		//expected: List<Product>
		//actual : appointmentService.filterByDoctor(1)
		
		//usecase : 1 (check the correct output came or not
		//create a list of doctor patient
		List<DoctorPatient> doctorPatients=Arrays.asList(dp1,dp2);
		List<Patient> patients=Arrays.asList(p1,p2);
		try {
			when(doctorService.getById(1)).thenReturn(d1);
		} catch (InvalidIdException e) {
		}
		when(appointmentRepository.findByDoctor(d1)).thenReturn(doctorPatients);
		try {
			assertEquals(patients, appointmentService.filterByDoctor(1));
		} catch (InvalidIdException e) { }
		
		//usecase : 2(check for exception)
		try {
			when(doctorService.getById(10)).thenThrow(new InvalidIdException("Doctor Id is Invalid"));
			assertEquals(patients, appointmentService.filterByDoctor(10));
		} catch (InvalidIdException e) {
			assertEquals("Doctor Id is Invalid", e.getMessage());
		}

		verify(appointmentRepository,times(1)).findByDoctor(d1);
	}
	@Test
	public void addAppointmentTest() {
		//usecase :  1 (correct output)
		when(appointmentRepository.save(dp4)).thenReturn(dp4);
		try {
			when(doctorService.getById(1)).thenReturn(d1);
			when(patientService.getById(1)).thenReturn(p1);
		} catch (InvalidIdException e) {
		}
		try {
			assertEquals(dp4,appointmentService.addAppointment(dp4, 1, 1));
		} catch (InvalidIdException e) {		}
        //check that patient is added
		assertEquals(p1,dp4.getPatient());
		//check that doctor is added
		assertEquals(d1,dp4.getDoctor());
		
		//usecase : 2 (exception throws)
		try {
			when(doctorService.getById(10)).thenThrow(new InvalidIdException("Doctor Id is Invalid"));
			assertEquals(dp4,appointmentService.addAppointment(dp4, 10, 1));
		} catch (InvalidIdException e) {	
			assertEquals("Doctor Id is Invalid", e.getMessage());
		}
		
		//usecase : 3 (exception throws)
		try {
			when(patientService.getById(10)).thenThrow(new InvalidIdException("Patient Id is Invalid"));
			assertEquals(dp4,appointmentService.addAppointment(dp4, 1, 10));
		} catch (InvalidIdException e) {	
			assertEquals("Patient Id is Invalid", e.getMessage());
		}
	}
}


