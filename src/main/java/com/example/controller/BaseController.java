package com.example.controller;

import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.utils.UtilsInfor;

@Controller
public class BaseController {
	@Value("${fbPageAccessToken}")
	private String pageAccessToken;
	@GetMapping("/privacy-policy")
	public String chinhsach() {
		return "privacy-policy";
	}
	@GetMapping("/test")
	@ResponseBody
	public String testService() throws URISyntaxException {
		return UtilsInfor.getUserInfor("3177698848959351", "EAAJAgl7Pj50BALiFGFgSqKYZC7wgVoAL1TlIFjp6ZB9jHrZCZC44hQJe6eAtZCITt7rYMPLUiSDVD9zZA75xHZCksheUj2dE8mc5YZCiXcie0b0DGn06bnmoabsmrWBzMMgJ13A5huqZAoGZBmgZC5vDuuiNtcrqtvGAVHaZB12MhTLwgkg1fm9zWbuu8PDWW7gpWSIZD").getFirst_name();
	}
}
