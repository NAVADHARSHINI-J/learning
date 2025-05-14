package com.hexa.assetmanagement.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.hexa.assetmanagement.exception.InvalidIdException;
import com.hexa.assetmanagement.model.Asset;
import com.hexa.assetmanagement.model.AssetRequest;
import com.hexa.assetmanagement.model.Employee;
import com.hexa.assetmanagement.service.AssetRequestService;
import com.hexa.assetmanagement.service.AssetService;
import com.hexa.assetmanagement.service.EmployeeService;

@RestController
@RequestMapping("/api/assetrequest")
@CrossOrigin(origins = "http://localhost:5173/")
public class AssetRequestController {

	@Autowired
	private AssetRequestService assetRequestService;
	
	@Autowired
	private AssetService assetService;
	
	@Autowired
	private EmployeeService employeeService;

	@PostMapping("/add/{assetId}")
	//adding a new request for asset with asset id.
	public AssetRequest addAssetRequest(@PathVariable int assetId, @RequestBody AssetRequest assetRequest,
			Principal principal) throws InvalidIdException {

		//fetching the username with principal.
		String username = principal.getName();
		return assetRequestService.addAssetRequest(assetId, username, assetRequest);
	}


	@GetMapping("/get/{assetRequestId}")
	//getting an asset request through it's id.
	public AssetRequest getById(@PathVariable int assetRequestId) throws InvalidIdException {
		return assetRequestService.getById(assetRequestId);
	}

	@GetMapping("/getall")
	// getting the list of asset requests made.
	public List<AssetRequest> getAllAssetRequest() {
		return assetRequestService.getAllAssetRequest();
	}

	@GetMapping("/getbystatus")
	// filtering the asset request with status.
	public List<AssetRequest> filterByStatus(@RequestParam String status) {
		return assetRequestService.filterByStatus(status);
	}

	@GetMapping("/getbyempid/{empId}")
	// filtering the asset request by employeeId.
	public List<AssetRequest> filterByEmployeeId(@PathVariable int empId) throws InvalidIdException {
		return assetRequestService.filterByEmployeeId(empId);
	}

	@GetMapping("/getbyassetid/{assetId}")
	// filtering the request by asset id.
	public List<AssetRequest> filterByAssetId(@PathVariable int assetId) throws InvalidIdException {
		return assetRequestService.filterByAssetId(assetId);
	}

	@GetMapping("/getbydate")
	// filtering the asset request by request date.
	public List<AssetRequest> filterByRequestDate(@RequestParam LocalDate RequestDate) {
		return assetRequestService.filterByRequestDate(RequestDate);
	}
	
	@PutMapping("/update-status/{assetRequestId}")
	//updating the status of the asset request with it's id.
	public AssetRequest updateStatus(@RequestParam String status, @PathVariable int assetRequestId) throws InvalidIdException {
		return assetRequestService.updateStatus(status, assetRequestId);
	}
	
	@DeleteMapping("/delete-by-asset/{assetId}")
	//delete the asset request by asset id
	public String deleteAssetRequestByAsset(@PathVariable int assetId) throws InvalidIdException {
		Asset asset = assetService.getById(assetId);
		
		return assetRequestService.deleteAssetRequestByAsset(asset);
	}
	
	@DeleteMapping("/delete-by-employee/{empId}")
	//delete the asset request by employee id
	public String deleteAssetRequestByEmployee(@PathVariable int empId) throws InvalidIdException {
		Employee employee = employeeService.getById(empId);
		
		return assetRequestService.deleteAssetRequestByEmployee(employee);
	}

}
