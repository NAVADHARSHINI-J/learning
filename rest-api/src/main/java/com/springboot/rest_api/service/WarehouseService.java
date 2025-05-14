package com.springboot.rest_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.rest_api.exception.InvalidIdException;
import com.springboot.rest_api.model.Warehouse;
import com.springboot.rest_api.repository.WarehouseRepository;

@Service
public class WarehouseService {

	@Autowired
	private WarehouseRepository warehouseRepository;
	
	public Warehouse add(Warehouse warehouse) {
		return warehouseRepository.save(warehouse);
	}

	public Warehouse getbyId(int id) throws InvalidIdException {
		Optional<Warehouse> op=warehouseRepository.findById(id);
		if(op.isEmpty())
			throw new InvalidIdException("Invalid Warehouse Id");
		return op.get();
	}

	public List<Warehouse> getAll() {
		return warehouseRepository.findAll();
	}

}
