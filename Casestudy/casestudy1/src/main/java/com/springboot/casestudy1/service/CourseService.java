package com.springboot.casestudy1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.casestudy1.exception.InvalidIdException;
import com.springboot.casestudy1.model.Course;

@Service
public class CourseService {
	
	@Autowired
	private CourseRepository courseRepository;
	public Course addCourse(Course c) {
		return courseRepository.save(c);
	}
	public List<Course> getAll() {
		return courseRepository.findAll();
	}
	public Course getOne(int id) throws InvalidIdException {
		Optional<Course> op = courseRepository.findById(id);
		if(op.isEmpty())
			throw new InvalidIdException("The Id is Invalid");
		return op.get();
	}
	
}
