package com.springboot.rest_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.rest_api.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer,Integer>{
		List<Customer> findByContact(String contact);
		List<Customer> findByName(String name);
		List<Customer> findByIsActive(boolean status);
		Customer findByUserUsername(String name);
		
}
