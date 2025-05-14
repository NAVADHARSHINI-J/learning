package com.springboot.casestudy1.service;

import org.springframework.data.jpa.repository.JpaRepository;


import com.springboot.casestudy1.model.Course;


public interface CourseRepository extends JpaRepository<Course,Integer>{
  
}
