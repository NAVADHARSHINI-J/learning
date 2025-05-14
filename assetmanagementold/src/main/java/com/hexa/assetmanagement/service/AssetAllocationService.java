package com.hexa.assetmanagement.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexa.assetmanagement.exception.AssetUnavailableException;
import com.hexa.assetmanagement.exception.InvalidIdException;
import com.hexa.assetmanagement.model.Asset;
import com.hexa.assetmanagement.model.AssetAllocation;
import com.hexa.assetmanagement.model.Employee;
import com.hexa.assetmanagement.repository.AssetAllocationRepository;

@Service
public class AssetAllocationService {

	@Autowired
	private AssetService assetService;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private AssetAllocationRepository assetAllocationRepository;
	
	Logger logger=LoggerFactory.getLogger("AssetAllocationService");

	/* 1. Get the asset by using id and set it in asset allocation 
	 * 2. Get the employee by using the id and set it in asset allocation 
	 * 3. Check the quantity of the asset is greater than 0 else throw the assetUnavailable exception
	 * 4. check that the date is given if not set date as today data 
	 * 5. reduce the quantity of the asset by 1 and save that asset
	 * 6. save the asset allocation*/
	public AssetAllocation addAssetAllocation(int assetId, int empId,
			AssetAllocation assetAllocation)
			throws InvalidIdException, AssetUnavailableException {
		//check the asset id
		Asset asset = assetService.getById(assetId);
		//check the quantity of the asset
		if(asset.getQuantity()<=0)
			throw new AssetUnavailableException("asset is not available.....");
		assetAllocation.setAsset(asset);
		//check the allocation date
		if(assetAllocation.getAllocationDate()==null)
			assetAllocation.setAllocationDate(LocalDate.now());
		//check the employee id and add it to asset allocation
		Employee employee = employeeService.getById(empId);
		assetAllocation.setEmployee(employee);
		//After allocation reduce the one quantity of the asset 
		asset.setQuantity((asset.getQuantity())-1);

		assetService.addAsset(asset);
		
		logger.info("Asset "+asset.getName()+"is assigned for the employee "+employee.getName());
		
		return assetAllocationRepository.save(assetAllocation);
	}

	/* 1. get the asset by using the id by repository method if found return the asset
	 * 2. else throw the exception*/
	public AssetAllocation getById(int assetAllocationId) throws InvalidIdException {
		//check the asset allocation is find with the id or not
		Optional<AssetAllocation> optional = assetAllocationRepository.findById(assetAllocationId);
		if (optional.isEmpty())
			throw new InvalidIdException("Asset Allocation Id is invalid");
		logger.info("Asser allocation is found with id "+optional.get().getId());
		return optional.get();
	}

	/* get all the allocation by using the repository method*/
	public List<AssetAllocation> getAllAssetAllocation() {
		logger.info("All asset allocation is retrieved from database");
		return assetAllocationRepository.findAll();
	}

	/* 1. get the asset by using id
	 * 2. find all the asset allocation having this assetId and store it in the list
	 * 3. delete the list by using repository deleteAll(list) method */
	public String deleteByAssetId(int assetId) throws InvalidIdException {
		//check that the asset id is found or not
		Asset asset=assetService.getById(assetId);
		//find all the assetallocation with this asset
		List<AssetAllocation> list=assetAllocationRepository.findAll().stream()
		.filter(a->a.getAsset().getId()==asset.getId()).toList();
		//delete the assetallocation
		assetAllocationRepository.deleteAll(list);
		logger.info("Asset allocation with asset Id "+asset.getId()+" is deleted");
		return "Assset allocation is deleted successfully";
	}

	/* 1. get the employee by using id
	 * 2. find all the asset allocation having this employee Id and store it in the list
	 * 3. delete the list by using repository deleteAll(list) method */
	public String deleteByEmployeeId(int empId) throws InvalidIdException {
		//check that the employee with id is found or not
		Employee employee=employeeService.getById(empId);
		//find all the asset allocation with this Employee id
		List<AssetAllocation> list=assetAllocationRepository.findAll().stream()
				.filter(a->a.getEmployee().getId()==employee.getId()).toList();
		//delete the assetallocation
		assetAllocationRepository.deleteAll(list);
		logger.info("Asset allocation with employee Id "+employee.getId()+" is deleted");
		return "Assset allocation is deleted successfully";
	}

