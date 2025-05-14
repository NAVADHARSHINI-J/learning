package com.springboot.rest_api.model;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.rest_api.exception.InvalidContactException;
import com.springboot.rest_api.exception.InvalidIdException;
import com.springboot.rest_api.repository.ManagerRepository;

@Service
public class ManagerService {

	@Autowired
	private ManagerRepository managerRepository;
	
	public Manager add(Manager manager) throws InvalidContactException {
		if(manager.getContact().length()!=10)
			throw new InvalidContactException("Invalid Contact number");		
		return managerRepository.save(manager);
	}

	public Manager getbyId(int id) throws InvalidIdException {
		Optional<Manager> op=managerRepository.findById(id);
		if(op.isEmpty())
			throw new InvalidIdException("Invalid Manager Id");
		return op.get();
	}

	public List<Manager> getAll() {
		return managerRepository.findAll();
	}
	
	

}
