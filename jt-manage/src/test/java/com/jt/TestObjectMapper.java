package com.jt;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.pojo.ItemDesc;
import com.jt.util.ObjectMapperUtil;

public class TestObjectMapper {
	
	@Test
	public void testObject() throws JsonProcessingException {
		//1.创建工具API对象
		ObjectMapper objectMapper = new ObjectMapper();
		//2.封装转化对象
		ItemDesc itemDesc  = new ItemDesc();
		itemDesc.setItemId(1001L).setItemDesc("测试json转化")
				.setCreated(new Date()).setUpdated(itemDesc.getCreated());
		//对象------JSON-------String字符串
		//3.对象转化为JSON时,调用的是对象的get()
		String json = objectMapper.writeValueAsString(itemDesc);
		System.out.println(json);  //{key:value,key2:value2}
		
		//4.将json串转化为对象 调用的是对象的set方法为属性赋值....
		ItemDesc itemDesc2 = objectMapper.readValue(json, ItemDesc.class);
		System.out.println(itemDesc2.toString());
	}
	
	
	@Test
	public void testList() throws JsonProcessingException {
		//1.创建工具API对象
		ObjectMapper objectMapper = new ObjectMapper();
		List<ItemDesc> list = new ArrayList<ItemDesc>();
		ItemDesc itemDesc1 = new ItemDesc();
		itemDesc1.setItemId(100L);
		ItemDesc itemDesc2 = new ItemDesc();
		itemDesc2.setItemId(200L);
		list.add(itemDesc1);
		list.add(itemDesc2);
		
		//测试list集合转化为JSON
		String json = objectMapper.writeValueAsString(list);
		System.out.println(json);
		
		//测试json结构,能否转化为List集合
		List<ItemDesc> list2 = objectMapper.readValue(json, list.getClass());
		System.out.println(list2);
	}
	
	
	@Test
	public void test3(){
		//1.创建工具API对象
		ItemDesc itemDesc1 = new ItemDesc();
		itemDesc1.setItemId(100L);
		String json = ObjectMapperUtil.toJSON(itemDesc1);
		System.out.println(json);
	}
	
	
	
	
	
	
	
	
}
