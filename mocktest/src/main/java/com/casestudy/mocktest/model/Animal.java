package com.casestudy.mocktest.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Animal {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String a1;
	private String a2;
	@ManyToOne
	private Dog dog;
	@OneToOne
	private Cat cat;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getA1() {
		return a1;
	}
	public void setA1(String a1) {
		this.a1 = a1;
	}
	public String getA2() {
		return a2;
	}
	public void setA2(String a2) {
		this.a2 = a2;
	}
	public Dog getDog() {
		return dog;
	}
	public void setDog(Dog dog) {
		this.dog = dog;
	}
	public Cat getCat() {
		return cat;
	}
	public void setCat(Cat cat) {
		this.cat = cat;
	}
	
}
