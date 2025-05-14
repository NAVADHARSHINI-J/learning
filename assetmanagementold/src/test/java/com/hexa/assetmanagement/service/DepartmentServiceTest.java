package com.hexa.assetmanagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.hexa.assetmanagement.exception.InvalidIdException;
import com.hexa.assetmanagement.model.Department;
import com.hexa.assetmanagement.repository.DepartmentRepository;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {

	@Mock
	private DepartmentRepository departmentRepository;

	@InjectMocks
	private DepartmentService departmentService;

	Department d1;
	Department d2;
	Department d3;

	@BeforeEach
	public void init() {

		d1 = new Department(1, "IT");
		d2 = new Department(2, "FINANCE");
		d3 = new Department(3, "HR");
	}

	@Test
	public void addDepartment() {

		// case 1: checking for correct output.
		when(departmentRepository.save(d1)).thenReturn(d1);

		assertEquals(d1, departmentService.addDepartment(d1));
		
		//case 2: checking for incorrect output.
		
		assertNotEquals(d2, departmentService.addDepartment(d1));

		//verifying if departmentRepository is being called or not.
		verify(departmentRepository, times(2)).save(d1);
	}
	
	@Test
	public void getById() {
		
		//case 1: checking for correct output.
		
		when(departmentRepository.findById(1)).thenReturn(Optional.of(d1));
		
		try {
			assertEquals(d1, departmentService.getById(1));
		} catch (InvalidIdException e) { 
		}
		
		//case 2: checking for incorrect output
		
		try {
			assertNotEquals(d2, departmentService.getById(1));
		} catch (InvalidIdException e) { 
			assertEquals("Department Id is invalid....", e.getMessage());
		}
		
		//verifying if departmentRepository is being called or not.
		verify(departmentRepository, times(2)).findById(1);
	}
	
	@Test
	public void getAll() {
		
		//case 1: checking for correct output.
		
		List<Department> list = Arrays.asList(d1,d2,d3);
		when(departmentRepository.findAll()).thenReturn(list);
		
		assertEquals(list, departmentService.getAll());
		assertEquals(3, departmentService.getAll().size());
		
		//case 2: checking for incorrect output
		
		assertNotEquals(4, departmentService.getAll().size());
		
		//verifying if departmentRepository is being called or not.
		verify(departmentRepository, times(3)).findAll();
	}
}
