package com.casestudy.mocktest.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.casestudy.mocktest.model.Animal;
import com.casestudy.mocktest.service.AnimalService;

@RestController
@RequestMapping("/api/animal")
public class AnimalController {

	@Autowired
	private AnimalService animalService;
	
	@GetMapping("/byDogName/{dogName}")
	public List<Animal> getByDogName(@PathVariable String dogName) {
		return animalService.getByDogName(dogName);
	}
	
	@GetMapping("/byCatName/{catName}")
	public Animal getByCatName(@PathVariable String catName) {
		return animalService.getByCatName(catName);
	}
	
	@GetMapping("/byDogId/{dogId}")
	public List<Animal> getByDogId(@PathVariable int dogId) {
		return animalService.getByDogId(dogId);
	}
	
	@GetMapping("/upload/{animalId}")
	public ResponseEntity<?> uploadImage(@PathVariable int animalId,
			@RequestBody MultipartFile file) throws IOException {
		animalService.uploadImage(animalId,file);
		return ResponseEntity.ok("uploaded Successfull");
	}
	
	
	
	
}
