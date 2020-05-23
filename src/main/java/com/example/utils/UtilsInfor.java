package com.example.utils;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriBuilder;

import com.example.gsonobj.User;
import com.google.gson.Gson;

public class UtilsInfor {
	@Value("${fbPageAccessToken}")
	public static String pageAccessToken;
	
	public static User getUserInfor(String id, String token) throws URISyntaxException {
		URIBuilder builder = new URIBuilder("https://graph.facebook.com/v7.0/"+id);
		builder.addParameter("access_token", token);
		URI uri = builder.build();
		RestTemplate restTemplate = new RestTemplate();
		User response = restTemplate.getForObject(uri,User.class);
		return response;
	}
}
