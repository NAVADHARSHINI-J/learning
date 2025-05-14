package com.hexa.assetmanagement.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.multipart.MultipartFile;

import com.hexa.assetmanagement.exception.InvalidIdException;
import com.hexa.assetmanagement.model.ServiceRequest;
import com.hexa.assetmanagement.service.ServiceRequestService;


@RestController
@RequestMapping("/api/servicerequest")
@CrossOrigin(origins = "http://localhost:5173")
public class ServiceRequestController {
	@Autowired
    private ServiceRequestService serviceRequestService;
	
	/* add the service request get the employee as principal from that 
	 * get the username of the employee */
    @PostMapping("/add/{assetId}")
    public ServiceRequest addServiceRequest(
            @RequestBody ServiceRequest serviceRequest,
            Principal principal,
            @PathVariable int assetId) throws InvalidIdException {
    	//getting the username by principal
       String username=principal.getName();
        return serviceRequestService.addServiceRequest(serviceRequest, username, assetId);
    }

    /* get the service request by using the id*/
    @GetMapping("/getbyid/{requestId}")
    public ServiceRequest getById(@PathVariable int requestId) throws InvalidIdException {
        return serviceRequestService.getById(requestId);
    }

	/* get all the service request */
    @GetMapping("/getall")
    public List<ServiceRequest> getAll() {
        return serviceRequestService.getAll();
    }
    
    /* get the service request by using the status*/
    @GetMapping("/bystatus")
    public List<ServiceRequest> filterByStatus(@RequestParam String status) {
    	return serviceRequestService.filterByStatus(status);
    }
    
    /* get the service request by using the employeeId*/
    @GetMapping("/byEmployeeId")
    public List<ServiceRequest> filterByEmployeeId(@RequestParam int empId) 
    		throws InvalidIdException {
    	return serviceRequestService.filterByEmployeeId(empId);
    }
    
    /* get the service request by using the asset id*/
    @GetMapping("/byAssetId")
    public List<ServiceRequest> filterByAssetId(@RequestParam int assetId) 
    		throws InvalidIdException {
    	return serviceRequestService.filterByAssetId(assetId);
    }
    
    /* delete the service request by using the asset id*/
    @DeleteMapping("/delete-assetid/{assetId}")
	public ResponseEntity<?> deleteByAssetId(@PathVariable int assetId) throws InvalidIdException {
		//delete all the service request by the given asset id
		String message =serviceRequestService.deleteByAssetId(assetId);
		return ResponseEntity.ok(message);
	}
	
    /* delete the service request by using the employee id*/
	@DeleteMapping("/delete-empId/{empId}")
	public ResponseEntity<?> deleteByEmployeeId(@PathVariable int empId) 
			throws InvalidIdException {
		//delete all the service request by the given employee id
		String message =serviceRequestService.deleteByEmployeeId(empId);
		return ResponseEntity.ok(message);
	}
	
	/* update the service request*/
	@PutMapping("update/{requestId}")
	public ServiceRequest update(@RequestBody ServiceRequest request,
			@PathVariable int requestId) throws InvalidIdException {
		//update the service request
		return serviceRequestService.update(request,requestId);
	}
	
	/* method to upload the file*/
	@PostMapping("/image/upload/{requestId}")
 	public ServiceRequest uploadImage(@PathVariable int requestId, 
 				@RequestParam MultipartFile file) throws IOException, InvalidIdException {
 		return serviceRequestService.uploadImage(file,requestId);
 	}
}

