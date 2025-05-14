package com.casestudy.mocktest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.casestudy.mocktest.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Integer>{

}
