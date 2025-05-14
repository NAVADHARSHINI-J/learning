package com.spring.security.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.security.model.Practice;

@RestController
@RequestMapping("/api/practice")
public class PracticeController {

	
	/*Add asset allocation*/
	@PostMapping("/add")
	public void Add(@RequestBody Practice practice) {
		
	}
	
}
