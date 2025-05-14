package com.test.testproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.testproject.model.Doctor;
import com.test.testproject.model.DoctorPatient;

public interface AppointmentRepository extends JpaRepository<DoctorPatient, Integer>{

	List<DoctorPatient> findByDoctor(Doctor doctor);

}
