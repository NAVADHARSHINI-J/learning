package com.casestudy.mocktest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.casestudy.mocktest.model.Student;

public interface StudentRepository extends JpaRepository<Student, Integer>{

}
