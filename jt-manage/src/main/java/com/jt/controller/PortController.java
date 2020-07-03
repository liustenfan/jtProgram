package com.jt.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PortController {
	
	//需求说明:通过用户的请求,获取端口号信息
	@Value("${server.port}")	//将服务端口号 动态注入
	private Integer port;
	
	
	@RequestMapping("/getPort")
	public String getPort() {
		
		return "当前访问服务器的端口号:"+port;
	}
}
