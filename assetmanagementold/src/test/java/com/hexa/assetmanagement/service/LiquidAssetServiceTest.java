package com.hexa.assetmanagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import com.hexa.assetmanagement.exception.InvalidIdException;
import com.hexa.assetmanagement.model.LiquidAsset;
import com.hexa.assetmanagement.repository.LiquidAssetAllocationRepository;
import com.hexa.assetmanagement.repository.LiquidAssetRepository;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class LiquidAssetServiceTest {

    @Mock
    private LiquidAssetRepository liquidAssetRepository;

    @InjectMocks
    private LiquidAssetService liquidAssetService;

    @Mock
    private LiquidAssetAllocationRepository liquidAssetAllocationRepository;

    LiquidAsset la1, la2, la3, la4;

    @BeforeEach
    public void init() {
        la1 = new LiquidAsset(1, "Asset1", 1000, 500, "Active", "Description1");
        la2 = new LiquidAsset(2, "Asset2", 1500, 1000, "Inactive", "Description2");
        // Invalid Remaining Amount
        la3 = new LiquidAsset(3, "Asset3", 2000, 0, "Active", "Description3"); 
        la4 = new LiquidAsset(4, "Asset4", 1200, 800, "Active", "Description4");
    }

    @Test
    public void addTest() {
        // Use case 1: Valid Liquid Asset
        when(liquidAssetRepository.save(la1)).thenReturn(la1);
        assertEquals(la1, liquidAssetService.addliquidAsset(la1));

        // Use case 2: Check if it fails when saving
        when(liquidAssetRepository.save(la2)).thenReturn(la2);
        assertNotEquals(la1, liquidAssetService.addliquidAsset(la2));
    }

    @Test
    public void getByIdTest() {
        // Use case 1: Valid ID
        when(liquidAssetRepository.findById(2)).thenReturn(Optional.of(la2));
        try {
            assertEquals(la2, liquidAssetService.getById(2));
        } catch (InvalidIdException e) {
        }

        // Use case 2: Invalid ID
        try {
            liquidAssetService.getById(10);
        } catch (InvalidIdException e) {
            assertEquals("Liquid asset Id is invalid!", e.getMessage());
        }

        // Use case 3: Wrong expected value
        try {
            assertNotEquals(la3, liquidAssetService.getById(2));
        } catch (InvalidIdException e) {
        }

        verify(liquidAssetRepository, times(2)).findById(2);
        verify(liquidAssetRepository, times(1)).findById(10);
    }

    @Test
    public void updateTest() {
        LiquidAsset laUpdate = new LiquidAsset();
        laUpdate.setName("Updated Asset");
        laUpdate.setRemainingAmount(300);

        // Use case 1: Valid update
        when(liquidAssetRepository.findById(3)).thenReturn(Optional.of(la3));
        when(liquidAssetRepository.save(la3)).thenReturn(la3);
        try {
            assertEquals(la3, liquidAssetService.update(laUpdate, 3));
        } catch (InvalidIdException e) {
        }

        // Use case 2: Invalid ID
        try {
            liquidAssetService.update(laUpdate, 10);
        } catch (InvalidIdException e) {
            assertEquals("Liquid asset Id is invalid!", e.getMessage());
        }

        // Use case 3: Invalid remaining amount
        laUpdate.setRemainingAmount(-100);
        try {
            liquidAssetService.update(laUpdate, 3);
        } catch (InvalidIdException e) {
            assertEquals("Remaining amount cannot be negative", e.getMessage());
        }

        verify(liquidAssetRepository, times(2)).save(la3);
    }

    @Test
    public void deleteByIdTest() {
        // Use case 1: Valid deletion
        when(liquidAssetRepository.findById(4)).thenReturn(Optional.of(la4));
        when(liquidAssetAllocationRepository.findAll()).thenReturn(List.of()); // No allocations to delete
        try {
            assertEquals("Liquid asset allocations deleted successfully.", liquidAssetService.deleteById(4));
        } catch (InvalidIdException e) {
        }

        // Use case 2: Invalid ID
        try {
            liquidAssetService.deleteById(10);
        } catch (InvalidIdException e) {
            assertEquals("Liquid asset Id is invalid!", e.getMessage());
        }
    }

    @Test
    public void getAllTest() {
        // Use case 1: Fetch all liquid assets with pagination
        when(liquidAssetRepository.findAll(PageRequest.of(0, 10))).thenReturn(new PageImpl<>(List.of(la1, la2, la4)));
        
        // Ensure the size of the result matches the expected number of liquid assets
        assertEquals(3, liquidAssetService.getAll(PageRequest.of(0, 10)).size());
    }

    @Test
    public void filterByStatusTest() {
        // Use case 1: Filter by Active status
        when(liquidAssetRepository.findByStatus("Active")).thenReturn(List.of(la1, la4));
        assertEquals(2, liquidAssetService.filterByStatus("Active").size());
    }

    @Test
    public void filterByNameTest() {
        // Use case 1: Filter by Name
        when(liquidAssetRepository.findByName("Asset1")).thenReturn(List.of(la1));
        assertEquals(1, liquidAssetService.filterByName("Asset1").size());
    }
}

