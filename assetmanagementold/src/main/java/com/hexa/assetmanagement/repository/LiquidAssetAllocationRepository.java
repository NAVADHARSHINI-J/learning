package com.hexa.assetmanagement.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.hexa.assetmanagement.model.Employee;
import com.hexa.assetmanagement.model.LiquidAsset;
import com.hexa.assetmanagement.model.LiquidAssetAllocation;

public interface LiquidAssetAllocationRepository extends JpaRepository<LiquidAssetAllocation,Integer>{
	
	List<LiquidAssetAllocation> findByEmployee(Employee employee);
	List<LiquidAssetAllocation> findByLiquidAsset(LiquidAsset asset);
}
