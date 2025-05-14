package com.casestudy.mocktest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.casestudy.mocktest.model.BookAuthor;

public interface BookAuthorRepository extends JpaRepository<BookAuthor, Integer>{

}
