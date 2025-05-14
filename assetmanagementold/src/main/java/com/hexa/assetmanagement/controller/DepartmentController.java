package com.hexa.assetmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexa.assetmanagement.exception.InvalidIdException;
import com.hexa.assetmanagement.model.Department;
import com.hexa.assetmanagement.service.DepartmentService;

@RestController
@RequestMapping("/api/department")
@CrossOrigin(origins = "http://localhost:5173/")
public class DepartmentController {
	@Autowired
	private DepartmentService departmentService;
	
	@PostMapping("/add")
	public Department addDepartment(@RequestBody Department department) {
		//adding a new department.
		return departmentService.addDepartment(department);
	}

	@GetMapping("/getbyid/{departmentId}")
	public Department getById(@PathVariable int departmentId) throws InvalidIdException {
		//getting a particular department with it's id.
		return departmentService.getById(departmentId);
	}
	
	@GetMapping("/getall")
	public List<Department> getAll(){
		//getting the list of departments exists.
		return departmentService.getAll();
	}
}