	/* 1. get the allocation by using the allocation id
	 * 2. check that the new allocation fields or null or not if not
	 * set the fields to the old allocation
	 * 3. check the return date if null set the return date as today date
	 * 4. get the asset from allocation and then increase the quantity by one and then save it 
	 * to the asset
	 * 5. save the asset allocation*/
	public AssetAllocation update(AssetAllocation assetAllocation, int allocationId)
			throws InvalidIdException {
		//get the asset allocation by the id
		AssetAllocation assetAllocation1 =getById(allocationId);
		//check the allocation date
		if(assetAllocation.getAllocationDate()!=null)
			assetAllocation1.setAllocationDate(assetAllocation.getAllocationDate());
		//check the employee
		if(assetAllocation.getEmployee()!=null)
			assetAllocation1.setEmployee(assetAllocation.getEmployee());
		//check the asset
		if(assetAllocation.getAsset()!=null)
			assetAllocation1.setAsset(assetAllocation.getAsset());
		//if the return date of the asset allocation is null then return date 
		//will be the current date
		if(assetAllocation.getReturnDate()==null)
			assetAllocation1.setReturnDate(LocalDate.now());
		//if return date is not null
		if(assetAllocation.getReturnDate()!=null)
			assetAllocation1.setReturnDate(assetAllocation.getReturnDate());
		if(assetAllocation.getReason()!=null)
			assetAllocation1.setReason(assetAllocation.getReason());
		logger.debug(assetAllocation.getStatus());
		//make the status as returned if they not give anything
		if(assetAllocation.getStatus()!=null) {
			assetAllocation1.setStatus(assetAllocation.getStatus());
		}
		//if the asset is returned then increase the quantity of the asset by one
		//get the asset
		Asset asset=assetAllocation1.getAsset();
		asset.setQuantity(asset.getQuantity()+1);
		//add the asset
		assetService.addAsset(asset);
		logger.info("Asset allocation is updated");
		//save it in the asset allocation
		return assetAllocationRepository.save(assetAllocation1);
	}

	/*1. get the asset by using the asset id 
	 * 2. using repository method find all allocation by the asset
	 * 3. filter the allocation by the status allocated*/
	public List<AssetAllocation> getAssetAllocationByAssetId(int assetId) 
			throws InvalidIdException {
		//check that asset is found with assetid
		Asset asset=assetService.getById(assetId);
		logger.info("Asset allocation is retrieved by assetId "+asset.getId());
		//get all the records by asset
		List<AssetAllocation> assetallocate = assetAllocationRepository.findByAsset(asset);
		return assetallocate
				.stream().
				filter(e->e.getStatus().equalsIgnoreCase("ALLOCATED")).toList();
	}
	
	/*1. get the employee by using the employee id 
	 * 2. using repository method find all allocation by the employee
	 * 3. filter the allocation by the status allocated*/
	public List<AssetAllocation> getAssetAllocationByEmployeeId(int empId) 
			throws InvalidIdException {
		//check that employee is found with empId
		Employee employee=employeeService.getById(empId);
		logger.info("Asset allocation is retrieved by employee Id "+employee.getId());
		//get all the records by employee
		List<AssetAllocation> assetallocate=assetAllocationRepository.findByEmployee(employee);
		return assetallocate
				.stream().
				filter(e->e.getStatus().equalsIgnoreCase("ALLOCATED")).toList();
	}

	/*this method to used to filter the allocation by using both asset id and employee id
	 * 1. filter allocation by using the assetid
	 * 2. filter the allocation by using the employeeid
	 * 3. filter the allocation by using the status allocated
	 * 4. this three filteration can be done in single list so that we can able to filter
	 * the allocation by using the both employee and assset id */
	public AssetAllocation getAllocationByAssetEmpId(int assetId, int empId) {
		List<AssetAllocation> assetAllocation = assetAllocationRepository.findAll()
				.stream().filter(a->a.getAsset().getId()==assetId)
				.filter(aa->aa.getEmployee().getId()==empId)
				.filter(a->a.getStatus().equalsIgnoreCase("ALLOCATED")).toList();
		return assetAllocation.getFirst();
	}

}
