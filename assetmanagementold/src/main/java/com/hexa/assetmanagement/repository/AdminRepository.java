package com.hexa.assetmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexa.assetmanagement.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer>{

	Admin findByUserUsername(String user);

}
