package com.hexa.assetmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexa.assetmanagement.model.Asset;
import com.hexa.assetmanagement.model.AssetAllocation;
import com.hexa.assetmanagement.model.Employee;

public interface AssetAllocationRepository extends JpaRepository<AssetAllocation, Integer> {

	List<AssetAllocation> findByAsset(Asset asset);

	List<AssetAllocation> findByEmployee(Employee employee);


}
