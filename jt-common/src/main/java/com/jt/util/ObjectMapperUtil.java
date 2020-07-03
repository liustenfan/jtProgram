package com.jt.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
//简化代码而生!!
public class ObjectMapperUtil {
	
	//定义一个常量对象
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	//1.将对象转化为json串
	public static String toJSON(Object target) {
		
		String json = null;
		try {
			json = MAPPER.writeValueAsString(target);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			//将检查异常,转化为运行时异常!!!!
			throw new RuntimeException(e);
		}
		return json;
	}
	
	//2.将json串按照指定的类型转化为对象
	//实现:传递什么类型,就返回什么对象
	// <T> 定义泛型
	public static <T> T  toObj(String json,Class<T> target) {
		T t = null;
		try {
			t = MAPPER.readValue(json, target);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return t;
	}
}
