package com.hexa.assetmanagement.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexa.assetmanagement.model.Asset;
import com.hexa.assetmanagement.model.AssetRequest;
import com.hexa.assetmanagement.model.Employee;

public interface AssetRequestRepository extends JpaRepository<AssetRequest, Integer> {

	//describing for getting the list of asset request through status.
	List<AssetRequest> findByStatus(String status);

	//describing for getting the list of asset request through employee.
	List<AssetRequest> findByEmployee(Employee employee);

	//describing for getting the list of asset request through asset.
	List<AssetRequest> findByAsset(Asset asset);

	//describing for getting the list of asset request through request date.
	List<AssetRequest> findByRequestDate(LocalDate requestDate);

}
