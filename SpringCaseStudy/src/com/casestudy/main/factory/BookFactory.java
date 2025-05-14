package com.casestudy.main.factory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.casestudy.main.config.AppConfig;
import com.casestudy.main.controller.BookController;
import com.casestudy.main.repository.BookRepository;
import com.casestudy.main.service.BookService;


public class BookFactory {
	 
 	static ApplicationContext context = 
 			new AnnotationConfigApplicationContext(AppConfig.class);
 	
 	public static BookController getBookController(){
 		return context.getBean(BookController.class);
 	}
 	
 	public static BookService getBookService(){
 		return context.getBean(BookService.class);
 
 		}
 	
 
 	public static BookRepository getBookRepository(){
 		return context.getBean(BookRepository.class);
 
 	}
 
 }