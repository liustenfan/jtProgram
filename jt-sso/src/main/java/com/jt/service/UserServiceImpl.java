package com.jt.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public List<User> findAll() {
		
		return userMapper.selectList(null);
	}

	/**
	 * type=1/2/3    1 username、2 phone、3 email  将123转化为数据库字段
	 * 1.如何查询数据库!!!
	 * 2.判断依据?    根据用户信息查询总记录数 >0 已存在    ==0 可以使用
	 * 3.返回值结果与业务关系   true 已存在   false 可以使用
	 */
	@Override
	public boolean checkUser(String param, Integer type) {
		//1.将type转化为具体的字段信息
		Map<Integer,String> map = new HashMap<>();
		map.put(1, "username");
		map.put(2, "phone");
		map.put(3, "email");
		//2.根据用户的类型获取字段信息
		String column = map.get(type);
		QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
		queryWrapper.eq(column, param);
		Integer count = userMapper.selectCount(queryWrapper);
		//3.根据结果返回数据
		return count>0?true:false;
	}
}
