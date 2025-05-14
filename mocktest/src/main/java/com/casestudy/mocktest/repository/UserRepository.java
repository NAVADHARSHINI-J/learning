package com.casestudy.mocktest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.casestudy.mocktest.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	User findByUsername(String username);

}
