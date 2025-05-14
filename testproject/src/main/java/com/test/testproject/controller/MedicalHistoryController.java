package com.test.testproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.testproject.model.MedicalHistory;
import com.test.testproject.service.MedicalHistoryService;

@RestController
@RequestMapping("/api/medical/history")
public class MedicalHistoryController {

	@Autowired
	private MedicalHistoryService medicalHistoryService;

	@PostMapping("/add")
	public MedicalHistory add(@RequestBody MedicalHistory medicalHistory) {
		return medicalHistoryService.add(medicalHistory);
	}
}
