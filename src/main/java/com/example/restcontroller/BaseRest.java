package com.example.restcontroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseRest {
	@GetMapping("/")
	public String welcome() {
		return "Welcome to my first webhook!";
	}
}
