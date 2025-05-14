package com.casestudy.mocktest.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.casestudy.mocktest.exception.InvalidIdException;
import com.casestudy.mocktest.model.Author;
import com.casestudy.mocktest.model.Book;
import com.casestudy.mocktest.model.BookAuthor;
import com.casestudy.mocktest.repository.BookAuthorRepository;
import com.casestudy.mocktest.repository.BookRepository;

@Service
public class BookAuthorService {

	@Autowired
	private BookAuthorRepository bookAuthorRepository;
	@Autowired
	private AuthorService authorService;
	@Autowired
	private BookRepository bookRepository;
	
	public BookAuthor add(int bId, int aId) throws InvalidIdException {
		Optional<Book> b = bookRepository.findById(bId);
		if (b.isEmpty())
			throw new InvalidIdException("Book Id is invalid.....");
		Book book= b.get();
		//check aId is present
		Author author =authorService.getById(aId);
		//set the values to the db
		BookAuthor ba=new BookAuthor();
		ba.setAuthor(author);
		ba.setBook(book);
		return bookAuthorRepository.save(ba);
	}

	
}
