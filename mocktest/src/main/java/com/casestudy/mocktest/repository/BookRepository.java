package com.casestudy.mocktest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.casestudy.mocktest.model.Book;

public interface BookRepository extends JpaRepository<Book, Integer>{

}
