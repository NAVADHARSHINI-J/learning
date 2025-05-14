package com.springboot.rest_api.controller;



import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.rest_api.dto.ChartDto;
import com.springboot.rest_api.dto.MessageDto;
import com.springboot.rest_api.exception.InvalidIdException;
import com.springboot.rest_api.model.Category;
import com.springboot.rest_api.model.Product;
import com.springboot.rest_api.model.Vendor;
import com.springboot.rest_api.model.Warehouse;
import com.springboot.rest_api.service.CategoryService;
import com.springboot.rest_api.service.ProductService;
import com.springboot.rest_api.service.VendorService;
import com.springboot.rest_api.service.WarehouseService;

@RestController
@RequestMapping("/api/product")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private VendorService vendorService;
	@Autowired
	private ProductService productService;
	@Autowired
	private MessageDto messageDto;
	@Autowired
	private WarehouseService warehouseService;
	
	/*Instead of writing the try block we can use the globalException handler
	 * for that we can create a package config within that we can write those*/
	
	/* To add the product with foreign keys*/
	@PostMapping("/add/{catId}/{wId}")
	public Product addProduct(@RequestBody Product product,
			@PathVariable int catId,Principal principal ,
			@PathVariable int wId) throws InvalidIdException{
		/*step-1 to get the category by id and validate that*/
		Category category = categoryService.getById(catId);
		
		/* Step-2 get the vendor by id and validate that*/
		Vendor vendor = vendorService.getByUsername(principal.getName());
		 
		// get the warehouse by id
		Warehouse warehouse = warehouseService.getbyId(wId);
		/* step 3 to add it on the product*/
		product.setCategory(category);
		product.setVendor(vendor);
		product.setWarehouse(warehouse);
		/* Step 4 add the products*/
		return productService.add(product);
	}
	
	@GetMapping("/bycategory/{catId}")
	public List<Product> getProductByCategory(@PathVariable int catId,
									@RequestParam int page,
									@RequestParam int size) throws InvalidIdException {
		categoryService.getById(catId);
		Pageable pageable=PageRequest.of(page, size);
		return productService.getByCategory(catId,pageable);
	}
	
	@GetMapping("/byvendor/{vId}")
	public ResponseEntity<?> getProductByVendor(@RequestParam int page,
							@RequestParam int size,@PathVariable int vId) {
		try {
			vendorService.getById(vId);
			Pageable pageable=PageRequest.of(page, size);
			return ResponseEntity.ok(productService.getByVendor(vId,pageable));
		} catch (InvalidIdException e) {
			messageDto.setMessage(e.getMessage());
			messageDto.setStatusCode(400);
			return ResponseEntity.status(400).body(messageDto);
		}
	}
	@GetMapping("/byId/{pId}")
	public Product getById(@PathVariable int pId) throws InvalidIdException {
	   return productService.getById(pId);
	}
	
	@PostMapping("/image/upload/{pid}")
 	public Product uploadImage(@PathVariable int pid, 
 							@RequestParam MultipartFile file) throws IOException, InvalidIdException {
 		
 		return productService.uploadImage(file,pid);
 	}
	
	//api for the charts
	@GetMapping("/chart")
	public ChartDto getChartData() {
		return productService.getChartData();
	}
}
