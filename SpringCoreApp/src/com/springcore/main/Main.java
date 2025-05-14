package com.springcore.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.springcore.main.config.MyConfig;
import com.springcore.main.controller.MyController;
import com.springcore.main.util.AddressUtil;

public class Main {

	public static void main(String[] args) {
		ApplicationContext context=new AnnotationConfigApplicationContext(MyConfig.class);
		MyController myControl=new MyController(context.getBean(AddressUtil.class));
		myControl.getCity("101,kings-lane some-address mumbai 459798 india");
		
		((AnnotationConfigApplicationContext) context).close();

	}

}
