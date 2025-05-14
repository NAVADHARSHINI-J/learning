package com.springboot.rest_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.rest_api.exception.InvalidIdException;
import com.springboot.rest_api.model.Manager;
import com.springboot.rest_api.model.ManagerService;
import com.springboot.rest_api.model.Warehouse;
import com.springboot.rest_api.service.WarehouseService;

@RestController
@RequestMapping("/api/warehouse")
@CrossOrigin(origins = "http://localhost:5173")
public class WarehouseController {
	@Autowired
	private WarehouseService warehouseService;
	@Autowired
	private ManagerService managerService;
	
	@PostMapping("/add/{mId}")
	public Warehouse add(@RequestBody Warehouse warehouse,@PathVariable int mId) throws InvalidIdException {
		Manager manager=managerService.getbyId(mId);
		warehouse.setManager(manager);
		return warehouseService.add(warehouse);
	}
	
	@GetMapping("/get/{id}")
	public Warehouse getbyId(@PathVariable int id) throws InvalidIdException {
           return warehouseService.getbyId(id);
	}
	@GetMapping("/getall")
	public List<Warehouse> getall() {
		return warehouseService.getAll();
	}
}
