package com.casestudy.mocktest.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.casestudy.mocktest.model.Animal;
import com.casestudy.mocktest.repository.AnimalRepository;

@Service
public class AnimalService {

	@Autowired
	private AnimalRepository animalRepository;
	public List<Animal> getByDogName(String dogName) {
		return animalRepository.findByDogDname(dogName);
	}
	public Animal getByCatName(String catName) {
		return animalRepository.findByCatCname(catName);
	}
	public List<Animal> getByDogId(int dogId) {
		return animalRepository.findByDogId(dogId);
	}
	public void uploadImage(int animalId, MultipartFile file) throws IOException {
		//get the animal from id
	    Optional<Animal> animal= animalRepository.findById(animalId);
	    if(animal.isEmpty())
	    	throw new RuntimeException("Animal Id Is invalid");
	    //get the allowed extension
	    List<String> allowedExtension = Arrays.asList("jpg","jpeg","png","svg");
	    //get the file name
	    String fileName = file.getOriginalFilename();
	    //get the extension
	    String ex = fileName.split("//.")[1];
	    //check extension allowed or not
	    if(!(allowedExtension.contains(ex)))
	    	throw new RuntimeException("Invalid image type");

	    String uploadPath = "E://UploadImage//";
	    
	    Files.createDirectories(Paths.get(uploadPath));
	    
	    Path path = Paths.get(uploadPath+"//"+ fileName);
	    
	    //copy the file here
	    Files.copy(file.getInputStream(), path,StandardCopyOption.REPLACE_EXISTING);
	    //set the url
	    //save the file
	}

}
