package com.casestudy.main.controller;

import java.util.List;

import org.springframework.stereotype.Component;

import com.casestudy.main.model.Book;

import com.casestudy.main.service.BookService;
@Component
public class BookController {

	private BookService bookService; 
	
	
	public BookController(BookService bookService) {
		super();
		this.bookService = bookService;
	}


	public List<Book> getBooks() {
		
		return bookService.getBooks();
	}

}