package com.jt.test.httpclient;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

/**
 * 定位请求路径
 * 创建httpClient对象
 * 创建请求的方式类型get/post
 * 发起请求，获取响应的信息
 * 检查状态码的信息是否为200
 * 获取返回值有效信息
 * @author Administrator
 *
 */
public class TestHttpClient {
	@Test
	public void test() throws ClientProtocolException, IOException{
		String url="https://www.baidu.com";
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet httpGet=new HttpGet(url);
		
		CloseableHttpResponse httpResponse = client.execute(httpGet);
		if(httpResponse.getStatusLine().getStatusCode()==200){
			String result = EntityUtils.toString(httpResponse.getEntity());
			System.out.println(result);
		}
	}
}
