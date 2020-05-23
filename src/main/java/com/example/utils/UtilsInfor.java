package com.example.utils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import com.example.gsonobj.Message;
import com.example.gsonobj.ResponseObjCurrent;
import com.example.gsonobj.SendMessageObj;
import com.example.gsonobj.User;
import com.google.gson.Gson;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.send.IdMessageRecipient;
import com.restfb.types.send.SendResponse;

public class UtilsInfor {
	@Value("${fbPageAccessToken}")
	public static String pageAccessToken;
	public static ArrayList<String> ListUserDk = new ArrayList<String>();
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
	public static void sendMessage(String userId, String message, String token) {
		IdMessageRecipient recipient = new IdMessageRecipient(userId);
		FacebookClient pageClient = new DefaultFacebookClient(pageAccessToken, Version.VERSION_7_0);

		SendResponse resp = pageClient.publish("me/messages", SendResponse.class,
		     Parameter.with("recipient", recipient), // the id or phone recipient
			 Parameter.with("message", message));
		
	}
}
