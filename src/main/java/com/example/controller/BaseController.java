package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BaseController {
	@GetMapping("/privacy-policy")
	public String chinhsach() {
		return "privacy-policy";
	}
}
