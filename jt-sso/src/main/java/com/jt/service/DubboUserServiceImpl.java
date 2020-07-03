package com.jt.service;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;

import redis.clients.jedis.JedisCluster;

@Service
public class DubboUserServiceImpl implements DubboUserService {
	
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private JedisCluster jedisCluster;
	
	@Transactional	//事物控制
	@Override
	public void saveUser(User user) {
		
		//1.密码加密
		String md5Pass = 
				DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5Pass)
			.setEmail(user.getPhone())	//电话号码填充
			.setCreated(new Date())
			.setUpdated(user.getCreated()); //保证时间一致!!!
		userMapper.insert(user);
	}

	
	/**
	 * 完成用户的登陆操作
	 * 1.查询用户数据,校验是否有效
	 * 2.将数据保存到redis中,之后返回ticket信息
	 */
	
	@Override
	public String doLogin(User user) {
		
		//1.根据user信息查询数据库记录.   根据其中不为null的属性充当where条件
		String md5Pass = 
				DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5Pass);
		QueryWrapper<User> queryWrapper = new QueryWrapper<User>(user);
		User userDB = userMapper.selectOne(queryWrapper);
		
		//2.判断数据是否有值
		if(userDB == null) { //用户名和密码错误.
			return null;
		}
		
		//3.开启单点登陆操作
		String ticket = UUID.randomUUID().toString();
		//将数据进行脱敏处理. 将一些重要的数据屏蔽.
		userDB.setPassword("你猜猜!!!");
		String userJSON = ObjectMapperUtil.toJSON(userDB);
		
		//4.将数据保存到redis中
		jedisCluster.setex(ticket, 7*24*60*60, userJSON);
		return ticket;
	}
}
