package com.casestudy.mocktest.dto;

import java.util.List;

import org.springframework.stereotype.Component;

import com.casestudy.mocktest.model.Student;

@Component
public class PageDto {
	private List<Student> list;
	private int currentPage;
	private int totalPages;
	private int totalElements;
	private int size;
	public List<Student> getList() {
		return list;
	}
	public void setList(List<Student> list) {
		this.list = list;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public int getTotalElements() {
		return totalElements;
	}
	public void setTotalElements(int totalElements) {
		this.totalElements = totalElements;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
}
