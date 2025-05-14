package com.hexa.assetmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexa.assetmanagement.model.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

}
