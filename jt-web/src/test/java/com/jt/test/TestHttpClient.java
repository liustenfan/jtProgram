package com.jt.test;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;

public class TestHttpClient {
	
	/**
	 * 目的: jt-web 访问jt-sso获取服务器数据
	 *url:http://localhost:8093/findAll
	 *实现步骤:
	 *	1.新建httpClient工具API对象
	 *	2.确定请求的网址
	 *	3.封装请求方式    get/post/put/delete
	 *	4.发起请求     
	 *	5.判断用户的请求是否正确.
	 *	6.如果请求正确,则获取返回值结果
	 *	
	 *	总结: httpClient相当于在java程序内部内置了一个浏览器.通过http请求协议
	 *   动态获取远程服务器资源. 该用法是一种万能的用法.
	 */
	@Test
	public void testGet() {
		CloseableHttpClient client = HttpClients.createDefault();	//实例化对象
		//String url = "http://localhost:8093/findAll";
		String url = "https://www.baidu.com";
		HttpGet httpGet = new HttpGet(url);
		try {
			CloseableHttpResponse response = client.execute(httpGet);
			//判断状态码信息是否为200
			int status = response.getStatusLine().getStatusCode();
			if(status == 200) {
				System.out.println("客户端请求服务端正常");
				//表示服务端响应正常.
				HttpEntity httpEntity = response.getEntity();	//获取服务端响应数据
				//将实体对象转化为字符串输出.
				String result = EntityUtils.toString(httpEntity,"UTF-8");
				System.out.println(result);
			}else {
				System.out.println("客户端请求服务器异常:"+status);
			}
		} catch (IOException e) {
			
			System.out.println("客户端请求服务器异常");
		}
	}
	
	
	
	
}
