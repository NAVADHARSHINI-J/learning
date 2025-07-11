package com.casestudy.mocktest.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.casestudy.mocktest.model.Student;
import com.casestudy.mocktest.repository.StudentRepository;

@Service
public class StudentService {

	@Autowired
	private StudentRepository studentRepository;
	
	public Student add(Student student) {
		return studentRepository.save(student);
	}

	public Page<Student> getAll(Pageable pageable) {
		return studentRepository.findAll(pageable);
	}

	public void delete(int sid) {
 		studentRepository.deleteById(sid);
 		
 	}
	public Student getStudent(int sid) {
 		Optional<Student> optional =  studentRepository.findById(sid);
 		if(optional.isEmpty())
 			throw new RuntimeException("Student id invalid");
 		return optional.get();
 	}
}
