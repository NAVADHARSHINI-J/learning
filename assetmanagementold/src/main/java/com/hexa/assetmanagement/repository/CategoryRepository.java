package com.hexa.assetmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexa.assetmanagement.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{

}
