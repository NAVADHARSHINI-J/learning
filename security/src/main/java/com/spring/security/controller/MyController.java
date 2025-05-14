package com.spring.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sample")
public class MyController {

	@GetMapping("/public/hello")
	public String sayHello() {
		return "Welcome to spring in public.....";
	}
	@GetMapping("/private/hello")
	public String sayPrivateHello() {
		return "Welcome to spring in private.....";
	}
}
