package com.hexa.assetmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hexa.assetmanagement.dto.AssetDto;
import com.hexa.assetmanagement.exception.InvalidIdException;
import com.hexa.assetmanagement.model.Asset;
import com.hexa.assetmanagement.model.Category;
import com.hexa.assetmanagement.service.AssetService;
import com.hexa.assetmanagement.service.CategoryService;

@RestController
@RequestMapping("/api/asset")
@CrossOrigin(origins = "http://localhost:5173/")
public class AssetController {

	@Autowired
	private AssetService assetService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private AssetDto assetDto;
	@Autowired
	private ServiceRequestController serviceRequestController;
	@Autowired
	private AssetRequestController assetRequestController;
	@Autowired
	private AssetAllocationController assetAllocationController;

	@GetMapping("/public/hello")
	public String sayHello() {
		return "Welcome spring in public..";
	}

	@GetMapping("/private/hello")
	public String sayPrivateHello() {
		return "Welcome spring in private..";
	}

	@PostMapping("/add/{categoryId}")
	// Adding assets using category Id.
	public Asset addAsset(@RequestBody Asset asset, @PathVariable int categoryId) throws InvalidIdException {
		Category category = categoryService.getById(categoryId);
		asset.setCategory(category);
		return assetService.addAsset(asset);
	}

	@GetMapping("/getbyid/{assetId}")
	// finding an asset using asset Id.
	public Asset getById(@PathVariable int assetId) throws InvalidIdException {
		return assetService.getById(assetId);
	}

	@GetMapping("/getall")
	// getting the list of assets
	public AssetDto getAll(@RequestParam int page, @RequestParam int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Asset> asset=assetService.getAll(pageable); 
		assetDto.setCurrentPage(page);
		assetDto.setList(asset.getContent());
		assetDto.setSize(size);
		assetDto.setTotalElements((int)asset.getTotalElements());
		assetDto.setTotalPages(asset.getTotalPages());
		return assetDto;
	}

	@GetMapping("/getbyname")
	// filtering assets by asset name.
	public List<Asset> filterByName(@RequestParam String name) {
		return assetService.filterByName(name);
	}

	@GetMapping("/getbycategory")
	// filtering assets by category
	public AssetDto filterByCategory(@RequestParam String category,@RequestParam int page, @RequestParam int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Asset> asset=assetService.filterByCategory(category,pageable); 
		assetDto.setCurrentPage(page);
		assetDto.setList(asset.getContent());
		assetDto.setSize(size);
		assetDto.setTotalElements((int)asset.getTotalElements());
		assetDto.setTotalPages(asset.getTotalPages());
		return assetDto;
	}

	@GetMapping("/getbystatus")
	// filtering assets by status
	public List<Asset> filterByStatus(@RequestParam String status) {
		return assetService.filterByStatus(status);
	}

	@PutMapping("/update-asset/{assetId}")
	// update an existing asset with its id:
	public Asset updateAsset(@RequestBody Asset newAsset, @PathVariable int assetId)
			throws InvalidIdException {

		Asset oldAsset = assetService.getById(assetId);
		return assetService.updateAsset(newAsset, oldAsset);
	}

	@DeleteMapping("/delete/{assetId}")
	// delete an existing asset with its id
	public String deleteAssetById(@PathVariable int assetId) throws InvalidIdException {
		Asset asset = assetService.getById(assetId);

		//deleting in service request
		serviceRequestController.deleteByAssetId(asset.getId());
		//deleting in asset request
		assetRequestController.deleteAssetRequestByAsset(asset.getId());
		//deleting in asset allocation
		assetAllocationController.deleteByAssetId(asset.getId());
		
		return assetService.deleteAssetById(asset);
	}
	@GetMapping("/getAssetSize")
	//get the size of the total assets
	public int getAssetSize() {
		return assetService.getAssetSize();
	}
}
