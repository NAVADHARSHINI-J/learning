package com.hexa.assetmanagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.hexa.assetmanagement.exception.AssetUnavailableException;
import com.hexa.assetmanagement.exception.InvalidIdException;
import com.hexa.assetmanagement.model.Asset;
import com.hexa.assetmanagement.model.AssetAllocation;
import com.hexa.assetmanagement.model.Category;
import com.hexa.assetmanagement.model.Department;
import com.hexa.assetmanagement.model.Employee;
import com.hexa.assetmanagement.repository.AssetAllocationRepository;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AssetAllocationServiceTest {

	@Mock
	private AssetAllocationRepository assetAllocationRepository;
	@Mock
	private  AssetService assetService;
	@Mock
	private EmployeeService employeeService;
	
	@InjectMocks
	private AssetAllocationService assetAllocationService;
	
	Asset a1,a2;
	Employee e1,e2;
	AssetAllocation aa1,aa2,aa3;
	
	@BeforeEach
	public void init() {
		a1=new Asset(1,"asset1","model1","Available",LocalDate.of(2024,04,11),"config1", "description1",10,
				new Category(1,"category1"));
		a2=new Asset(2,"asset2","model2","Available",LocalDate.of(2024,04,11),"config2", "description2",12,
				new Category(2,"category2"));
		e1=new Employee(1, "employee1","employee1@gmail.com","9344908756","Chennai",new Department(1,"IT"));
		e2=new Employee(2, "employee2","employee2@gmail.com","6598908756","Mumbai",new Department(2,"FINANCE"));
		aa1=new AssetAllocation(1, LocalDate.of(2024,03,11), null,"ALLOCATED");
		aa2=new AssetAllocation(2, null, null,"Allocated");
		aa3=new AssetAllocation(3, LocalDate.of(2024,03,15), null,"ALLOCATED");
	}
	
	@Test
	public void addAssetAllocationTest() {
		//expected : aa1
		//actual : assetAllocationService.addAssetAllocation(1, 1, aa1);
		
		//use case 1:(correct output)
		//when we give the assetId and empId in the add method it will check the asset found or not
		//so that we also mock the repository of asset and employee
		try {
			when(assetService.getById(1)).thenReturn(a1);
			when(employeeService.getById(1)).thenReturn(e1);
		} catch (InvalidIdException e) { }
		when(assetAllocationRepository.save(aa1)).thenReturn(aa1);
		AssetAllocation actual = null;
		try {
			actual=assetAllocationService.addAssetAllocation(1, 1, aa1);
		} catch (InvalidIdException | AssetUnavailableException e) { }
		//check the assetallocation is added or not
		assertEquals(aa1,actual);
		//check whether the employee is added
		assertEquals(1, actual.getEmployee().getId());
		//check whether the asset added 
		assertEquals(1,actual.getAsset().getId());
		
		//usecase: 2 (check allocation date)
		//check whether the allocation date is added automatically or not
		when(assetAllocationRepository.save(aa2)).thenReturn(aa2);
		try {
			actual=assetAllocationService.addAssetAllocation(1, 1, aa2);
		} catch (InvalidIdException | AssetUnavailableException e) { }
		//check the assetallocation is added or not
		assertEquals(aa2,actual);
		//check the date is added or not in aa2 the allocated date is null
		assertEquals(LocalDate.now(),actual.getAllocationDate());
		
		//usecase : 3 (check the quantity is reduced or not)
		//the quantity in asset 2 is 12
		try {
			when(assetService.getById(2)).thenReturn(a2);
		} catch (InvalidIdException e) {		}
		try {
			actual=assetAllocationService.addAssetAllocation(2, 1, aa2);
		} catch (InvalidIdException | AssetUnavailableException e) { }
		//check the assetallocation is added or not
		assertEquals(aa2,actual);
		//check the quantity is reduced original=12 check now it as 11
		assertEquals(11,a2.getQuantity());
		
		//usecase :4 (throws AssetUnavailableException)
		a2.setQuantity(0);
		try {
			actual=assetAllocationService.addAssetAllocation(2, 1, aa2);
		} catch (InvalidIdException | AssetUnavailableException e) { 
			assertEquals("asset is not available.....", e.getMessage());
		}
		 
		//usecase : 5(throws InvalidIdException for asset)
		try {
			when(assetService.getById(5)).thenThrow(new InvalidIdException("Asset Id is invalid...."));
			//wrong asset id is given here
			assertEquals(aa2, assetAllocationService.addAssetAllocation(5, 1, aa2));
		} catch (InvalidIdException | AssetUnavailableException e) { 
			assertEquals("Asset Id is invalid....", e.getMessage());
		}
		
		//usecase : 6 (throws InvalidIdException for employee)
		try {
			when(employeeService.getById(5)).thenThrow(new InvalidIdException("Employee Id is invalid...."));
			//wrong employee id is given here
			actual=assetAllocationService.addAssetAllocation(1, 5, aa2);
		} catch (InvalidIdException | AssetUnavailableException e) { 
			assertEquals("Employee Id is invalid....", e.getMessage());
		}
	}
	
	@Test
	public void getByIdTest() {
		//expected : aa1
		//actual : assetAllocationService.getById(1);
		//usecase :1 (check the correct output)
		//check that the FindById method works or not
		when(assetAllocationRepository.findById(1)).thenReturn(Optional.of(aa1));
		try {
			assertEquals(aa1,assetAllocationService.getById(1));
		} catch (InvalidIdException e) { }
		
		//usecase : 2  (throws InvalidIdException)
		try {
			//we don't have a asset allocation with id 10
			assertEquals(aa1,assetAllocationService.getById(10));
		} catch (InvalidIdException e) {
			assertEquals("Asset Allocation Id is invalid", e.getMessage());
		}
		
		//usecase : 3 (wrong output)
		try {
			//for id 1 the correct assetallocation is aa1 but here we gave a wrong asset allocation
			assertNotEquals(aa2,assetAllocationService.getById(1));
		} catch (InvalidIdException e) { }
	}
	
	@Test
	public void deleteByAssetIdTest() {
		//expected: deleted message
		//actual: assetAllocationService.deleteByAssetId(1);
		//usecsase :1 (correct output)
		//create a list to check that the asset allocation with given id is 
		//deleted at the end
		//at last we have only aa3 in the list
		aa1.setAsset(a1);
		aa2.setAsset(a1);
		aa3.setAsset(a2);
		List<AssetAllocation> list=Arrays.asList(aa1,aa2,aa3);
		try {
			when(assetService.getById(1)).thenReturn(a1);
		} catch (InvalidIdException e) { }
		when(assetAllocationRepository.findAll()).thenReturn(list);
		//check that the message is returned or not 
		try {
			assertEquals("Assset allocation is deleted successfully",
					assetAllocationService.deleteByAssetId(1));
		} catch (InvalidIdException e) {}
		//here I written expected as aa1,aa2 because this will be go into the 
		//delete all method after the filteration to delete
				List<AssetAllocation> expected=Arrays.asList(aa1,aa2);
		//check that deleteAll method is called
		verify(assetAllocationRepository,times(1)).deleteAll(expected);
		
		//usecase : 2 (throws InvalidIdException)
		//we don't have a asset id 10
		try {
			when(assetService.getById(10)).thenThrow(new InvalidIdException("Asset Id is invalid...."));
			assertEquals("Assset allocation is deleted successfully",
					assetAllocationService.deleteByAssetId(10));
		} catch (InvalidIdException e) {
			assertEquals("Asset Id is invalid....", e.getMessage());
		}
		
		//usecase 3:
		aa1.setAsset(a1);
		aa2.setAsset(a2);
		aa3.setAsset(a2);
		list=Arrays.asList(aa1,aa2,aa3);
		try {
			when(assetService.getById(1)).thenReturn(a1);
		} catch (InvalidIdException e) { }
		when(assetAllocationRepository.findAll()).thenReturn(list);
		//check that the message is returned or not 
		try {
			assertEquals("Assset allocation is deleted successfully",
					assetAllocationService.deleteByAssetId(1));
		} catch (InvalidIdException e) {}
		//here I written expected as aa1 because this will be go into the 
		//delete all method after the filteration to delete
		expected=Arrays.asList(aa1);
		//check that deleteAll method is called
		verify(assetAllocationRepository,times(1)).deleteAll(expected);
		
	}

	@Test
	public void deleteByEmployeeIdTest() {
		//expected: deleted message
		//actual: assetAllocationService.deleteByEmployeeId(1);
		//usecsase :1 (correct output)
		//create a list to check that the asset allocation with given id is 
		//deleted at the end
		//at last we have only aa3 in the list
		aa1.setEmployee(e1);
		aa2.setEmployee(e1);
		aa3.setEmployee(e2);
		List<AssetAllocation> list=Arrays.asList(aa1,aa2,aa3);
		try {
			when(employeeService.getById(1)).thenReturn(e1);
		} catch (InvalidIdException e) { }
		when(assetAllocationRepository.findAll()).thenReturn(list);
		//check that the message is returned or not 
		try {
			assertEquals("Assset allocation is deleted successfully",
					assetAllocationService.deleteByEmployeeId(1));
		} catch (InvalidIdException e) {}
		//here I written expected as aa1,aa2 because this will be go into the 
		//delete all method after the filteration to delete
				List<AssetAllocation> expected=Arrays.asList(aa1,aa2);
		//check that deleteAll method is called
		verify(assetAllocationRepository,times(1)).deleteAll(expected);
		
		//usecase : 2 (throws InvalidIdException)
		//we don't have a asset id 10
		try {
			when(employeeService.getById(10)).thenThrow(new InvalidIdException("Employee Id is invalid...."));
			assertEquals("Assset allocation is deleted successfully",
					assetAllocationService.deleteByEmployeeId(10));
		} catch (InvalidIdException e) {
			assertEquals("Employee Id is invalid....", e.getMessage());
		}
		
		//usecase 3:
		aa1.setEmployee(e1);
		aa2.setEmployee(e2);
		aa3.setEmployee(e2);
		list=Arrays.asList(aa1,aa2,aa3);
		try {
			when(employeeService.getById(1)).thenReturn(e1);
		} catch (InvalidIdException e) { }
		when(assetAllocationRepository.findAll()).thenReturn(list);
		//check that the message is returned or not 
		try {
			assertEquals("Assset allocation is deleted successfully",
					assetAllocationService.deleteByEmployeeId(1));
		} catch (InvalidIdException e) {}
		//here I written expected as aa1 because this will be go into the 
		//delete all method after the filteration to delete
		expected=Arrays.asList(aa1);
		//check that deleteAll method is called
		verify(assetAllocationRepository,times(1)).deleteAll(expected);
		
	}
	
	@Test
	public void updateTest() {
		//expected : aa1
		//actual:assetAllocationService.update(aa1, 1);
		//use case 1: correct output
		//set the asset for allocation
		aa1.setAsset(a1);
		when(assetAllocationRepository.findById(1)).thenReturn(Optional.of(aa1));
		when(assetAllocationRepository.save(aa1)).thenReturn(aa1);
		//check that the output is correct or not
		try {
			assertEquals(aa1,assetAllocationService.update(aa1, 1));
		} catch (InvalidIdException e) {	}
		
		
		//usecase 2: check that the value is changed for return date
		//for return date
		assertEquals(LocalDate.now(), aa1.getReturnDate());
		
		//usecase : 3
		//check the quantity of the asset is increased by 1
		when(assetService.addAsset(a1)).thenReturn(a1);
		assertEquals(11,a1.getQuantity());
		
		//usecase : 4 (throws InvalidIdException)
		//10 is not the valid id
		try {
			assertEquals(aa1,assetAllocationService.update(aa1, 10));
		} catch (InvalidIdException e) {
			assertEquals("Asset Allocation Id is invalid",e.getMessage());
		}
		
		//usecase : 5(check for wrong output)
		//the update of aa1 return the aa1 but it is aa2 so it make the assertion 
		//not equal
		try {
			assertNotEquals(aa2,assetAllocationService.update(aa1, 1));
		} catch (InvalidIdException e) {	}
		
		
	}
	
	@Test
	public void getAssetAllocationByAssetIdTest() {
		//expected : List<AssetAllocation>
		//actual :assetAllocationService.getAssetAllocationByAssetId(1);
		
		//usecase 1:check for correct output
		aa1.setAsset(a1);
		aa2.setAsset(a1);
		List<AssetAllocation> list=Arrays.asList(aa1,aa2); 
		try {
			when(assetService.getById(1)).thenReturn(a1);
		} catch (InvalidIdException e) {		}
		when(assetAllocationRepository.findByAsset(a1)).thenReturn(list);
		try {
			assertEquals(list,assetAllocationService.getAssetAllocationByAssetId(1));
		} catch (InvalidIdException e) {	}
		
		//usecase :2 (throws exception)
		try {
			//there is no asset found with id 10 soit throws an exception
			when(assetService.getById(10))
					.thenThrow(new InvalidIdException("Asset Id is invalid...."));
			assertEquals(list,assetAllocationService.getAssetAllocationByAssetId(10));
		} catch (InvalidIdException e) {
			assertEquals("Asset Id is invalid....", e.getMessage());
		}
		
		
		//usecase 3: wrong output
		//here the aa3 contains a2 when the method called it should not came as output
		//but here the list is came with this aa3 ao it makes the assertion not equals
		aa3.setAsset(a2);
		list=Arrays.asList(aa1,aa2,aa3); 
		try {
			assertNotEquals(list,assetAllocationService.getAssetAllocationByAssetId(1));
		} catch (InvalidIdException e) {	}	
	}
	
	@Test
	public void getAssetAllocationByEmployeeIdTest() {
		//expected : List<AssetAllocation>
		//actual :assetAllocationService.getAssetAllocationByEmployeeId(1);
		
		//usecase 1:check for correct output
		aa1.setEmployee(e1);
		aa2.setEmployee(e1);
		List<AssetAllocation> list=Arrays.asList(aa1,aa2); 
		try {
			when(employeeService.getById(1)).thenReturn(e1);
		} catch (InvalidIdException e) {		}
		when(assetAllocationRepository.findByEmployee(e1)).thenReturn(list);
		try {
			assertEquals(list,assetAllocationService.getAssetAllocationByEmployeeId(1));
		} catch (InvalidIdException e) {	}
		
		//usecase :2 (throws exception)
		try {
			//there is no employee found with id 10 so it throws an exception
			when(employeeService.getById(10))
					.thenThrow(new InvalidIdException("Employee Id is invalid...."));
			assertEquals(list,assetAllocationService.getAssetAllocationByEmployeeId(10));
		} catch (InvalidIdException e) {
			assertEquals("Employee Id is invalid....", e.getMessage());
		}
		
		
		//usecase 3: wrong output
		//here the aa3 contains e2 when the method called it should not came as output
		//but here the list is came with this aa3 ao it makes the assertion not equals
		aa3.setEmployee(e2);
		list=Arrays.asList(aa1,aa2,aa3); 
		try {
			assertNotEquals(list,assetAllocationService.getAssetAllocationByEmployeeId(1));
		} catch (InvalidIdException e) {	}
		
	}
}








