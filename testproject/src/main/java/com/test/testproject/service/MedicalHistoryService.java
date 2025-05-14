package com.test.testproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.test.testproject.model.MedicalHistory;
import com.test.testproject.repository.MedicalHistoryRepository;

@Service
public class MedicalHistoryService {

	@Autowired
	private MedicalHistoryRepository historyRepository;
	
	public MedicalHistory add(MedicalHistory medicalHistory) {
		return historyRepository.save(medicalHistory);
	}
	
	
}
