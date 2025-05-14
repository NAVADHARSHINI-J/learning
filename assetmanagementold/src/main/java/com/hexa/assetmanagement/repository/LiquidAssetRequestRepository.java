package com.hexa.assetmanagement.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexa.assetmanagement.model.Employee;
import com.hexa.assetmanagement.model.LiquidAssetRequest;

public interface LiquidAssetRequestRepository extends JpaRepository<LiquidAssetRequest,Integer>{

	List<LiquidAssetRequest> findByStatus(String status);
	List<LiquidAssetRequest> findByLiquidAssetId(int liquidAssetId);
	List<LiquidAssetRequest> findByRequestDate(LocalDate date);
	List<LiquidAssetRequest> findByEmployee(Employee employee);

}
