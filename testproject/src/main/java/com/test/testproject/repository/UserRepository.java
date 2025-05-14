package com.test.testproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.testproject.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	User findByUsername(String username);

}
