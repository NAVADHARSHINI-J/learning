package com.casestudy.mocktest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.casestudy.mocktest.exception.InvalidIdException;
import com.casestudy.mocktest.model.BookAuthor;
import com.casestudy.mocktest.service.BookAuthorService;



@RestController
@RequestMapping("/api/book/author")
public class BookAuthorController {
	@Autowired
	private BookAuthorService bookAuthorService;
	
	@PostMapping("/add/{bId}/{aId}")
	public BookAuthor add(@PathVariable int bId,@PathVariable int aId)
			throws InvalidIdException {
		return bookAuthorService.add(bId,aId);
	}
}







