package com.casestudy.mocktest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.casestudy.mocktest.dto.PageDto;
import com.casestudy.mocktest.model.Student;
import com.casestudy.mocktest.service.StudentService;

@RestController
@RequestMapping("/api/student")
@CrossOrigin(origins = "http://localhost:5173/")
public class StudentController {

	@Autowired
	private StudentService studentService;
	@Autowired
	private PageDto pageDto;
	
	@PostMapping("/add")
	public Student add(@RequestBody Student student) {
		return  studentService.add(student);
	}
	
	@GetMapping("/all")
	public PageDto getAll(@RequestParam int page,@RequestParam int size) {
		Pageable pageable=PageRequest.of(page, size);
		Page<Student> s=studentService.getAll(pageable);
		pageDto.setCurrentPage(page);
		pageDto.setList(s.getContent());
		pageDto.setSize(s.getSize());
		pageDto.setTotalElements(s.getNumberOfElements());
		pageDto.setTotalPages(s.getTotalPages());
		return pageDto;
	}
	@DeleteMapping("/delete/{sid}")
 	public void delete(@PathVariable int sid) {
 		studentService.delete(sid); 
 	}
	@PutMapping("/update/{sid}")
 	public Student update(@PathVariable int sid,  @RequestBody Student student) {
 		Student studentDB = studentService.getStudent(sid); 
 		studentDB.setName(student.getName());
 		studentDB.setAge(student.getAge()); 
 		return studentService.add(studentDB);
 	}
}
