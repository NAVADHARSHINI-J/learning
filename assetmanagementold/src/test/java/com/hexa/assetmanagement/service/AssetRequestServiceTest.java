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

import com.hexa.assetmanagement.exception.InvalidIdException;
import com.hexa.assetmanagement.model.Asset;
import com.hexa.assetmanagement.model.AssetRequest;
import com.hexa.assetmanagement.model.Category;
import com.hexa.assetmanagement.model.Department;
import com.hexa.assetmanagement.model.Employee;
import com.hexa.assetmanagement.model.User;
import com.hexa.assetmanagement.repository.AssetRequestRepository;
import com.hexa.assetmanagement.repository.EmployeeRepository;
import com.hexa.assetmanagement.repository.UserRepository;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AssetRequestServiceTest {

	@Mock
	private AssetRequestRepository assetRequestRepository;

	@InjectMocks
	   private AssetRequestService assetRequestService;
	
	@Mock
	private AssetService assetService; 
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private EmployeeRepository employeeRepository;
	@Mock
	private EmployeeService employeeService;


	AssetRequest as1;
	AssetRequest as2;

	@BeforeEach
	public void gitint() {
		as1 = new AssetRequest(1, LocalDate.of(2024, 12, 11), "for new project", "approved",
				new Employee(1, "sheryl", "sheryl@gmail.com", "9076234187", "no.22, 4th street",
						new Department(1, "IT"), new User(1, "sheryl", "1234", "EMPLOYEE")),
				new Asset(1, "msi laptop", "modern 14", "available", LocalDate.of(2024, 12, 11), "16gb ram", "good product", 4,
						new Category(1, "laptop")));
		
		as2 = new AssetRequest(2, LocalDate.of(2024, 04, 10), "for personal use", "declined",
				new Employee(2, "nava", "nava@gmail.com", "8025671893", "no.32, 5th street", new Department(2, "FINANCE"),
						new User(2, "nava", "1234", "EMPLOYEE")),
				new Asset(2, " panasonic", "new model", "available", LocalDate.of(2024, 04, 10), "andriod tv", "good product", 6,
						new Category(2, "television")));
		
		
	}
	
	@Test
	public void addAssetRequest() {
		
		try {
			when(assetService.getById(1)).thenReturn(as1.getAsset());
		} catch (InvalidIdException e)  {
		}
		when(userRepository.findByUsername("sheryl")).thenReturn(as1.getEmployee().getUser());
		when(employeeRepository.findByUser(as1.getEmployee().getUser())).thenReturn(Optional.of(as1.getEmployee()));
		when(assetRequestRepository.save(as1)).thenReturn(as1);
		
		//case 1: checking for getting correct output.
		 
		try {
			assertEquals(as1, assetRequestService.addAssetRequest(1, "sheryl", as1));
		} catch (InvalidIdException e) { 
		}
		
		//case 2: checking for incorrect output.
		
		try {
			assertNotEquals(as2, assetRequestService.addAssetRequest(1, "sheryl", as1));
		} catch (InvalidIdException e) {  
		}
		
		//case 3: check if getById returns correct output.
		
		try {
			assertEquals(as1.getAsset(), assetService.getById(1));
		} catch (InvalidIdException e) { 
		}
		
		//case 4: check if getById returns incorrect output
		//And throws InvalidIdException.
		
		try {
			assertNotEquals(as2.getAsset(), assetService.getById(1));
		} catch (InvalidIdException e) { 
			assertEquals("Asset Id is invalid....", e.getMessage());
		}
		
		//case 5: check if findByUsername gets correct output.
		
		assertEquals(as1.getEmployee().getUser(), userRepository.findByUsername("sheryl"));
		
		//case 6: intentionally passing different username.
		
		assertNotEquals(as1.getEmployee().getUser(), userRepository.findByUsername("nava"));
		
		//case 7:check if getting employee by user
		
		assertEquals(as1.getEmployee(), 
				employeeRepository.findByUser(as1.getEmployee().getUser()).get());
													// .get() because it returns an optional.
		
		//case 8: intentionally expecting other employee details by passing a different user.
		assertNotEquals(as2.getEmployee(), 
				employeeRepository.findByUser(as1.getEmployee().getUser()).get());
		
		verify(assetRequestRepository, times(2)).save(as1);
		
	}
	
	@Test
	public void getById() {
		
		//case 1: getting the correct output.
		
		when(assetRequestRepository.findById(1)).thenReturn(Optional.of(as1));
		
		try {
			assertEquals(as1, assetRequestService.getById(1));
		} catch (InvalidIdException e) { 
		}
		
		//case 2: intentionally passing wrong id to check for incorrect output.
		
        when(assetRequestRepository.findById(2)).thenReturn(Optional.of(as2));
		
		try {
			assertNotEquals(as1, assetRequestService.getById(2));
		} catch (InvalidIdException e) { 
			assertEquals("Asset Request Id is invalid", e.getMessage());
		}
		
		verify(assetRequestRepository, times(1)).findById(1);
		verify(assetRequestRepository, times(1)).findById(2);
	}
	
	@Test
	public void getAllAssetRequest() {
		
		//case 1: getting the correct list of requests.
		List<AssetRequest> list = Arrays.asList(as1, as2);
		when(assetRequestRepository.findAll()).thenReturn(list);
		assertEquals(list, assetRequestService.getAllAssetRequest());
		assertEquals(2, assetRequestService.getAllAssetRequest().size());
		
		//case 2: getting incorrect output.
		list=Arrays.asList(as2);
		assertNotEquals(list, assetRequestService.getAllAssetRequest());
		assertNotEquals(3, assetRequestService.getAllAssetRequest().size());
		
		verify(assetRequestRepository, times(4)).findAll();
		
	}
	
	@Test
	public void filterByStatus() {
		
		//case 1: getting correct output.
		List<AssetRequest> list = Arrays.asList(as1);
		when(assetRequestRepository.findByStatus("approved")).thenReturn(list);
		
		assertEquals(list, assetRequestService.filterByStatus("approved"));
		
		//case 2: checking for incorrect output.
		list=Arrays.asList(as2);
		assertNotEquals(list, assetRequestService.filterByStatus("approved"));
		
		verify(assetRequestRepository, times(2)).findByStatus("approved");
	}
	
	@Test
	public void filterByEmployeeId() {
		
		//case 1: getting correct output.
		List<AssetRequest> list = Arrays.asList(as1);
		try {
		when(employeeService.getById(1)).thenReturn(as1.getEmployee());
		} catch (InvalidIdException e) {  
		}
		when(assetRequestRepository.findByEmployee(as1.getEmployee())).thenReturn(list);
		
		try {
			assertEquals(list, assetRequestService.filterByEmployeeId(1));
		} catch (InvalidIdException e) { 
		}
		
		//case 2: checking for getting wrong output.
 
		try {
			assertNotEquals(list, assetRequestService.filterByEmployeeId(2));
		} catch (InvalidIdException e) {  
		     assertEquals("Employee Id is invalid....", e.getMessage());
		}
		
		verify(assetRequestRepository, times(1)).findByEmployee(as1.getEmployee());
	}
	
	@Test
	public void filterByAssetId() {
		
		//case 1: checking for correct output.
	    List<AssetRequest> list = Arrays.asList(as1);

	    try {
	        when(assetService.getById(1)).thenReturn(as1.getAsset());
	    } catch (InvalidIdException e) {}

	    when(assetRequestRepository.findByAsset(as1.getAsset())).thenReturn(list);

	    try {
	        assertEquals(list, assetRequestService.filterByAssetId(1));
	    } catch (InvalidIdException e) {}

	    //case 2: checking for incorrect output.
	    try {
	        assertNotEquals(Arrays.asList(as2), assetRequestService.filterByAssetId(1));
	    } catch (InvalidIdException e) {
	        assertEquals("Asset Id is invalid....", e.getMessage());
	    }

	    verify(assetRequestRepository, times(2)).findByAsset(as1.getAsset());
	}

	@Test
	public void filterByRequestDate() {
		
		//case 1: checking for correct output.
	    List<AssetRequest> list = Arrays.asList(as1);
	    LocalDate requestDate = LocalDate.of(2024, 12, 11);

	    when(assetRequestRepository.findByRequestDate(requestDate)).thenReturn(list);

	    assertEquals(list, assetRequestService.filterByRequestDate(requestDate));
	    
	    //case 2: checking for incorrect output.
	    assertNotEquals(Arrays.asList(as2), assetRequestService.filterByRequestDate(requestDate));

	    verify(assetRequestRepository, times(2)).findByRequestDate(requestDate);
	}

	@Test
	public void updateStatus() {
		
		//case 1: checking for correct output.
	    try {
	        when(assetRequestRepository.findById(1)).thenReturn(Optional.of(as1));
	        when(assetRequestRepository.save(as1)).thenReturn(as1);

	        AssetRequest updated = assetRequestService.updateStatus("declined", 1);
	        assertEquals("declined", updated.getStatus());

	    //case 2: checking for incorrect output.
	        assertNotEquals("approved", updated.getStatus());

	        verify(assetRequestRepository, times(1)).save(as1);
	    } catch (InvalidIdException e) {}
	}

	@Test
	public void deleteAssetRequestByAsset() {
		
		//case 1: checking for correct output.
	    List<AssetRequest> list = Arrays.asList(as1);
	    when(assetRequestRepository.findByAsset(as1.getAsset())).thenReturn(list);

	    String result = assetRequestService.deleteAssetRequestByAsset(as1.getAsset());
	    assertEquals("Asset request using asset id deleted successfully", result);

	    verify(assetRequestRepository, times(1)).deleteAll(list);
	}

	@Test
	public void deleteAssetRequestByEmployee() {
		
		//case 1: checking for correct output.
	    List<AssetRequest> list = Arrays.asList(as1);
	    when(assetRequestRepository.findByEmployee(as1.getEmployee())).thenReturn(list);

	    String result = assetRequestService.deleteAssetRequestByEmployee(as1.getEmployee());
	    assertEquals("Asset request using employee id deleted successfully", result);

	    verify(assetRequestRepository, times(1)).deleteAll(list);
	}

}
