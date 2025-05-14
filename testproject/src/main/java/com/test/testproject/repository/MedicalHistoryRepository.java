package com.test.testproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.testproject.model.MedicalHistory;

public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory, Integer>{

}
