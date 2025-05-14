package com.casestudy.mocktest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.casestudy.mocktest.model.Animal;

public interface AnimalRepository extends JpaRepository<Animal, Integer>{

	List<Animal> findByDogDname(String dogName);

	Animal findByCatCname(String catName);

	List<Animal> findByDogId(int dogId);

}
