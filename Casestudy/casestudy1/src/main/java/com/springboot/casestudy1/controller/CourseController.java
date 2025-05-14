package com.springboot.casestudy1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.casestudy1.dto.MessageDto;
import com.springboot.casestudy1.exception.InvalidIdException;
import com.springboot.casestudy1.model.Course;
import com.springboot.casestudy1.service.CourseService;

@RestController
public class CourseController {
	@Autowired
	private CourseService courseService;
	@Autowired
	private MessageDto messageDto;

	 @PostMapping("api/course/add")
	 public Course addCourse(@RequestBody Course c) {
		  return courseService.addCourse(c);
	 }
	
 	 @GetMapping("/api/course/getall")
 	 public List<Course> getAll(){
 		 return courseService.getAll();
 	 }
 	 
 	 @GetMapping("/api/course/one/{id}")
 	 public ResponseEntity<?> getOne(@PathVariable int id) {
 		 try {
			return ResponseEntity.ok(courseService.getOne(id));
		} catch (InvalidIdException e) {
			messageDto.setMessage(e.getMessage());
			messageDto.setStatusCode(400);
			return ResponseEntity.status(400).body(messageDto);
		}
 	 }
}
