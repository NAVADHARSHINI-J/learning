package com.test.testproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.testproject.model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Integer>{

}
