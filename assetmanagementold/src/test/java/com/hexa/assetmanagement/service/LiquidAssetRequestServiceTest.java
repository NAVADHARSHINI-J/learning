package com.hexa.assetmanagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.hexa.assetmanagement.exception.InvalidIdException;
import com.hexa.assetmanagement.model.Employee;
import com.hexa.assetmanagement.model.LiquidAssetRequest;
import com.hexa.assetmanagement.repository.LiquidAssetRequestRepository;

@ExtendWith(MockitoExtension.class)
public class LiquidAssetRequestServiceTest{

    @InjectMocks
    private LiquidAssetRequestService requestService;

    @Mock
    private LiquidAssetRequestRepository requestRepository;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private LiquidAssetService liquidAssetService;

    private LiquidAssetRequest request;
    private Employee employee;

    @BeforeEach
    void setup() {
        employee = new Employee(1, "John Doe", "john@example.com", "1234567890", "Engineer", "Chennai");
        request = new LiquidAssetRequest(1, LocalDate.now(), "PENDING", employee, 101);
    }
    @Test
    void addTest() {
        request.setRequestDate(null);
        when(requestRepository.save(any())).thenReturn(request);

        LiquidAssetRequest saved = requestService.addLiquidAssetRequest(request);

        assertNotNull(saved);
        assertNotNull(saved.getRequestDate());
        verify(requestRepository).save(request);
    }

    @Test
    void getByIdTest() throws InvalidIdException {
        when(requestRepository.findById(1)).thenReturn(Optional.of(request));

        LiquidAssetRequest result = requestService.getById(1);

        assertEquals(request, result);
        verify(requestRepository).findById(1);
    }

    @Test
    void testGetById_InvalidId_ThrowsException() {
        when(requestRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(InvalidIdException.class, () -> requestService.getById(1));
    }

    @Test
    void testGetAll() {
        Pageable pageable = PageRequest.of(0, 2);
        when(requestRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(request)));

        List<LiquidAssetRequest> result = requestService.getAll(pageable);

        assertEquals(1, result.size());
        verify(requestRepository).findAll(pageable);
    }

    @Test
    void testFilterByStatus() {
        when(requestRepository.findByStatus("PENDING")).thenReturn(List.of(request));

        List<LiquidAssetRequest> result = requestService.filterByStatus("PENDING");

        assertFalse(result.isEmpty());
    }

    @Test
    void filterByLiquidAssetIdTest() {
        when(requestRepository.findByLiquidAssetId(101)).thenReturn(List.of(request));

        List<LiquidAssetRequest> result = requestService.filterByLiquidAssetId(101);

        assertEquals(1, result.size());
    }

    @Test
    void filterByRequestDateTest() {
        LocalDate today = LocalDate.now();
        when(requestRepository.findByRequestDate(today)).thenReturn(List.of(request));

        List<LiquidAssetRequest> result = requestService.filterByRequestDate(today);

        assertFalse(result.isEmpty());
    }

    @Test
    void testFilterByEmployeeId_Valid() throws InvalidIdException {
        when(employeeService.getById(1)).thenReturn(employee);
        when(requestRepository.findByEmployee(employee)).thenReturn(List.of(request));

        List<LiquidAssetRequest> result = requestService.filterByEmployeeId(1);

        assertEquals(1, result.size());
    }

    @Test
    void deleteByLiquidAssetId() throws InvalidIdException {
        when(liquidAssetService.getById(101)).thenReturn(null);
        when(requestRepository.findByLiquidAssetId(101)).thenReturn(List.of(request));

        String message = requestService.deleteByLiquidAssetId(101);

        assertTrue(message.contains("have been deleted"));
        verify(requestRepository).deleteAll(List.of(request));
    }

    @Test
    void deleteByEmployeeId() throws InvalidIdException {
        when(employeeService.getById(1)).thenReturn(employee);
        when(requestRepository.findByEmployee(employee)).thenReturn(List.of(request));

        String message = requestService.deleteByEmployeeId(1);

        assertTrue(message.contains("have been deleted"));
        verify(requestRepository).deleteAll(List.of(request));
    }
}
    


