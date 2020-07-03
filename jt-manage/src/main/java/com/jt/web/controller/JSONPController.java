package com.jt.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 该类的主要的作用是实现JSONP跨域访问.
 * @author LYJ
 *
 */
@RestController	//返回的数据要求是json
public class JSONPController {
	
	/**
	 * 1. 网址:http://manage.jt.com/web/testJSONP?callback=jQuery1111 自动生成自动拼接
	 * 2.返回值的格式要求:  callback(JSON)  规定!!!!!
	 * @return
	 */
	@RequestMapping("/web/testJSONP")
	public String jsonp(String callback) {
		
		String json = "{'name':'jsonp跨域调用成功!!!!!'}";
		return callback + "("+json+")";
	}
}
