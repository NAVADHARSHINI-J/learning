package com.hexa.assetmanagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.hexa.assetmanagement.exception.InvalidContactException;
import com.hexa.assetmanagement.exception.InvalidIdException;
import com.hexa.assetmanagement.exception.UsernameInvalidException;
import com.hexa.assetmanagement.model.Admin;
import com.hexa.assetmanagement.model.User;
import com.hexa.assetmanagement.repository.AdminRepository;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

	@Mock
	private AdminRepository adminRepository;
	@InjectMocks
	private AdminService adminService;
	@Mock
	private UserService userService;
	
	Admin a1,a2,a3,a4;
	User u1,u2;
	
	@BeforeEach
	public void init() {
		a1=new Admin(1,"Julie","julie@gmail.com", "9344650345","Chennai",new User(1,"julie","1234","ADMIN"));
		a2=new Admin(2,"Robert","robert@gmail.com", "9336985245","Bangalore",new User(2,"robert","1234","ADMIN"));
		a3=new Admin(3,"Harry","harry@gmail.com", "9850345","Mumbai",new User(3,"harry","1234","ADMIN"));
		a4=new Admin(4,"admin4","admin4@gmail.com","987563698","Chennai");
		u1=new User(4,"admin1", "1234","ADMIN");
		u2=new User(1,"julie","1234","ADMIN");
	}
	
	@Test
	public void addTest() {
		//expected:a1
		//actual:adminService.add(a1,"julie");
		//usecase 1: correct output
		when(adminRepository.save(a1)).thenReturn(a1);
		try {
			when(userService.signup(u2)).thenReturn(u2);
		} catch (UsernameInvalidException e) { }
		try {
			assertEquals(a1,adminService.add(a1));
		} catch (InvalidContactException | UsernameInvalidException e) {		}
		
		//usecase 2: throw exception
		//I expect to throw exception because the contect number of a3 is invalid
		try {
			assertEquals(a3,adminService.add(a3));
		} catch (InvalidContactException | UsernameInvalidException e) {	
			assertEquals("Invalid contact number...", e.getMessage());
		}
		
		//usecase 3: wrong output
		when(adminRepository.save(a2)).thenReturn(a2);
		//it give the wrong output because when save the a2 will give a2
		//but here we gave expected as a1
		try {
			assertNotEquals(a1,adminService.add(a2));
		} catch (InvalidContactException | UsernameInvalidException e) {	}
		
		verify(adminRepository,times(1)).save(a1);
		verify(adminRepository,never()).save(a3);
		
	}
	
	@Test
	public void getByIdTest() {
		//expected: a2
		//actual:adminService.getById(2);
		//usecase : 1 (correct output)
		when(adminRepository.findById(2)).thenReturn(Optional.of(a2));
		try {
			assertEquals(a2, adminService.getById(2));
		} catch (InvalidIdException e) {	}
		
		//usecase : 2 (Exception throw)
		//here we give the wrong id so the exception is thrown
		try {
			assertEquals(a2, adminService.getById(10));
		} catch (InvalidIdException e) {
			assertEquals("Admin id is invalid.....", e.getMessage());
		}
		
		//usecase : 3 (wrong output)
		//here we give the id as 2 for id 2 the correct  expected value is a2
		//here we give it as a3 
		try {
			assertNotEquals(a3, adminService.getById(2));
		} catch (InvalidIdException e) {	}
		
		verify(adminRepository,times(2)).findById(2);
		verify(adminRepository,times(1)).findById(10);
	}
	
	@Test
	public void updateTest() {
		//expected : au3
		//actual : adminService.update(a33, 3);
		//usecase 1 (correct output check)
		//a33 contains the values to be updated and au3 is the
		//complete updated record
		//a33 contains the updated element
		Admin a33 = new Admin();
		a33.setContact("6985741256");
		a33.setAddress("Bangalore");
		when(adminRepository.findById(3)).thenReturn(Optional.of(a3));
		when(adminRepository.save(a3)).thenReturn(a3);
		try {
			assertEquals(a3,adminService.update(a33, 3));
		} catch (InvalidIdException | InvalidContactException e) {		}
		
		//use case 2: throw Invalid ID exception
		//given admin id is wrong so it thrown the exception
		try {
			assertEquals(a3,adminService.update(a33, 10));
		} catch (InvalidIdException | InvalidContactException e) {	
			assertEquals("Admin id is invalid.....", e.getMessage());
		}
		
		//use case 3: throw InvalidContactException
		//given contact is invalid so it thrown the exception
		a33.setContact("45698");
		try {
			assertEquals(a3,adminService.update(a33, 3));
		} catch (InvalidIdException | InvalidContactException e) {	
			assertEquals("Invalid contact number...", e.getMessage());
		}
		
		//use case 4: (update email and name)
		a33.setContact(null);
		a33.setAddress(null);
		a33.setEmail("harry123@gmail.com");
		a33.setName("Harry");
		//here we update the email and password
		try {
			assertEquals(a3,adminService.update(a33, 3));
		} catch (InvalidIdException | InvalidContactException e) {		}
		
		verify(adminRepository,times(2)).save(a3);
		
	}
	
}









