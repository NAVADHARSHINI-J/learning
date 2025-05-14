package com.hexa.assetmanagement.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hexa.assetmanagement.exception.InvalidIdException;
import com.hexa.assetmanagement.model.LiquidAsset;
import com.hexa.assetmanagement.model.LiquidAssetAllocation;
import com.hexa.assetmanagement.repository.LiquidAssetAllocationRepository;
import com.hexa.assetmanagement.repository.LiquidAssetRepository;

@Service
public class LiquidAssetService {
	@Autowired
	private LiquidAssetRepository liquidAssetRepository;
	
	@Autowired
	private LiquidAssetAllocationRepository liquidAssetAllocationRepository;
	
	Logger logger = LoggerFactory.getLogger("LiquidAssetService");
	
	public LiquidAsset addliquidAsset(LiquidAsset liquidAsset) {
		// adding of a new liquid asset to the list
		logger.info("A new liquid asset is added");
		return liquidAssetRepository.save(liquidAsset);
	}

	public LiquidAsset getById(int id) throws InvalidIdException {
		//check whether the id is present in the database or not 
		Optional<LiquidAsset> optional = liquidAssetRepository.findById(id);
		if (optional.isEmpty())
			throw new InvalidIdException("Liquid asset Id is invalid!");
		logger.info("A Liquid Asset with this id is found "+optional.get().getId());
		//if optional is not empty get the request from the optional
		return optional.get();
	}

	public List<LiquidAsset> getAll(Pageable pageable) {
		//get all the liquid asset request in page format 
		logger.info("All Liquid Asset Service request are fetched");
		return liquidAssetRepository.findAll(pageable).getContent();
	}

	public List<LiquidAsset> filterByStatus(String status) {
		//get all the liquid asset by status
		logger.info("Liquid Asset is filtered by status");
		return liquidAssetRepository.findByStatus(status);
	}

	public List<LiquidAsset> filterByName(String name) {
		//get all liquid asset by name
		logger.info("Liquid Asset is filtered by Name");
		return liquidAssetRepository.findByName(name);
	}

	public LiquidAsset update(LiquidAsset liquidAsset, int liquidAssetId) throws InvalidIdException {
	//checks whether id is present or not
    //if not null add that in the existing liquid asset
    LiquidAsset liquidAsset1 = getById(liquidAssetId); 
    // Update non-null fields
    if (liquidAsset.getName() != null)
    	liquidAsset1.setName(liquidAsset.getName());
    if (liquidAsset.getTotalAmount() != 0)
    	liquidAsset1.setTotalAmount(liquidAsset.getTotalAmount());
    if (liquidAsset.getRemainingAmount() !=0)
    	liquidAsset1.setRemainingAmount(liquidAsset.getRemainingAmount());
    if (liquidAsset.getDescription() != null)
    	liquidAsset1.setDescription(liquidAsset.getDescription());
    if (liquidAsset.getStatus() != null)
    	liquidAsset1.setStatus(liquidAsset.getStatus());
    logger.info("LiquidAsset " + liquidAsset1.getName() + " updated successfully");
    return liquidAssetRepository.save(liquidAsset1);
}

	public String deleteById(int liquidAssetId) throws InvalidIdException {
	   // Check if the liquid asset exists
	   LiquidAsset liquidAsset = getById(liquidAssetId); // throws InvalidIdException if not found
	   // Find all LiquidAssetAllocations linked to this id
	   List<LiquidAssetAllocation> allocationsToDelete = liquidAssetAllocationRepository.findAll()
	            .stream()
	            .filter(a -> a.getLiquidAsset().getId() == liquidAsset.getId())
	            .toList();
	    // Delete the allocation done to this id
	    liquidAssetAllocationRepository.deleteAll(allocationsToDelete);
	    logger.info("Liquid asset allocations with asset ID " + liquidAsset.getId() + " deleted successfully.");
	    return "Liquid asset allocations deleted successfully.";
	}
}

