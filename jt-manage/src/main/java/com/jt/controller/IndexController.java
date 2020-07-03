package com.jt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	
	/**
	 * 问题分析:
	 * 	1.		/page/item-add
	 *  2.      /page/item-list
	 *  3.		/page/item-param-list
	 * 
	 * 	特点1: 请求的前缀都相同.
	 *  特点2: 请求路径信息与页面逻辑名称一致
	 *  
	 *  通用页面跳转的实现思路: 如何获取url中的参数???
	 *  
	 *  解决方案: 利用RESTFul方式动态获取数据
	 *  语法: 
	 *  	1.将需要接收的参数使用{}形式包裹
	 *  	2.将需要接收的参数使用/方式分割
	 *  	3.在方法中标识参数,并且配合注解动态获取
	 *  
	 *  总结: 如果需要获取url中的参数时,则使用restFul方式动态获取数据.
	 * @return
	 */
	//1.实现页面跳转   /page/item-list  //商品新增页面
	@RequestMapping("/page/{moduleName}")	
	public String itemAdd(@PathVariable String moduleName) {
		
		//经过springmvc的视图解析器,拼接前缀和后缀
		return moduleName;
	}
	
}
