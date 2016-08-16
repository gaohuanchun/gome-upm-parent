package com.gome.test;

import org.apache.commons.httpclient.HttpClient;
import org.apache.http.client.methods.HttpPost;

public class HttpClientTest {

	public void testPost(){
		HttpClient client = new HttpClient();
		HttpPost post = new HttpPost("http://10.58.56.72:8081/alarmplatform/alarm");
	}
}
