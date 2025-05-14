package com.hexa.assetmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexa.assetmanagement.model.Manager;

public interface ManagerRepository extends JpaRepository<Manager,Integer>{

}
