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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.hexa.assetmanagement.exception.InvalidContactException;
import com.hexa.assetmanagement.exception.InvalidIdException;
import com.hexa.assetmanagement.exception.UsernameInvalidException;
import com.hexa.assetmanagement.model.Department;
import com.hexa.assetmanagement.model.Employee;
import com.hexa.assetmanagement.model.User;
import com.hexa.assetmanagement.repository.EmployeeRepository;
import com.hexa.assetmanagement.repository.UserRepository;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

	@InjectMocks
	private EmployeeService employeeService;

	@Mock
	private EmployeeRepository employeeRepository;

	@Mock
	private UserRepository userRepository;
	
	@Mock
    private BCryptPasswordEncoder encoder;

	Employee e1;
	Employee e2;
	Employee e3;
	Employee e4;
	Employee e5;
	Employee e6;
	Employee e66;
	Employee oldEmployee;
	Employee newEmployee;

	@BeforeEach
	public void init() {
		e1 = new Employee(1, "sheryl", "sheryl@gmail.com", "9076234187", "no.22, 4th street", new Department(1, "IT"),
				new User(1, "sheryl", "1234", "EMPLOYEE"));
		e2 = new Employee(2, "nava", "nava@gmail.com", "8025671893", "no.32, 5th street", new Department(2, "FINANCE"),
				new User(2, "nava", "1234", "EMPLOYEE"));
		// e3 with invalid contact
		e3 = new Employee(3, "dharshini", "dharshini@gmail.com", "9372907", "no.29, 4th street",
				new Department(3, "HR"), new User(3, "dharshini", "1234", "EMPLOYEE"));
		// e4 and e5 with same names.
		e4 = new Employee(4, "pooja", "pooja@gmail.com", "8248810291", "no.82, 2nd street", new Department(1, "IT"),
				new User(4, "pooja", "1234", "EMPLOYEE"));
		e5 = new Employee(5, "pooja", "pooja@123.com", "8167230291", "no.29, 1st street", new Department(2, "FINANCE"),
				new User(5, "pooja", "1234", "EMPLOYEE"));
		
		//employee for checking add() method 
		//which adds an employee through their login
		e6 = new Employee(6, "varshaa", "varshaa@gmail.com", "9127392681", "address", new Department(1, "IT"));
		e66=new Employee(6, "varshaa", "varshaa@gmail.com", "9127392681", "address", new Department(1, "IT"), new User(6, "varshaa@gmail.com", "1234", "EMPLOYEE"));
		
	}

	@Test
	public void addEmployee() {

		// case 1: getting the correct output
		// passing e1 and getting the same.
		when(employeeRepository.save(e1)).thenReturn(e1);

		try {
			assertEquals(e1, employeeService.addEmployee(e1,1));
		} catch (InvalidIdException e) {

		} catch (InvalidContactException e) {

		} catch (UsernameInvalidException e) { 
			e.printStackTrace();
		}

		// case 2: intentionally passing invalid contact

		try {
			try {
				assertEquals(e3, employeeService.addEmployee(e3,1));
			} catch (UsernameInvalidException e) {  
			}
		} catch (InvalidIdException e) {

		} catch (InvalidContactException e) {

			assertEquals("Invalid Contact number....", e.getMessage());
		}

		// case 3: passing a object and expecting a different object in return
		// checking for wrong output.

		when(employeeRepository.save(e4)).thenReturn(e4);

		try {
			assertNotEquals(e2, employeeService.addEmployee(e4,1));
		} catch (InvalidContactException e) {
			e.printStackTrace();
		} catch (InvalidIdException e) {
			e.printStackTrace();
		} catch (UsernameInvalidException e) { 
			e.printStackTrace();
		}

		// case 4: checking for getting the username correctly

		when(userRepository.findByUsername("nava")).thenReturn(e2.getUser());
		when(employeeRepository.save(e2)).thenReturn(e2);
		try {
			assertEquals(e2, employeeService.addEmployee(e2,1));
		} catch (InvalidContactException e) {
			e.printStackTrace();
		} catch (InvalidIdException e) {
			e.printStackTrace();
		} catch (UsernameInvalidException e) { 
			
		}
		
		

		verify(employeeRepository, times(1)).save(e1);
		verify(employeeRepository, times(1)).save(e2);

	}

	@Test
	public void getById() {

		// case 1:checking if findById works or not.
		// as my return type of findById is optional here, i'm returning it as
		// "Optional.of(e1)"
		when(employeeRepository.findById(e1.getId())).thenReturn(Optional.of(e1));

		try {
			assertEquals(e1, employeeService.getById(1));
		} catch (InvalidIdException e) {
		}

		// case 2: intentionally passing wrong id.

		try {
			assertNotEquals(e2, employeeService.getById(1));
		} catch (InvalidIdException e) {
			assertEquals("Employee Id is invalid....", e.getMessage());
		}

		verify(employeeRepository, times(2)).findById(1);
	}

	@Test
	public void getAll() {

		// case 1: checking for getting correct output.
		Pageable pageable = PageRequest.of(0, 3);
		List<Employee> list = Arrays.asList(e1, e2, e4);
		Page<Employee> page = new PageImpl<>(list);

		when(employeeRepository.findAll(pageable)).thenReturn(page);

		assertEquals(page, employeeService.getAll(pageable));

		// case 2: checking for incorrect output.
		list = Arrays.asList(e1, e2);
		page = new PageImpl<>(list);
		assertNotEquals(page, employeeService.getAll(pageable));
		verify(employeeRepository, times(2)).findAll(pageable);
	}

	@Test
	public void filterByName() {

		// case 1: getting the correct output.
		List<Employee> list = Arrays.asList(e4, e5);

		when(employeeRepository.findByName("pooja")).thenReturn(list);

		assertEquals(list, employeeService.filterByName("pooja"));
		assertEquals(2, employeeService.filterByName("pooja").size());

		// case 2: checking for incorrect output.

		assertNotEquals(1, employeeService.filterByName("pooja").size());
		assertNotEquals(list, employeeService.filterByName("sheryl"));

		verify(employeeRepository, times(3)).findByName("pooja");
		verify(employeeRepository, times(1)).findByName("sheryl");
	}

