package com.springcore.main.controller;

import com.springcore.main.util.AddressUtil;

public class MyController {
	AddressUtil addUtil;

	public MyController(AddressUtil addUtil) {    //Constructor Injection
		super();
		this.addUtil = addUtil;
	}
	public void getCity(String address) {
		System.out.println(addUtil.getCity(address));
	}
}
