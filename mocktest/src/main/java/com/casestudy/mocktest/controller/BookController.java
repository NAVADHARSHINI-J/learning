package com.casestudy.mocktest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.casestudy.mocktest.dto.BookDto;
import com.casestudy.mocktest.exception.InvalidIdException;
import com.casestudy.mocktest.model.Book;
import com.casestudy.mocktest.service.BookService;

@RestController
@RequestMapping("/api/book")
public class BookController {

	@Autowired
	private BookService bookService;
	@PostMapping("/add")
	public ResponseEntity<?> add(@RequestBody BookDto bookDto) throws InvalidIdException {
		bookService.add(bookDto);
		return ResponseEntity.ok("Book is added....");
	}
	
	@GetMapping("/getbyid/{bId}")
	public Book getById(@PathVariable int bId) throws InvalidIdException {
		return bookService.getById(bId);
	}
}
