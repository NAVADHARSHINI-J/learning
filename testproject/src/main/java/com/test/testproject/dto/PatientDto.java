package com.test.testproject.dto;

import org.springframework.stereotype.Component;


@Component
public class PatientDto {

	private String name;
	private int age;
	private String illness;
	private int num_of_years;
	private String current_medication;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getIllness() {
		return illness;
	}
	public void setIllness(String illness) {
		this.illness = illness;
	}
	public int getNum_of_years() {
		return num_of_years;
	}
	public void setNum_of_years(int num_of_years) {
		this.num_of_years = num_of_years;
	}
	public String getCurrent_medication() {
		return current_medication;
	}
	public void setCurrent_medication(String current_medication) {
		this.current_medication = current_medication;
	}
	
}
