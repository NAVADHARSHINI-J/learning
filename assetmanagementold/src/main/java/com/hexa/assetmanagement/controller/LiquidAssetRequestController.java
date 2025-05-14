package com.hexa.assetmanagement.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.hexa.assetmanagement.exception.InvalidIdException;
import com.hexa.assetmanagement.model.Employee;
import com.hexa.assetmanagement.model.LiquidAsset;
import com.hexa.assetmanagement.model.LiquidAssetRequest;
import com.hexa.assetmanagement.service.EmployeeService;
import com.hexa.assetmanagement.service.LiquidAssetRequestService;
import com.hexa.assetmanagement.service.LiquidAssetService;

@RestController
@RequestMapping("/api/liquidassetreq")
@CrossOrigin(origins = {"http://localhost:5173"})
public class LiquidAssetRequestController {

	@Autowired
    private LiquidAssetRequestService liquidAssetRequestService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private LiquidAssetService liquidAssetService;
    

    @PostMapping("/add/{employeeId}/{liquidAssetId}")
    public LiquidAssetRequest addLiquidAssetRequest(
            @RequestBody LiquidAssetRequest request,
            @PathVariable int employeeId,
            @PathVariable int liquidAssetId) throws InvalidIdException {
        //get the employee with id 
        Employee employee = employeeService.getById(employeeId);
        //get the liquid asset id
        LiquidAsset liquidAsset = liquidAssetService.getById(liquidAssetId);
        //set the fetched details to  liquid asset request
        request.setEmployee(employee);
        request.setLiquidAsset(liquidAsset);
        return liquidAssetRequestService.addLiquidAssetRequest(request);
    }

    @GetMapping("/getbyid/{id}")
    public LiquidAssetRequest getById(@PathVariable int id) throws InvalidIdException {
    	//get liquid asset request using id
        return liquidAssetRequestService.getById(id);
    }

    @GetMapping("/getall")
    public List<LiquidAssetRequest> getAll(@RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        //get all liquid asset request in page format
        return liquidAssetRequestService.getAll(pageable);
    }
    
    @GetMapping("/bystatus/{status}")
    public List<LiquidAssetRequest> filterByStatus(@PathVariable String status) {
    	// get liquid asset request by status
    	return liquidAssetRequestService.filterByStatus(status);
    }
    
    @GetMapping("/byliquidAssetId/{liquidAssetId}")
    public List<LiquidAssetRequest> filterByLiquidAssetId(@PathVariable int liquidAssetId) 
    		throws InvalidIdException {
    	//check asset with id is present in database or not
    	liquidAssetRequestService.getById(liquidAssetId);
    	//get the liquid assets using the request id
    	return liquidAssetRequestService.filterByLiquidAssetId(liquidAssetId);
    }
    
    @GetMapping("/bydate/{date}")
    public ResponseEntity<List<LiquidAssetRequest>> filterByRequestDate(@PathVariable LocalDate date) {
        List<LiquidAssetRequest> requests = liquidAssetRequestService.filterByRequestDate(date);
        //get the liquid asset request using date
        return ResponseEntity.ok(requests);
    }
    
    @GetMapping("/byemployeeId/{id}")
    public ResponseEntity<List<LiquidAssetRequest>> filterByEmployeeId(@PathVariable int id) throws InvalidIdException {
        List<LiquidAssetRequest> requests = liquidAssetRequestService.filterByEmployeeId(id);
        //get the liquid asset request using employee id
        return ResponseEntity.ok(requests);
    }
    
    @DeleteMapping("/delete/byliquidasset/{id}")
    public ResponseEntity<String> deleteRequestsByLiquidAssetId(@PathVariable int id) throws InvalidIdException {
        String message = liquidAssetRequestService.deleteByLiquidAssetId(id);
        //delete the request for liquid asset using the liquid asset id
        return ResponseEntity.ok(message);
    }
    
    @DeleteMapping("/delete/byemployee/{id}")
    public ResponseEntity<String> deleteRequestsByEmployeeId(@PathVariable int id) throws InvalidIdException {
        String message = liquidAssetRequestService.deleteByEmployeeId(id);
        //delete the request using the employee id
        return ResponseEntity.ok(message);
    }
    
    
    
}
