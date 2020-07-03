package com.jt.thread;

import com.jt.pojo.User;

public class UserThreadLocal {
	
	//定义ThreadLocal对象    在线程内实现数据的共享
	private static ThreadLocal<User> threadLocal = new ThreadLocal<>();
	
	//1.保存数据的方法
	public static void set(User user) {
		
		threadLocal.set(user); //保存到了线程中
	}
	
	
	//2.获取数据的方法
	public static User get() {
		
		return threadLocal.get();
	}
	
	//3.清除数据的方法
	public static void remove() {
		
		threadLocal.remove();
	}
	
	
	
}	
