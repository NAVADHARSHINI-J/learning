package com.springboot.rest_api.controller;


import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.rest_api.dto.MessageDto;
import com.springboot.rest_api.exception.InvalidContactException;
import com.springboot.rest_api.exception.InvalidIdException;
import com.springboot.rest_api.exception.UsernameInvalidException;
import com.springboot.rest_api.model.Customer;
import com.springboot.rest_api.service.CustomerService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private MessageDto messageDto;

	@GetMapping("/api/customer/public/hello")
	public String getHello() {
		return "Hello from spring boot in public!!";
	}
	
	@GetMapping("/api/customer/private/hello")
	public String getPrivateHello() {
		return "Hello from spring boot in private!!";
	}

	@PostMapping("/api/customer/add")
	public Customer addCustomer(@RequestBody Customer customer) throws UsernameInvalidException {
		return customerService.addEmployee(customer);
	}

	@GetMapping("/api/customer/getall")
	public List<Customer> getAllCustomer(@RequestParam int page,int size) {
	Pageable p= PageRequest.of(page, size);
		return customerService.getAllCustomer(p);
	}

	@GetMapping("/api/customer/one/{id}")
	public ResponseEntity<?> getSingleCustomer(@PathVariable int id) {

		Customer c;
		try {
			c = customerService.getSingleCustomer(id);
			return ResponseEntity.ok(c);
		} catch (InvalidIdException e) {
			// purpose of creating messagedto is to convert the response in postman raw to
			// json
			messageDto.setMessage(e.getMessage());
			messageDto.setStatusCode(400);
			return ResponseEntity.status(400).body(messageDto);
		}

	}

	// hard delete means delete entire row
	@DeleteMapping("/api/customer/hard-delete/{id}")
	public ResponseEntity<?> hardDelete(@PathVariable int id) {
		try {
			Customer c = customerService.getSingleCustomer(id);
			customerService.hardDelete(c);
			messageDto.setMessage("Hard Delete Performed");
			messageDto.setStatusCode(200);
			return ResponseEntity.ok(messageDto);
		} catch (InvalidIdException e) {
			messageDto.setMessage(e.getMessage());
			messageDto.setStatusCode(400);
			return ResponseEntity.status(400).body(messageDto);
		}
	}

	// soft delete is like update a single value in a row
	@DeleteMapping("/api/customer/soft-delete/{id}")
	public ResponseEntity<?> softDelete(@PathVariable int id) {
		try {
			Customer c = customerService.getSingleCustomer(id);
			customerService.softDelete(c);
			messageDto.setMessage("Soft delete is performed");
			messageDto.setStatusCode(200);
			return ResponseEntity.ok(messageDto);
		} catch (InvalidIdException e) {
			messageDto.setMessage(e.getMessage());
			messageDto.setStatusCode(400);
			return ResponseEntity.status(400).body(messageDto);
		}
	}

	// Update a row by the id
	@PutMapping("/api/customer/update/{id}")
	public ResponseEntity<?> updateCustomer(@PathVariable int id,@RequestBody Customer cus) 
			throws UsernameInvalidException {
		try {
			Customer c=customerService.getSingleCustomer(id);
			if(cus.getName()!=null)
				c.setName(cus.getName());
			if(cus.getContact()!=null)
				c.setContact(cus.getContact());
			return ResponseEntity.ok(customerService.addEmployee(c));
		} catch (InvalidIdException e) {
			messageDto.setMessage(e.getMessage());
			messageDto.setStatusCode(400);
			return ResponseEntity.status(400).body(messageDto);
		}
	}
	
	//Level 2
	// find the customer by contact
	@GetMapping("/api/customer/contact")
	public ResponseEntity<?> getCustomerByContact(@RequestParam String contact) {
		List<Customer> customer;
		try {
			customer = customerService.getCustomerByContact(contact);
			return ResponseEntity.ok(customer);
		} catch (InvalidContactException e) {
			messageDto.setMessage(e.getMessage());
			messageDto.setStatusCode(400);
			return ResponseEntity.status(400).body(messageDto);
		}
	}
	
	// find the customer by is active
	@GetMapping("/api/customer/isactive")
	public List<Customer> findByIsActive(@RequestParam boolean status) {
		return customerService.getByIsActive(status);
	}
	
	
	//delete a record that are inactive
	@DeleteMapping("/api/customer/delete-inactive")
	public ResponseEntity<?> deleteInActiveCustomer() {
		customerService.deleteInActiveCustomer();
		return ResponseEntity.ok("In Active Customers deleted...");
	}
	
	//get a input for the table in the excel file
	@PostMapping("/api/customer/get-excel")
	public ResponseEntity<?> getCustomerFromExcel(@RequestParam MultipartFile file) 
			throws IOException, UsernameInvalidException {
		customerService.getCustomerFromExcel(file);
		return ResponseEntity.ok("customer added.....");
	}
		
}
