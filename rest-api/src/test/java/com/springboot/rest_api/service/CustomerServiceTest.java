package com.springboot.rest_api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.springboot.rest_api.exception.InvalidContactException;
import com.springboot.rest_api.model.Customer;
import com.springboot.rest_api.model.User;
import com.springboot.rest_api.repository.CustomerRepository;

@SpringBootTest
/*This tells Spring that we are going to use Mocking */
@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
	/* This is the service class where I want to mack my repository */
	@InjectMocks
	private CustomerService customerService;
	/*This is my actual repo which i am mocking*/
	@Mock
	private CustomerRepository customerRepository;
	
	Customer c1,c2,c3,c4,c11;
	
	
	@BeforeEach
	public void init() {
		
		c1=new Customer(1,"customer1","9344650305",true,new User(1,"cust1@gmail.com","1234","CUSTOMER"));
		c2=new Customer(2,"customer2","9344650305",true,new User(2,"cust2@gmail.com","1234","CUSTOMER"));
		c3=new Customer(3,"customer3","9345634509",true,new User(3,"cust3@gmail.com","1234","CUSTOMER"));
		c4=new Customer(4,"customer4","5677634509",false,new User(4,"cust4@gmail.com","1234","CUSTOMER"));
		c11=new Customer(1,"customer1","9344650305",true,new User(1,"cust1@gmail.com","1234","CUSTOMER"));

	}
	@Test
	public void getAllCustomerTest() {
		Pageable pageable=PageRequest.of(0, 5);
		List<Customer> list=Arrays.asList(c1,c2,c3);
		Page<Customer> page=new PageImpl<>(list);
		/* I am telling Spring to return this page having list of 
 		 * 3 objects c1,c2,c3 whenever it encounters 
 		 * customerRepository.findAll(pageable) */
		when(customerRepository.findAll(pageable)).thenReturn(page);
		/* I do mocking because I do not want to rely on DB records 
 		 * as my test case will fail if DB records were to be deleted */
		assertEquals(3,customerService.getAllCustomer(pageable).size());
		
		list=Arrays.asList(c1,c2,c4);
		page=new PageImpl<>(list);
		when(customerRepository.findAll(pageable)).thenReturn(page);
		/*This 2 as expected is because I am filtering out 'inactive' customers 
 		 * and c4 customer in the list is in-active. hence 2 is correct expectation */
		assertEquals(2,customerService.getAllCustomer(pageable).size());
		verify(customerRepository, times(2)).findAll(pageable);
		
	}
	
	@Test
	public void addEmployeeTest() {
		//expected : c11
		//actula : customerService.addEmployee(c1)
////		usecase: 1
//		when(customerRepository.save(c1)).thenReturn(c11);
//		assertEquals(c11, customerService.addEmployee(c1));
////		usecase: 2
//		when(customerRepository.save(c1)).thenReturn(c11);
//		assertNotEquals(c2, customerService.addEmployee(c1));
//		verify(customerRepository,times(2)).save(c1);
	}
	
	@Test
	public void getCustomerByContactTest() {
		//expected : List<Customers> list=Arrays.asList(c1,c2,c11);
		//actual : customerService.getCustomerByContact("9344650305")
		//usecase:1
		String contact="9344650305";
		List<Customer> list=Arrays.asList(c1,c2,c11);
		when(customerRepository.findByContact(contact)).thenReturn(list);
		try {
			assertEquals(list, customerService.getCustomerByContact(contact));
		} catch (InvalidContactException e) { }
		
		//usecase:2
		contact="122123";
		try {
			assertEquals(list, customerService.getCustomerByContact(contact));
		} catch (InvalidContactException e) { 
			assertEquals("Invalid contact number must have 10 digits", e.getMessage());
		}
		verify(customerRepository,times(1)).findByContact("9344650305");
	}
	
	@Test
	public void getByIsActiveTest() {
		//expected : List<Customer> list=Arrays.asList(c1,c2,c3,c11);
		//actual : customerService.getByIsActive()
		//usecase : 1
		boolean status=true;
		List<Customer> list=Arrays.asList(c1,c2,c3,c11);
		when(customerRepository.findByIsActive(status)).thenReturn(list);
		assertEquals(list,customerService.getByIsActive(status));
		verify(customerRepository,times(1)).findByIsActive(status);
		//usecase: 2
		status=false;
		list=Arrays.asList(c4);
		when(customerRepository.findByIsActive(status)).thenReturn(list);
		assertEquals(list,customerService.getByIsActive(status));
		
		verify(customerRepository,times(1)).findByIsActive(status);
	}
}
