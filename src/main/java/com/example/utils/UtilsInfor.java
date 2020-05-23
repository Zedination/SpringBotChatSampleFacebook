package com.example.utils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import com.example.gsonobj.ResponseObjCurrent;
import com.example.gsonobj.User;
import com.google.gson.Gson;

public class UtilsInfor {
	@Value("${fbPageAccessToken}")
	public static String pageAccessToken;
	//sử dụng RestTemplate
	public static User getUserInfor(String id, String token) throws URISyntaxException {
		URIBuilder builder = new URIBuilder("https://graph.facebook.com/v7.0/"+id);
		builder.addParameter("access_token", token);
		URI uri = builder.build();
		RestTemplate restTemplate = new RestTemplate();
		User response = restTemplate.getForObject(uri,User.class);
		return response;
	}
	//sử dụng Apache HTTP component
	public static ResponseObjCurrent getCurrentWheather(String location) throws URISyntaxException, ClientProtocolException, IOException {
		HttpClient httpclient = HttpClients.createDefault();
        URIBuilder builder = new URIBuilder("https://api.openweathermap.org/data/2.5/weather");
        builder.setParameter("q", location);
        builder.setParameter("appid", "973409c76a23124126ad59dcfd692ba3");
        URI uri = builder.build();
        HttpGet request = new HttpGet(uri);
        HttpResponse response = httpclient.execute(request);
        HttpEntity entity = response.getEntity();
        int statusCode = response.getStatusLine().getStatusCode();
        String result = EntityUtils.toString(entity);
        if(statusCode == 200) {
        	Gson gson = new Gson();
            ResponseObjCurrent current = gson.fromJson(result,ResponseObjCurrent.class);
            return current;
        }else {
        	return null;
        }
	}
}
