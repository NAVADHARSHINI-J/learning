package com.hexa.assetmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hexa.assetmanagement.model.LiquidAsset;

public interface LiquidAssetRepository extends JpaRepository<LiquidAsset, Integer>{
   
	List<LiquidAsset> findByStatus(String status);
	List<LiquidAsset> findByName(String name);
}
