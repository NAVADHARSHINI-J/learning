package com.springboot.rest_api.service;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.rest_api.exception.InvalidIdException;
import com.springboot.rest_api.model.Customer;
import com.springboot.rest_api.model.CustomerProduct;
import com.springboot.rest_api.model.Product;
import com.springboot.rest_api.model.Review;
import com.springboot.rest_api.repository.CustomerProductRepository;
import com.springboot.rest_api.repository.ReviewRepository;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;
	@Autowired
	private ProductService productService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private CustomerProductRepository customerProductRepository;

	
	public List<Review> getByProductId(int pId) throws InvalidIdException {
		//check the pId is valid
		productService.getById(pId);
		return reviewRepository.findByProductId(pId);
	}
	public List<Review> getByCustomerId(int cusId) throws InvalidIdException {
		//get the customer from id to validate
		customerService.getSingleCustomer(cusId);
		return reviewRepository.findByCustomerId(cusId);
	}
	
	public void add(Review review, int pId, String username) throws InvalidIdException {
		//check the product id is correct
		Product product=productService.getById(pId);
		//get the customer by using the username
		Customer customer=customerService.getByUsername(username);
		//set the customer id and the product Id in the Review
		review.setCustomer(customer);
		review.setProduct(product);
		//check the customerproduct by the productId and employeeid if there any record 
		//present then the customer purchased the product
	   List<CustomerProduct> customerPro = customerProductRepository.findAll().stream()
			   	.filter(cp->cp.getProduct().getId()==pId)
			   		.filter(c->c.getCustomer().getId()==customer.getId())
			   		.toList();
	   if(customerPro.isEmpty())
		   throw new RuntimeException("The Product is not purchased by you....");
	   //If the employee purchased the product then review can be added
	    reviewRepository.save(review);
	   
	}
	
}
