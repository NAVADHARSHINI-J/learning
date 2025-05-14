package com.casestudy.main.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.casestudy.main.factory.BookFactory;
import com.casestudy.main.model.Book;
import com.casestudy.main.repository.BookRepository;
@Component
public class BookService {

	public List<Book> getBooks() {
		 BookRepository bookRepository = BookFactory.getBookRepository();
		return bookRepository.getBooks();
	}

	
}