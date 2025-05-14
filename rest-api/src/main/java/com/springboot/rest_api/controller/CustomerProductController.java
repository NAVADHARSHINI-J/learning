package com.springboot.rest_api.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.rest_api.exception.InvalidIdException;
import com.springboot.rest_api.model.Customer;
import com.springboot.rest_api.model.CustomerProduct;
import com.springboot.rest_api.model.Product;
import com.springboot.rest_api.service.CustomerProductService;
import com.springboot.rest_api.service.CustomerService;
import com.springboot.rest_api.service.ProductService;

@RestController
@RequestMapping("/api/customer/product")
public class CustomerProductController {
	
	@Autowired
	private CustomerProductService customerProductService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private ProductService productService;
	
	@PostMapping("/purchase/{cusId}/{pId}")
	public CustomerProduct purchase(@PathVariable int cusId,
					@PathVariable int pId,
					@RequestBody CustomerProduct customerProduct) throws InvalidIdException {
		/*1. get the customer from cusId and validate*/
		Customer c=customerService.getSingleCustomer(cusId);
		/*2. get the product from pId and validate*/
		Product p=productService.getById(pId);
		/*3.add it in CustomerProduct*/
		customerProduct.setCustomer(c);
		customerProduct.setProduct(p);
		/*4.validate date*/
		if(customerProduct.getPurchaseDate()==null)
			customerProduct.setPurchaseDate(LocalDate.now());
		/*5.save it in CustomerProduct*/
		return customerProductService.add(customerProduct);
	}
	
	/*get the customer by the product id purchased ask=id */
	@GetMapping("/byProduct/{pId}")
	public List<Customer> getCustomerByProductId(@PathVariable int pId)
			throws InvalidIdException {
		/*1.get the product by id and validate */
		productService.getById(pId);
		/*2.create a method in service*/
		return customerProductService.getByProductId(pId);
	}
	
	/*get the products purchased by the customer  ask=id */
	@GetMapping("/bycustomer/{cusId}")
	public List<Product> getProductByCustomerId(@PathVariable int cusId)
			throws InvalidIdException {
		/*1.get the customer by id and validate */
		customerService.getSingleCustomer(cusId);
		/*2.create a method in service*/
		return customerProductService.getProductByCustomerId(cusId);
	}
}




