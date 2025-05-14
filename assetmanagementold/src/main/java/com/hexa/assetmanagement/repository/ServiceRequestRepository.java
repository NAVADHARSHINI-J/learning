package com.hexa.assetmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexa.assetmanagement.model.ServiceRequest;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequest,Integer>{

	List<ServiceRequest> findByStatus(String status);

	List<ServiceRequest> findByEmployeeId(int empid);

	List<ServiceRequest> findByAssetId(int assetId);

}
