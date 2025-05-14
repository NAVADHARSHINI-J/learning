package com.hexa.assetmanagement.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.times;
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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.hexa.assetmanagement.exception.InvalidIdException;
import com.hexa.assetmanagement.model.Asset;
import com.hexa.assetmanagement.model.Category;
import com.hexa.assetmanagement.repository.AssetRepository;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AssetServiceTest {

	 @InjectMocks
	    private AssetService assetService;

	    @Mock
	    private AssetRepository assetRepository;

	    private Asset a1;
	    private Asset a2;

	    @BeforeEach
	    void setUp() { 
	        a1 = new Asset();
	        a1.setId(1);
	        a1.setName("Laptop");
	        a1.setModel("Dell XPS");
	        a1.setStatus("Available");
	        a1.setDate(LocalDate.now());
	        a1.setConfiguration("16GB RAM, 512GB SSD");
	        a1.setDescription("New office laptop");
	        a1.setQuantity(1);
	        
	        a2 = new Asset();
	        a2.setId(2);
	        a2.setName("TV");
	        a2.setModel("Panasonic");
	        a2.setStatus("Not-Available");
	        a2.setDate(LocalDate.now());
	        a2.setConfiguration("Andriod Tv");
	        a2.setDescription("good performance");
	        a2.setQuantity(1);
	    }

	    @Test
	    void addAsset() {
	        
	    	//case 1: checking for correct output.
	    	when(assetRepository.save(a1)).thenReturn(a1);
	    	
	    	assertEquals(a1, assetService.addAsset(a1));
	    	
	    	//case 2: checking for incorrect output.
	    	assertNotEquals(a2, assetService.addAsset(a1));
	    	
	    	verify(assetRepository, times(2)).save(a1);
	    }

	    @Test
	    void getById() {
	    	
	    	//case 1: checking for correct output.
	        when(assetRepository.findById(1)).thenReturn(Optional.of(a1));
	        try {
				assertEquals(a1, assetService.getById(1));
			} catch (InvalidIdException e) { 
			}
	        
	      //case 2: checking for incorrect output. 
	        try {
				assertNotEquals(a1, assetService.getById(2));
			} catch (InvalidIdException e) { 
				assertEquals("Asset Id is invalid....", e.getMessage());
			}
	        
	        verify(assetRepository, times(1)).findById(1);
	        verify(assetRepository, times(1)).findById(2);
	    }
 
	    @Test
	    void getAll() {
	    	
	    	//case 1: checking for correct output.
	        Pageable pageable = PageRequest.of(0, 5);
	        List<Asset> list = List.of(a1, a2);
	        Page<Asset> page = new PageImpl<>(list);

	        when(assetRepository.findAll(pageable)).thenReturn(page);

	        assertEquals(page, assetService.getAll(pageable));
	        
	        //case 2: checking for incorrect output.

	        assertNotEquals(List.of(a2), assetService.getAll(pageable));
	        
	        verify(assetRepository, times(2)).findAll(pageable);
	    }

	    @Test
	    void filterByName() {
	    	
	    	//case 1: checking for correct output.
	        when(assetRepository.findByName("Laptop")).thenReturn(List.of(a1)); 

	        assertEquals(1, assetService.filterByName("Laptop").size());
	        assertEquals(List.of(a1), assetService.filterByName("Laptop"));
	        
	        //case 2: checking for incorrect output.
	        assertNotEquals(a2, assetService.filterByName("Laptop"));
	        
	        verify(assetRepository, times(3)).findByName("Laptop");
	    }

//	    @Test
//	    void filterByCategory() {
//	    	
//	    	//case 1: checking for correct output.
//	        when(assetRepository.findByCategoryName("Electronics")).thenReturn(List.of(a1, a2));
//	     
//	        assertEquals(List.of(a1,a2), assetService.filterByCategory("Electronics"));
//	        assertEquals(2, assetService.filterByCategory("Electronics").size());
//	        
//	        //case 2: checking for incorrect output.
//	        
//	        assertNotEquals(List.of(a1), assetService.filterByCategory("Electronics"));
//	        assertNotEquals(3, assetService.filterByCategory("Electronics").size());
//	        
//	        verify(assetRepository, times(4)).findByCategoryName("Electronics");
//	    }

	    @Test
	    void filterByStatus() {
	    	
	    	//case 1: checking for correct output.
	        when(assetRepository.findByStatus("Available")).thenReturn(List.of(a1));
 
	        assertEquals(List.of(a1), assetService.filterByStatus("Available"));
	        
	        //case 2: checking for incorrect output.
	        assertNotEquals(List.of(a2), assetService.filterByStatus("Available"));
	        
	        verify(assetRepository, times(2)).findByStatus("Available");
	    }

	    @Test
	    void updateAsset() {
	       
	    	 // case 1: Full successful update
	        Asset oldAsset = new Asset(1, "Laptop", "Dell Inspiron", "Available",
	                LocalDate.of(2024, 1, 10), "16GB RAM, 512GB SSD", "Standard issue", 3, new Category(1,"ELECTRONICS"));
	        Asset newAsset = new Asset(1, "Updated Laptop", "HP EliteBook", "Assigned",
	                LocalDate.of(2024, 12, 1), "32GB RAM, 1TB SSD", "Upgraded model", 5,null);

	        when(assetRepository.save(Mockito.<Asset>any()))
	            .thenAnswer(invocation -> invocation.getArgument(0));

	        Asset updated=null;
			try {
				updated = assetService.updateAsset(newAsset, oldAsset);
			} catch (InvalidIdException e) {
			}
	        assertEquals("Updated Laptop", updated.getName());
	        assertEquals("HP EliteBook", updated.getModel());
	        assertEquals("Assigned", updated.getStatus());
	        assertEquals(LocalDate.of(2024, 12, 1), updated.getDate());
	        assertEquals("32GB RAM, 1TB SSD", updated.getConfiguration());
	        assertEquals("Upgraded model", updated.getDescription());
	        assertEquals(5, updated.getQuantity());

	        // case 2: Partial update (only some fields updated)
	        oldAsset = new Asset(1, "Laptop", "Dell Inspiron", "Available",
	                LocalDate.of(2024, 1, 10), "16GB RAM, 512GB SSD", "Standard issue", 3, new Category(1,"ELECTRONICS"));
	        newAsset = new Asset(1, null, null, "Assigned",
	                null, null, "New description only", 0, null);

	        try {
				updated = assetService.updateAsset(newAsset, oldAsset);
			} catch (InvalidIdException e) {
			}
	        assertEquals("Laptop", updated.getName()); // unchanged
	        assertEquals("Dell Inspiron", updated.getModel()); // unchanged
	        assertEquals("Assigned", updated.getStatus()); // updated
	        assertEquals("New description only", updated.getDescription()); // updated
	        assertEquals(3, updated.getQuantity()); // unchanged

	        // case 3: Invalid quantity (if business rule: quantity can't be negative)
	        oldAsset = new Asset(1, "Laptop", "Dell Inspiron", "Available",
	                LocalDate.of(2024, 1, 10), "16GB RAM, 512GB SSD", "Standard issue", 3, new Category(1,"ELECTRONICS"));
	        newAsset = new Asset(1, "Tablet", "Samsung", "In Repair",
	                LocalDate.of(2024, 10, 10), "4GB RAM", "Faulty", 2,null);

	        try {
				updated = assetService.updateAsset(newAsset, oldAsset);
			} catch (InvalidIdException e) {
			} 
	        assertNotEquals(1, updated.getQuantity()); 
	        assertEquals(2, updated.getQuantity());

	        // case 4: Only name and configuration update
	        oldAsset = new Asset(1, "Laptop", "Dell Inspiron", "Available",
	                LocalDate.of(2024, 1, 10), "16GB RAM, 512GB SSD", "Standard issue", 3,new Category(1,"ELECTRONICS"));
	        newAsset = new Asset(1, "Tablet", null, null,
	                null, "4GB RAM", null, 0, null);

	        try {
				updated = assetService.updateAsset(newAsset, oldAsset);
			} catch (InvalidIdException e) {
			}
	        assertEquals("Tablet", updated.getName()); // updated
	        assertEquals("4GB RAM", updated.getConfiguration()); // updated
	        assertEquals("Dell Inspiron", updated.getModel()); // unchanged
	        assertEquals("Available", updated.getStatus()); // unchanged

	        // case 5: No updates - all fields null or default
	        oldAsset = new Asset(1, "Laptop", "Dell Inspiron", "Available",
	                LocalDate.of(2024, 1, 10), "16GB RAM, 512GB SSD", "Standard issue", 3, new Category(1,"ELECTRONICS"));
	        newAsset = new Asset();

	        try {
				updated = assetService.updateAsset(newAsset, oldAsset);
			} catch (InvalidIdException e) {
			}
	        assertEquals("Laptop", updated.getName());
	        assertEquals("Dell Inspiron", updated.getModel());
	        assertEquals("Available", updated.getStatus());
	        assertEquals(LocalDate.of(2024, 1, 10), updated.getDate());
	        assertEquals("16GB RAM, 512GB SSD", updated.getConfiguration());
	        assertEquals("Standard issue", updated.getDescription());
	        assertEquals(3, updated.getQuantity());
	        

	        verify(assetRepository, times(5)).save(Mockito.<Asset>any());
	    }

	    @Test
	    void deleteAssetById() {
	        // No return type for delete — just verifying interaction

	        assertEquals("Asset deleted successfully", assetService.deleteAssetById(a1));
	        verify(assetRepository, times(1)).delete(a1);
	    }
}
