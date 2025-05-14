package com.casestudy.mocktest.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.casestudy.mocktest.exception.InvalidIdException;
import com.casestudy.mocktest.model.Author;
import com.casestudy.mocktest.model.User;
import com.casestudy.mocktest.repository.AuthorRepository;
import com.casestudy.mocktest.repository.UserRepository;

@Service
public class AuthorService {

	@Autowired
	private AuthorRepository authorRepository;
	@Autowired
	private UserRepository userRepository;

	public Author getById(int aId) throws InvalidIdException {
		// check the Id is present
		Optional<Author> b = authorRepository.findById(aId);
		if (b.isEmpty())
			throw new InvalidIdException("Author Id is invalid.....");
		return b.get();
	}

	public Author add(Author author,int uId) throws InvalidIdException {
		Optional<User> user=userRepository.findById(uId);
		if(user.isEmpty())
			throw new InvalidIdException("User Id is wrong....");
		author.setUser(user.get());
		return authorRepository.save(author);
	}

}




