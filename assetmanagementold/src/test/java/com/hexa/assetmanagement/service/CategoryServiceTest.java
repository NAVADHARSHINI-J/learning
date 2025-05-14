package com.hexa.assetmanagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import com.hexa.assetmanagement.model.Category;
import com.hexa.assetmanagement.repository.CategoryRepository;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
	
	@Mock
	private CategoryRepository categoryRepository;
	
	@InjectMocks
	private CategoryService categoryService;

	Category c1,c2,c3,c11;
	
	@BeforeEach
	public void init() {
		c1=new Category(1,"Mobile");
		c2=new Category(2,"Laptop");
		c3=new Category(3,"Monitor");
		c11=new Category(1,"Mobile");	
	}
	
	@Test
	public void addCategoryTest() {
		//expected : c11
		//actual  : categoryService.addCategory(c1);
		//usecase :1
		when(categoryRepository.save(c1)).thenReturn(c11);
		assertEquals(c11, categoryService.addCategory(c1));
		
		//usecase :2
		when(categoryRepository.save(c1)).thenReturn(c11);
		assertNotEquals(c2, categoryService.addCategory(c1));
		
		verify(categoryRepository,times(2)).save(c1);
	}
	
	public void getByIdTest() {
		//expected : c2
		//actual  : categoryService.getById(2);
		//use case : 1 (correct output)
		when(categoryRepository.findById(2)).thenReturn(Optional.of(c2));
		try {
			assertEquals(c2,categoryService.getById(2));
		} catch (InvalidIdException e) {  }
		
		//usecase : 2 (exception throw)
		try {
			assertEquals(c2,categoryService.getById(10));
		} catch (InvalidIdException e) { 
			assertEquals("Category Id is invalid....", e.getMessage());
		}
		
		//use case : 3 (wrong output)
		when(categoryRepository.findById(3)).thenReturn(Optional.of(c3));
		try {
			assertNotEquals(c3,categoryService.getById(2));
		} catch (InvalidIdException e) {  }
		
		verify(categoryRepository,times(2)).findById(2);
	}
	
	@Test
	public void getAllTest() {
		//expected : List<Category>
		//actual  : categoryService.getall();
		//usecase :1  (correct output)
		List<Category> list=Arrays.asList(c1,c2,c3);
		when(categoryRepository.findAll()).thenReturn(list);
		assertEquals(list,categoryService.getall());
		
		//usecase :2 
		list=Arrays.asList(c1,c2);
		when(categoryRepository.findAll()).thenReturn(list);
		assertEquals(list,categoryService.getall());
	}
	
}








