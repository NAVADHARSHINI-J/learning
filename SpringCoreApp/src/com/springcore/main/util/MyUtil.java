package com.springcore.main.util;

public class MyUtil {
	public String getFirstName(String fullName){
			String[] name=fullName.split(" ");
			return name[0];
		}
	public String getLastName(String fullName){
		String[] name=fullName.split(" ");
		return name[name.length-1];
	}
	
	/*
	 * APPLn-CONTEXT
	 * -------
	 * MyUtility myutil = new MyUtility()
	 */
}
