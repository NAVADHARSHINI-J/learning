package com.springboot.rest_api.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.rest_api.exception.InvalidContactException;
import com.springboot.rest_api.exception.InvalidIdException;
import com.springboot.rest_api.exception.UsernameInvalidException;
import com.springboot.rest_api.model.Customer;
import com.springboot.rest_api.model.User;
import com.springboot.rest_api.repository.CustomerRepository;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private UserService userService;
	
	public Customer addEmployee(Customer customer) throws UsernameInvalidException {
		User user = customer.getUser();
 		//set the role
 		user.setRole("CUSTOMER");
 		//save user in DB
 		user = userService.signup(user);
 		
 		//attach saved user to customer 
 		customer.setUser(user);
		return customerRepository.save(customer);
	}

	public List<Customer> getAllCustomer(Pageable p) {
		//get by active=true
	     return customerRepository.findAll(p).getContent()
	    		 .stream()
	    		 .filter(c->c.isActive()==true)
	    		 .toList();
	}

	public Customer getSingleCustomer(int id) throws InvalidIdException{
		Optional<Customer> op=customerRepository.findById(id);
		if(op.isEmpty()) {
			throw new InvalidIdException("Invalid Id");
		}
		return op.get();
	}

	public void hardDelete(Customer c) {
		customerRepository.delete(c);
	}

	public void softDelete(Customer c) {
		c.setActive(true);
		customerRepository.save(c);
	}

	public List<Customer> getCustomerByContact(String contact) throws InvalidContactException {
		if(contact.length()!=10)
			throw new InvalidContactException("Invalid contact number must have 10 digits");
		return customerRepository.findByContact(contact);
	}

	public List<Customer> getByIsActive(boolean status) {
		return customerRepository.findByIsActive(status);
	}

	public void deleteInActiveCustomer() {
		//Fetch the customer where status is inactive
		List<Customer> customer=customerRepository.findByIsActive(false);
		//delete all the customer where inactive
		customerRepository.deleteAll(customer);
	}

	public void getCustomerFromExcel(MultipartFile file)
			throws IOException, UsernameInvalidException {
		 InputStream ins = file.getInputStream();
 		 /*Convert input stream of file into reader that u can read */
 		BufferedReader br = new BufferedReader(new InputStreamReader(ins));
 		//System.out.println(br.lines().count());
 		String line; 
 		int linesToDelete = 1; 
 		//ignore the first line which has headers 
 		for(int i=0;i<linesToDelete;i++) {
 			br.readLine();
 		}
 		List<Customer> list = new ArrayList<>();
 		/*When there is no line to read, line becomes null and exits the loop */
 		while((line = br.readLine()) != null) {
 			//System.out.println(line);
 			String[] fields  = line.split(",");
 			String name = fields[1];
 			String contact = fields[2];
 			String username = fields[3];
 			String password = fields[4];
 			System.out.println(fields[1]);
 			User user = new User(username,password, "CUSTOMER");
 			/* save user in DB , because user needs id to be complete 
 				before attaching to 
 			 */
 			user = userService.signup(user);
 			Customer customer = new Customer (name,contact,user); 
 			list.add(customer);
 		}
 		/*After this loop , we have list of customers 
 		 * having users attached for login*/
 		
 		/*Use saveAll - Batch insert to save them all in DB */
 		customerRepository.saveAll(list);
 	}
	
	public Customer getByUsername(String username) {
		return customerRepository.findByUserUsername(username);
	}

	

}
