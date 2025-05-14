package com.springboot.rest_api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.rest_api.model.Customer;
import com.springboot.rest_api.model.CustomerProduct;
import com.springboot.rest_api.model.Product;
import com.springboot.rest_api.repository.CustomerProductRepository;

@Service
public class CustomerProductService {
	@Autowired
	private CustomerProductRepository customerProductRepository;
	public CustomerProduct add(CustomerProduct customerProduct) {
		return customerProductRepository.save(customerProduct);
	}
	public List<Customer> getByProductId(int pId) {
		return customerProductRepository.findAll().stream()
				.filter(p->p.getProduct().getId()==pId)
				.map(c->c.getCustomer()).toList();
	}
	public List<Product> getProductByCustomerId(int cusId) {
		return customerProductRepository.findAll().stream()
		.filter(p->p.getCustomer().getId()==cusId)
		.map(c->c.getProduct()).toList();
	}
	

}
