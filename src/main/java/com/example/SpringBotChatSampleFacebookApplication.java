package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"me.ramswaroop.jbot", "com.example"})
public class SpringBotChatSampleFacebookApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBotChatSampleFacebookApplication.class, args);
	}
	
//	@Bean
//	public RestTemplate restTemplate() {
//	    return new RestTemplate();
//	}
}
