package com.casestudy.main;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.casestudy.main.config.AppConfig;
import com.casestudy.main.controller.BookController;
import com.casestudy.main.factory.BookFactory;
import com.casestudy.main.model.Book;

public class App {

	public static void main(String[] args) {
		ApplicationContext context = 
 				new AnnotationConfigApplicationContext(AppConfig.class);
 		
 		BookController bookController = BookFactory.getBookController(); 
 		List<Book> list =  bookController.getBooks();
 		for(Book b : list) {
 			System.out.println(b);
 		}
 		((AnnotationConfigApplicationContext)context).close();
 		
 	}
 }