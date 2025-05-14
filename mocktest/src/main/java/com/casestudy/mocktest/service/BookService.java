package com.casestudy.mocktest.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.casestudy.mocktest.dto.BookDto;
import com.casestudy.mocktest.exception.InvalidIdException;
import com.casestudy.mocktest.model.Author;
import com.casestudy.mocktest.model.Book;
import com.casestudy.mocktest.repository.AuthorRepository;
import com.casestudy.mocktest.repository.BookRepository;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private AuthorRepository authorRepository;
	@Autowired
	private BookAuthorService bookAuthorService;

	public Book getById(int bId) throws InvalidIdException {
		// check the Id is present
		Optional<Book> b = bookRepository.findById(bId);
		if (b.isEmpty())
			throw new InvalidIdException("Book Id is invalid.....");
		return b.get();
	}

	public void add(BookDto bookDto) throws InvalidIdException {
		// create a book and save the elemnets in the book
		Book book = new Book();
		book.setTitle(bookDto.getTitle());
		book.setPrice(bookDto.getPrice());
		book.setIsbn(bookDto.getIsbn());
		book=bookRepository.save(book);
		// find all the authors by the ID
		List<Author> author = authorRepository.findAllById(bookDto.getAuthorId());
		for(Author au:author) {
			bookAuthorService.add(book.getId(),au.getId());
		}
	}

}