//	@Test
//	public void filterByDepartment() {
//
//		// case 1: getting the correct output.
//		List<Employee> list = Arrays.asList(e1, e4);
//
//		when(employeeRepository.findByDepartmentName("IT")).thenReturn(list);
//
//		assertEquals(list, employeeService.filterByDepartment("IT"));
//
//		assertEquals(2, employeeService.filterByDepartment("IT").size());
//
//		// case 2: checking for incorrect output.
//		assertNotEquals(3, employeeService.filterByDepartment("IT").size());
//
//		assertNotEquals(list, employeeService.filterByDepartment("HR"));
//
//		verify(employeeRepository, times(3)).findByDepartmentName("IT");
//	}

	@Test
	public void updateEmployee() {

		oldEmployee = new Employee(6, "charu", "charu@gmail.com", "9128371293", "2, 5th street",
				new Department(1, "IT"), new User(6, "charu", "1234", "EMPLOYEE"));
		newEmployee = new Employee(6, "sri", "sri@gmail.com", "9092407442", "no.154, anna nagar",
				new Department(1, "IT"), new User(6, "charu", "1234", "EMPLOYEE"));

		// case 1: full successfull update.
 		
		//writing this once is enough for passing any method of type employee.
		//no need for writing again for each use cases.
		//telling mockito to allow passing of any methods of employee
		//since .save() is a generic method declaring <Employee> so that mockito would know.
		//if not declaring <Employee> mockito throws an TypeMismatchError. 
		when(employeeRepository.save(Mockito.<Employee>any()))
	    .thenAnswer(invocation -> invocation.getArgument(0));

 
		Employee updated;
		try {
			updated = employeeService.updateEmployee(oldEmployee, newEmployee);
			assertEquals("sri", updated.getName()); // updated
			assertEquals("sri@gmail.com", updated.getEmail()); // updated
			assertEquals("9092407442", updated.getContact()); // updated
			assertEquals("no.154, anna nagar", updated.getAddress()); // updated
		} catch (InvalidContactException | InvalidIdException e) {
		}

		// case 2: partial update
		oldEmployee = new Employee(6, "charu", "charu@gmail.com", "9128371293", "2, 5th street",
				new Department(1, "IT"), new User(6, "charu", "1234", "EMPLOYEE"));
		newEmployee = new Employee(6, null, null, "9092407442", "no.154, anna nagar", new Department(1, "IT"),
				new User(6, "charu", "1234", "EMPLOYEE"));

		try {
			updated = employeeService.updateEmployee(oldEmployee, newEmployee);

			assertEquals("charu", updated.getName()); // not - updated
			assertEquals("9092407442", updated.getContact()); // updated
			assertEquals("no.154, anna nagar", updated.getAddress()); // updated
		} catch (InvalidContactException | InvalidIdException e) {
		}
		
		//case 3: intentionally passing invalid contact
		oldEmployee = new Employee(6, "charu", "charu@gmail.com", "9128371293", "2, 5th street",
				new Department(1, "IT"), new User(6, "charu", "1234", "EMPLOYEE"));
		newEmployee = new Employee(6, null, null, "90442", "no.154, anna nagar", new Department(1, "IT"),
				new User(6, "charu", "1234", "EMPLOYEE"));

		try {
			updated = employeeService.updateEmployee(oldEmployee, newEmployee);
		} catch (InvalidContactException | InvalidIdException e) {
			 assertEquals("Invalid Contact number....", e.getMessage());
		}
		
		//case 4: null contact , updating name
		oldEmployee = new Employee(6, "charu", "charu@gmail.com", "9128371293", "2, 5th street",
				new Department(1, "IT"), new User(6, "charu", "1234", "EMPLOYEE"));
		newEmployee = new Employee(6, "sri", null, null, "no.154, anna nagar", new Department(1, "IT"),
				new User(6, "charu", "1234", "EMPLOYEE"));

		try {
			updated = employeeService.updateEmployee(oldEmployee, newEmployee);
			assertEquals("sri", updated.getName());
		} catch (InvalidContactException | InvalidIdException e) { 
		}
		
		//case 5: no updates, passing only null.

		oldEmployee = new Employee(6, "charu", "charu@gmail.com", "9128371293", "2, 5th street",
				new Department(1, "IT"), new User(6, "charu", "1234", "EMPLOYEE"));
		newEmployee = new Employee();
		
		try {
			updated = employeeService.updateEmployee(oldEmployee, newEmployee);
			assertEquals("charu", updated.getName());  
			assertEquals("charu@gmail.com", updated.getEmail());  
			assertEquals("9128371293", updated.getContact());  
			assertEquals("2, 5th street", updated.getAddress());  
		} catch (InvalidContactException | InvalidIdException e) { 
		}
	 	
	}
	
	@Test
	public void deleteByEmployee() {
		
		//case 1: performing deletion correctly. 
		
		try {
			assertEquals("Employee deleted successfully", employeeService.deleteByEmployee(e1));
		} catch (InvalidIdException e) {
		}
		
		verify(employeeRepository, times(1)).delete(e1);
	}
	
	@Test
	public void findByUsername() {
		
		//case 1: getting the correct output
		when(employeeRepository.findByUserUsername("sheryl")).thenReturn(e1);
		
		assertEquals(e1, employeeService.findByUsername("sheryl")); 
		
		//case 2: checking for incorrect output
		
		assertNotEquals(e2, employeeService.findByUsername("sheryl"));
		
		verify(employeeRepository, times(2)).findByUserUsername("sheryl");
	}
}
