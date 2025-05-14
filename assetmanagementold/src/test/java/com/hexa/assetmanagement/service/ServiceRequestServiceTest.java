package com.hexa.assetmanagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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

import com.hexa.assetmanagement.exception.InvalidIdException;
import com.hexa.assetmanagement.model.Asset;
import com.hexa.assetmanagement.model.Category;
import com.hexa.assetmanagement.model.Department;
import com.hexa.assetmanagement.model.Employee;
import com.hexa.assetmanagement.model.ServiceRequest;
import com.hexa.assetmanagement.repository.ServiceRequestRepository;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ServiceRequestServiceTest {

	@InjectMocks
	private ServiceRequestService serviceRequestService;
	@Mock
	private ServiceRequestRepository serviceRequestRepository;
	@Mock
	private AssetService assetService;
	@Mock
	private EmployeeService employeeService;
	
	ServiceRequest s1,s2,s3;
	Employee e1,e2;
	Asset a1,a2;
	@BeforeEach
	public void init() {
		a1=new Asset(1,"asset1","model1","Available",LocalDate.of(2024,04,11),"config1", "description1",10,
				new Category(1,"category1"));
		a2=new Asset(2,"asset2","model2","Available",LocalDate.of(2024,04,11),"config2", "description2",12,
				new Category(2,"category2"));
		e1=new Employee(1, "employee1","employee1@gmail.com","9344908756","Chennai",new Department(1,"IT"));
		e2=new Employee(2, "employee2","employee2@gmail.com","6598908756","Mumbai",new Department(2,"FINANCE"));
		s1=new ServiceRequest(1,LocalDate.now() ,"reason1", "image1", "Approved", e1, a1);
		s2=new ServiceRequest(2,LocalDate.now(),"reason2", "image2", "Approved", e1, a1);
		s3=new ServiceRequest(3,null ,"reason3", "image3", "Declined", e1, a1);
	}
	
	@Test
	public void addServiceRequestTest() {
		//expected :s1
		//actual :
		//usecase 1:Correct output
		when(serviceRequestRepository.save(s1)).thenReturn(s1);
		//check whether the output is correct or not
		try {
			assertEquals(s1,serviceRequestService.addServiceRequest(s1,"employee1",1));
		} catch (InvalidIdException e) {
		}
		
		//usecase : 2 check the request date is updated
		when(serviceRequestRepository.save(s3)).thenReturn(s3);
		//check whether the output is correct or not
		try {
			assertEquals(s3,serviceRequestService.addServiceRequest(s3,"employee2",2));
		} catch (InvalidIdException e) {}
		//check that the value of request date is changed first the value is null
		//after the excution it is changed to today date
		assertEquals(LocalDate.now(), s3.getRequestDate());
		
		//usecase: 3 Correct output
		//check whether the output is correct or not
		try {
			assertEquals(s3,serviceRequestService.addServiceRequest(s3,"employee2",2));
		} catch (InvalidIdException e) {}
		
		//usecase : 4 Wrong output
		//when we add the request s3 the expected value will also be s3 but here 
		//changed it to s1 to it can check assertnotequals
		try {
			assertNotEquals(s1,serviceRequestService.addServiceRequest(s3,"employee2",2));
		} catch (InvalidIdException e) {}
		
		//usecase : 5 Exception throws
		
		try {
			when(assetService.getById(10)).thenThrow(new InvalidIdException("Asset Id is invalid...."));
			assertEquals(s1,serviceRequestService.addServiceRequest(s3,"employee2",10));
		} catch (InvalidIdException e) {
			assertEquals("Asset Id is invalid....", e.getMessage());
		}
		verify(serviceRequestRepository,times(4)).save(any(ServiceRequest.class));
	}
	
	@Test
	public void getByIdTest() {
		//expected: s1
		//actual : serviceRequestService.getById(1);
		//usecase :1 (correct output)
		when(serviceRequestRepository.findById(1)).thenReturn(Optional.of(s1));
		//check whether the output is correct or not
		try {
			assertEquals(s1,serviceRequestService.getById(1));
		} catch (InvalidIdException e) { }
	
		//usecase : 2 (throws InvalidIdException)
		//the id 10 is not the correct id so it throws an exception
		try {
			assertEquals(s1,serviceRequestService.getById(10));
		} catch (InvalidIdException e) { 
			assertEquals("Service Request ID is invalid...", e.getMessage());
		}
	
		//usecase 3 :  wrong output
		//when we give the s2 it should return the s2 but here we gave
		//s3 as expected so it has check that it is not equals
		try {
			assertEquals(s1,serviceRequestService.getById(2));
		} catch (InvalidIdException e) { }
		verify(serviceRequestRepository,times(3)).findById(anyInt());
		
	}
	
	@Test
	public void filterByStatusTest() {
		//expected : List<ServiceRequest>
		//actual:serviceRequestService.filterByStatus("Approved");
		//use case :1 (correct output)
		List<ServiceRequest> list=Arrays.asList(s1,s2);
		when(serviceRequestRepository.findByStatus("Approved")).thenReturn(list);
		//check that it filter the list
		assertEquals(list,serviceRequestService.filterByStatus("Approved"));
		
		//use case 2 (for declined)
		list=Arrays.asList(s3);
		when(serviceRequestRepository.findByStatus("Declined")).thenReturn(list);
		//check that it filter the list
		assertEquals(list,serviceRequestService.filterByStatus("Declined"));
		
		//use case 3: (wrong output)
		//for the status approved the expected is s1,s2 but now the s3 is only
		//in the list so it make the asssertion not equals
		assertNotEquals(list,serviceRequestService.filterByStatus("Approved"));
		verify(serviceRequestRepository,times(3)).findByStatus(any(String.class));
	}
	
	@Test
	public void filterByEmployeeIdTest() {
		//expected :List<ServiceRequest>
		//actual:serviceRequestService.filterByEmployeeId(1);
		//use case :1 (correct output)
		List<ServiceRequest> list=Arrays.asList(s1,s2);
		when(serviceRequestRepository.findByEmployeeId(1)).thenReturn(list);
		//check that the output gave is correct
		try {
			assertEquals(list,serviceRequestService.filterByEmployeeId(1));
		} catch (InvalidIdException e) {}
		
		//usecase 2:(wrong output)
		s3.setEmployee(null);
		list=Arrays.asList(s1,s3);
		//In the s3 request there is no employee so it make the assertion not equals
		try {
			assertNotEquals(list,serviceRequestService.filterByEmployeeId(1));
		} catch (InvalidIdException e) {}
		
		//usecase 3: Exception throw
		try {
			when(employeeService.getById(10)).thenThrow(new InvalidIdException("Employee Id is invalid...."));
			assertEquals(list,serviceRequestService.filterByEmployeeId(10));
		} catch (InvalidIdException e) {
			assertEquals("Employee Id is invalid....", e.getMessage());
		}
		verify(serviceRequestRepository,times(2)).findByEmployeeId(anyInt());

	}
	
	@Test
	public void filterByAssetIdTest() {
		//expected :List<ServiceRequest>
		//actual:serviceRequestService.filterByAssetId(1);
		//use case :1 (correct output)
		List<ServiceRequest> list=Arrays.asList(s1,s2);
		when(serviceRequestRepository.findByAssetId(1)).thenReturn(list);
		//check that the output gave is correct
		try {
			assertEquals(list,serviceRequestService.filterByAssetId(1));
		} catch (InvalidIdException e) {}
		
		//usecase 2:(wrong output)
		s3.setAsset(null);
		list=Arrays.asList(s1,s3);
		//In the s3 request there is no employee so it make the assertion not equals
		try {
			assertNotEquals(list,serviceRequestService.filterByAssetId(1));
		} catch (InvalidIdException e) {}
		
		//use case :3 (correct output)
		s3.setAsset(a1);
	    list=Arrays.asList(s1,s2,s3);
		when(serviceRequestRepository.findByAssetId(1)).thenReturn(list);
		//check that the output gave is correct
		try {
			assertEquals(list,serviceRequestService.filterByAssetId(1));
		} catch (InvalidIdException e) {}
		
		//usecase 4: Exception throw
				try {
					when(assetService.getById(10)).thenThrow(new InvalidIdException("Asset Id is invalid...."));
					assertEquals(list,serviceRequestService.filterByAssetId(10));
				} catch (InvalidIdException e) {
					assertEquals("Asset Id is invalid....", e.getMessage());
				}
		verify(serviceRequestRepository,times(3)).findByAssetId(anyInt());

	}
	
	@Test
	public void deleteByEmployeeIdTest() {
		//expected: deleted message
		//actual: serviceRequestService.deleteByEmployeeId(1);
		//usecsase :1 (correct output)
		//create a list to check that the service request with given id is 
		//deleted at the end
		//at last we have only s3 in the list
		s1.setEmployee(e1);
		s2.setEmployee(e1);
		s3.setEmployee(e2);
		List<ServiceRequest> list=Arrays.asList(s1,s2,s3);
		try {
			when(employeeService.getById(1)).thenReturn(e1);
		} catch (InvalidIdException e) { }
		when(serviceRequestRepository.findAll()).thenReturn(list);
		//check that the message is returned or not 
		try {
			assertEquals("Service request is deleted successfully",
					serviceRequestService.deleteByEmployeeId(1));
		} catch (InvalidIdException e) {}
		//here I written expected as s1,s2 because this will be go into the 
		//delete all method after the filteration to delete
		List<ServiceRequest> expected=Arrays.asList(s1,s2);
		//check that deleteAll method is called
		verify(serviceRequestRepository,times(1)).deleteAll(expected);
		
		//usecase : 2 (throws InvalidIdException)
		//we don't have a asset id 10
		try {
			when(employeeService.getById(10)).thenThrow(new InvalidIdException("Employee Id is invalid...."));
			assertEquals("Service request is deleted successfully",
					serviceRequestService.deleteByEmployeeId(10));
		} catch (InvalidIdException e) {
			assertEquals("Employee Id is invalid....", e.getMessage());
		}
		
		//usecase 3:
		s1.setEmployee(e1);
		s2.setEmployee(e2);
		s3.setEmployee(e2);
		list=Arrays.asList(s1,s2,s3);
		try {
			when(employeeService.getById(1)).thenReturn(e1);
		} catch (InvalidIdException e) { }
		when(serviceRequestRepository.findAll()).thenReturn(list);
		//check that the message is returned or not 
		try {
			assertEquals("Service request is deleted successfully",
					serviceRequestService.deleteByEmployeeId(1));
		} catch (InvalidIdException e) {}
		//here I written expected as aa1 because this will be go into the 
		//delete all method after the filteration to delete
		expected=Arrays.asList(s1);
		//check that deleteAll method is called
		verify(serviceRequestRepository,times(1)).deleteAll(expected);	
	}
	
	@Test
	public void deleteByAssetIdTest() {
		//expected: deleted message
		//actual: serviceRequestService.deleteByAssetId(1);
		//usecsase :1 (correct output)
		//create a list to check that the service request with given id is 
		//deleted at the end
		//at last we have only s3 in the list
		s1.setAsset(a1);
		s2.setAsset(a1);
		s3.setAsset(a2);
		List<ServiceRequest> list=Arrays.asList(s1,s2,s3);
		try {
			when(assetService.getById(1)).thenReturn(a1);
		} catch (InvalidIdException e) { }
		when(serviceRequestRepository.findAll()).thenReturn(list);
		//check that the message is returned or not 
		try {
			assertEquals("Service request is deleted successfully",
					serviceRequestService.deleteByAssetId(1));
		} catch (InvalidIdException e) {}
		//here I written expected as s1,s2 because this will be go into the 
		//delete all method after the filteration to delete
				List<ServiceRequest> expected=Arrays.asList(s1,s2);
		//check that deleteAll method is called
		verify(serviceRequestRepository,times(1)).deleteAll(expected);
		
		//usecase : 2 (throws InvalidIdException)
		//we don't have a asset id 10
		try {
			when(assetService.getById(10)).thenThrow(new InvalidIdException("Asset Id is invalid...."));
			assertEquals("Service request is deleted successfully",
					serviceRequestService.deleteByAssetId(10));
		} catch (InvalidIdException e) {
			assertEquals("Asset Id is invalid....", e.getMessage());
		}
		
		//usecase 3:
		s1.setAsset(a1);
		s2.setAsset(a2);
		s3.setAsset(a2);
		list=Arrays.asList(s1,s2,s3);
		try {
			when(assetService.getById(1)).thenReturn(a1);
		} catch (InvalidIdException e) { }
		when(serviceRequestRepository.findAll()).thenReturn(list);
		//check that the message is returned or not 
		try {
			assertEquals("Service request is deleted successfully",
					serviceRequestService.deleteByAssetId(1));
		} catch (InvalidIdException e) {}
		//here I written expected as s1 because this will be go into the 
		//delete all method after the filteration to delete
		expected=Arrays.asList(s1);
		//check that deleteAll method is called
		verify(serviceRequestRepository,times(1)).deleteAll(expected);		
	}
	
	@Test
	public void updateTest() {
		//expected : s1
		//actual:serviceRequestService.update(s1, 1);
		//use case 1: correct output
		when(serviceRequestRepository.findById(1)).thenReturn(Optional.of(s1));
		when(serviceRequestRepository.save(s1)).thenReturn(s1);
		//check that the output is correct or not
		try {
			assertEquals(s1,serviceRequestService.update(s1, 1));
		} catch (InvalidIdException e) {	}
		
		//usecase : 2 (throws InvalidIdException)
		//10 is not the valid id
		try {
			assertEquals(s1,serviceRequestService.update(s1, 10));
		} catch (InvalidIdException e) {
			assertEquals("Service Request ID is invalid...",e.getMessage());
		}
		
		//usecase : 3 (check for wrong output)
		//the update of s1 return the s1 but it is s2 so it make the assertion 
		//not equal
		try {
			assertNotEquals(s2,serviceRequestService.update(s1, 1));
		} catch (InvalidIdException e) {	}
		
	}

}






