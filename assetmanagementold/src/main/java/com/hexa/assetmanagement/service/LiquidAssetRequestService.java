package com.hexa.assetmanagement.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.hexa.assetmanagement.exception.InvalidIdException;
import com.hexa.assetmanagement.model.Employee;
import com.hexa.assetmanagement.model.LiquidAssetRequest;
import com.hexa.assetmanagement.repository.LiquidAssetRequestRepository;

@Service
public class LiquidAssetRequestService {

	    @Autowired
	    private LiquidAssetRequestRepository liquidAssetRequestRepository;
	    
	    @Autowired
	    private EmployeeService employeeService;
	    
	    @Autowired
	    private LiquidAssetService liquidAssetService;
	 
	Logger logger = LoggerFactory.getLogger("LiquidAssetRequestService");


	    public LiquidAssetRequest addLiquidAssetRequest(LiquidAssetRequest request) {
	    	//check is request date is null if so give today's date
	    	if(request.getRequestDate()==null)
	    		request.setRequestDate(LocalDate.now());
	    	logger.info("Allocation date was null. Set to current date: "+request.getRequestDate());
	        return liquidAssetRequestRepository.save(request);
	    }

	    public LiquidAssetRequest getById(int id) throws InvalidIdException {
	    	//fetch the liquid asset using id
	        Optional<LiquidAssetRequest> optional = liquidAssetRequestRepository.findById(id);
	        if (optional.isEmpty()) {
	            throw new InvalidIdException("Liquid Asset Request ID is invalid...");
	        }
	        logger.info("LiquidAsset request fetched with ID: " + id);
	        return optional.get();
	    }

	    public List<LiquidAssetRequest> getAll(Pageable pageable) {
	    	 logger.info("Fetched all the liquid asset requests");
	    	 //fetch all the liquid asset request in page format
	        return liquidAssetRequestRepository.findAll(pageable).getContent();
	    }

		public List<LiquidAssetRequest> filterByStatus(String status) {
			//fetch the liquid asset request using the status
			logger.info("Liquid Asset request is filtered by status");
			return liquidAssetRequestRepository.findByStatus(status);
		}

		public List<LiquidAssetRequest> filterByLiquidAssetId(int liquidAssetId) {
			//fetch the liquid asset requests using the liquid asset id 
				logger.info(" Request is filtered by Liquid Asset Id ");
				return liquidAssetRequestRepository.findByLiquidAssetId(liquidAssetId);
		}

		public List<LiquidAssetRequest> filterByRequestDate(LocalDate date) {
           // Fetch and return all requests matching the given request date
           List<LiquidAssetRequest> list = liquidAssetRequestRepository.findByRequestDate(date);
           logger.info("Found liquid asset requests on date: " + date);
           return list;
         }
		
		public List<LiquidAssetRequest> filterByEmployeeId(int id) throws InvalidIdException {
		    // get employee id
		    Employee employee = employeeService.getById(id); 
             // Fetch all requests made by this employee
		    List<LiquidAssetRequest> requests = liquidAssetRequestRepository.findByEmployee(employee);
		    logger.info("Found liquid asset requests for employee ID: " + id);
		    return requests;
		}

		public String deleteByLiquidAssetId(int id) throws InvalidIdException {
		    liquidAssetService.getById(id);
            //find all request using this id
		    List<LiquidAssetRequest> requests = liquidAssetRequestRepository.findByLiquidAssetId(id);
            //delete all request in this id
		    liquidAssetRequestRepository.deleteAll(requests);
		    logger.info("Deleted requests for LiquidAsset ID: " + id);
		    return "All requests for LiquidAsset ID " + id + " have been deleted successfully.";
		}
		
		public String deleteByEmployeeId(int id) throws InvalidIdException {
		    //get employee using the id
		    Employee employee = employeeService.getById(id); 
		    // Find all requests made by this employee
		    List<LiquidAssetRequest> requests = liquidAssetRequestRepository.findByEmployee(employee);
            // Delete all those requests
		    liquidAssetRequestRepository.deleteAll(requests);
		    logger.info("Deleted requests for Employee ID: " + id);
		    return "All requests for Employee ID " + id + " have been deleted successfully.";
		}
		
	}
