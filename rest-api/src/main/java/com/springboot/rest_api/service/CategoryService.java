package com.springboot.rest_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.rest_api.exception.InvalidIdException;
import com.springboot.rest_api.model.Category;
import com.springboot.rest_api.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
 	private CategoryRepository categoryRepository;
 	
 	public Category add(Category category) {
 		return categoryRepository.save(category);
 	}
 
 	public List<Category> getAll() {
 		return categoryRepository.findAll();
 	}

	public Category getById(int catId) throws InvalidIdException {
		Optional<Category> optional = categoryRepository.findById(catId);
		if(optional.isEmpty())
			throw new InvalidIdException("Category Id is invalid...");
		return optional.get();
	}
}
