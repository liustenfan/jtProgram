package com.jt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties.Jedis;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.User;
import com.jt.service.UserService;
import com.jt.util.ObjectMapperUtil;
import com.jt.vo.SysResult;

import redis.clients.jedis.JedisCluster;

//是否需要跳转页面   true:@Controller   false:@RestController
@RestController
//@CrossOrigin(origins="http://www.jt.com") //只对当前controller有效
//@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private JedisCluster jedisCluster;
	
	
	/**
	 * 目的: 校验用户信息是否存在
	 * url地址: http://sso.jt.com/user/check/{param}/{type}
	 * 参数:    param    type参数类型
	 * 返回值: SysResult对象
	 * 标识符:   true 表示用户已存在
	 * 			false 表示用户不存在 可以使用
	 * 
	 * JSONP
	 * 		1.回调函数名称
	 * 		2.特殊格式处理
	 */
	@RequestMapping("/user/check/{param}/{type}")
	public JSONPObject checkUser(@PathVariable String param,
							   @PathVariable int type,
							   String callback) {
		
		//根据用户传递的参数,判断数据库中是否有数据.
		boolean flag = userService.checkUser(param,type);
		SysResult sysResult = SysResult.success(flag);
		return new JSONPObject(callback, sysResult);
	}
	
	
	/**
	 * 业务接口说明
	 * 1.url网址: http://sso.jt.com/user/query/{ticket}?callback=jsonp1593
	 * 2.参数: ticket信息
	 * 3.返回值结果: SysResult对象(userJSON)信息
	 * 
	 * 业务逻辑说明: 根据ticket信息,查询redis获取userJSON信息
	 */
	@RequestMapping("/user/query/{ticket}")
	public JSONPObject findUserByTicket(@PathVariable String ticket,
			String callback) {
		
		if(jedisCluster.exists(ticket)) {
			String userJSON = jedisCluster.get(ticket);
			//表示用户已经登陆
			return new JSONPObject(callback,SysResult.success(userJSON));
		}else {
			//根据ticket查询redis中并没有该记录.
			return new JSONPObject(callback,SysResult.fail());
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	//测试方法 ,查询所有的用户信息 
	/*@RequestMapping("/findAll")
	public List<User> findAll(){
		
		return userService.findAll();
	}*/
	
	/*
	 * 利用跨域的形式获取user数据
	 *
	 **/
	/*@RequestMapping("/findAll")
	public String findAll(String callback) {
		
		 List<User> userList = userService.findAll();
		 String json = ObjectMapperUtil.toJSON(userList);
		 return callback+"("+json+")";
	}*/
	
	/**
	 * JSONP 高级用法
	 * @param callback
	 * @return
	 */
	@RequestMapping("/findAll")
	public JSONPObject findAll(String callback) {
		 
		 //业务数据是什么
		 List<User> userList = userService.findAll();
		 return new JSONPObject(callback, userList);
		 //返回之后,可以将jsonpObject对象自动的封装为指定的格式
	}
	
	
	
	@RequestMapping("/findCors")
	public List<User> findCors() {
		 
		return userService.findAll();
	}
	
}
