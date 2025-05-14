package com.hexa.assetmanagement.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexa.assetmanagement.exception.InvalidIdException;
import com.hexa.assetmanagement.model.Asset;
import com.hexa.assetmanagement.model.AssetRequest;
import com.hexa.assetmanagement.model.Employee;
import com.hexa.assetmanagement.model.User;
import com.hexa.assetmanagement.repository.AssetRequestRepository;
import com.hexa.assetmanagement.repository.EmployeeRepository;
import com.hexa.assetmanagement.repository.UserRepository;

@Service
public class AssetRequestService {

	@Autowired
	private AssetService assetService;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private AssetRequestRepository assetRequestRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EmployeeService employeeService;

	Logger logger = LoggerFactory.getLogger("AssetRequestService");


	public AssetRequest addAssetRequest(int assetId, String username, AssetRequest assetRequest)
			throws InvalidIdException {

		//getting the asset by asset id.
		Asset asset = assetService.getById(assetId);
		// finding the user by username
		User user = userRepository.findByUsername(username);
		
		//setting the asset in asset request.
		assetRequest.setAsset(asset);

		// finding the employee with user, if not found then throw an exception
		Employee employee = employeeRepository.findByUser(user)
				.orElseThrow(() -> new InvalidIdException("Employee not found for user"));

		//setting the employee in asset request.
		assetRequest.setEmployee(employee);

		// if date is not provided then set it with current date
		if (assetRequest.getRequestDate() == null)
			assetRequest.setRequestDate(LocalDate.now());

		logger.info("Asset Request for " + asset.getName() + "is made");
		return assetRequestRepository.save(assetRequest);
	}


	public AssetRequest getById(int assetRequestId) throws InvalidIdException {
		//check if the asset request is found or not.
		Optional<AssetRequest> optional = assetRequestRepository.findById(assetRequestId);
		//if not threw an exception.
		if (optional.isEmpty())
			throw new InvalidIdException("Asset Request Id is invalid");
		return optional.get();
	}

	public List<AssetRequest> getAllAssetRequest() {
		//return the list of requests.
		return assetRequestRepository.findAll();
	}

	public List<AssetRequest> filterByStatus(String status) {
		//return the list of requests based on their status.
		return assetRequestRepository.findByStatus(status);
	}

	public List<AssetRequest> filterByEmployeeId(int empId) throws InvalidIdException {
		Employee employee = employeeService.getById(empId);
		//returning the list requests made by a particular employee with his/her id.
		return assetRequestRepository.findByEmployee(employee);
	}

	public List<AssetRequest> filterByAssetId(int assetId) throws InvalidIdException {
		Asset asset = assetService.getById(assetId);
		//returning the list of asset requests made for a particular asset with it's id.
		return assetRequestRepository.findByAsset(asset);
	}

	public List<AssetRequest> filterByRequestDate(LocalDate RequestDate) {
		//returning the list of asset requests made based on their request date.
		return assetRequestRepository.findByRequestDate(RequestDate);
	}

	public AssetRequest updateStatus(String status, int assetRequestId) throws InvalidIdException { 
		//updating the status of the asset request such as approved or declined through it's id.
		AssetRequest assetRequest = getById(assetRequestId);
		if(status != null) 
			assetRequest.setStatus(status);
		logger.info("Status updated to {} for AssetRequest {}", status, assetRequest.getAsset().getName());
		return assetRequestRepository.save(assetRequest);
	}

	public String deleteAssetRequestByAsset(Asset asset) { 
		//deleting the asset request made, with asset.
		List<AssetRequest> list = assetRequestRepository.findByAsset(asset);
		logger.info("Asset Request for {} deleted successfully!", asset.getName());
		assetRequestRepository.deleteAll(list);
		return "Asset request using asset id deleted successfully";
	}

	public String deleteAssetRequestByEmployee(Employee employee) { 
		//deleting the asset request made, with employee.
		List<AssetRequest> list =assetRequestRepository.findByEmployee(employee);
		logger.info("Asset Request for {} deleted successfully!", employee.getName());
		assetRequestRepository.deleteAll(list);
		return "Asset request using employee id deleted successfully";
	}

}
