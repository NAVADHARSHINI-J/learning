package com.hexa.assetmanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexa.assetmanagement.exception.InvalidIdException;
import com.hexa.assetmanagement.model.Department;
import com.hexa.assetmanagement.repository.DepartmentRepository;

@Service
public class DepartmentService {

	@Autowired
	private DepartmentRepository departmentRepository;

	public Department addDepartment(Department department) {
		//saving the department information.
		return departmentRepository.save(department);
	}

	public Department getById(int departmentId) throws InvalidIdException {
		//check if the department id exists or not.
		Optional<Department> op = departmentRepository.findById(departmentId);
		//if not threw an exception.
		if (op.isEmpty())
			throw new InvalidIdException("Department Id is invalid....");
		return op.get();
	}

	public List<Department> getAll() {
		//returning the list of departments.
		return departmentRepository.findAll();
	}

}
