package com.springboot.rest_api.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.rest_api.exception.InvalidIdException;
import com.springboot.rest_api.model.Review;
import com.springboot.rest_api.service.ReviewService;

@RestController
@RequestMapping("/api/review")
public class ReviewController {
	@Autowired
	private ReviewService reviewService;
	
	@PostMapping("/add/{pId}")
	public ResponseEntity<?> add(@RequestBody Review review,@PathVariable int pId,Principal p) 
			throws InvalidIdException {
		//get the username
		String username=p.getName();
		reviewService.add(review,pId,username);
		return ResponseEntity.ok("Review added successfully");
	}
	
	@GetMapping("/byproduct/{pId}")
	public List<Review> getByProductId(@PathVariable int pId) throws InvalidIdException {
		return reviewService.getByProductId(pId);
		
	}
	
	@GetMapping("/bycustomer/{cusId}")
	public List<Review> getByCustomerId(@PathVariable int cusId) throws InvalidIdException {
		return reviewService.getByCustomerId(cusId);
		
	}
}
