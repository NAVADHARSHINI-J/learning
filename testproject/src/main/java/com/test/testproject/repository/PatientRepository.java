package com.test.testproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.testproject.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer>{

}
