package com.casestudy.mocktest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.casestudy.mocktest.exception.InvalidIdException;
import com.casestudy.mocktest.model.Author;
import com.casestudy.mocktest.service.AuthorService;

@RestController
@RequestMapping("/api/author")
public class AuthorController {

	@Autowired
	private AuthorService authorService;
	
	
	@PostMapping("/add/{uId}")
	public Author add(@RequestBody Author author,@PathVariable int uId) throws InvalidIdException {
		
		return authorService.add(author,uId);
	}
	
	@GetMapping("/getbyid/{aId}")
	public Author getById(@PathVariable int aId) throws InvalidIdException {
		return authorService.getById(aId);
	}
}
