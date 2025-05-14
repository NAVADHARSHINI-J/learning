package com.casestudy.mocktest.dto;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class BookDto {

	private String title;

	private double price;

	private String isbn;
	
	private List<Integer> authorId;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public List<Integer> getAuthorId() {
		return authorId;
	}

	public void setAuthorId(List<Integer> authorId) {
		this.authorId = authorId;
	}
	
}
