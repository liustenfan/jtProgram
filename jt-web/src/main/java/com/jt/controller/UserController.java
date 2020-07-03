package com.jt.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.User;
import com.jt.service.DubboUserService;
import com.jt.vo.SysResult;

import redis.clients.jedis.JedisCluster;

@Controller			//只能返回页面逻辑名称
@RequestMapping("/user")
public class UserController {
	
	//引入dubbo接口
	@Reference(check=false)    //启动消费者时,暂时不检查是否有服务提供者.
	private DubboUserService userService;
	@Autowired
	private JedisCluster jedisCluster;
	private static final String TICKET = "JT_TICKET";
	
	
	/**
	 * 实现页面跳转
	 * 1.http://www.jt.com/user/login.html
	 * 		                要求跳转的页面是 login.jsp
	 * 3.http://www.jt.com/user/register.html
	 * 				要求跳转的页面是 register.jsp
	 * 	
	 * 
	 * 业务说明:一般利用springMVC进行请求拦截时,不会拦截后缀.
	 * 要求: 用户通过www.jt.com/user/login的方式同样可以实现页面跳转.
	 *核心: 告知springMVC框架,要求拦截后缀.
	 */
	
	@RequestMapping("/{moduleName}")
	public String login(@PathVariable String moduleName) {
		
		return moduleName;
	}
	
	
	/**
	 * 用户数据注册
	 * url:/user/doRegister
	 * 参数:{password:_password,username:_username,phone:_phone}
	 * 返回值:SysResult对象 的json数据
	 */
	@RequestMapping("/doRegister")
	@ResponseBody	//手动转化为json
	public SysResult saveUser(User user) {
		
		userService.saveUser(user);
		return SysResult.success();
	}
	
	
	/**
	 * 业务要求:
	 * 1.http://www.jt.com/user/doLogin?r=0.9169833675441843 避免浏览器缓存
	 * 2.参数信息:{username:_username,password:_password},
	 * 3.返回值结果: SysResult对象的返回
	 */
	
	@RequestMapping("/doLogin")
	@ResponseBody
	public SysResult doLogin(User user,HttpServletResponse response) {
		
		//1.进行用户登陆操作,获取返回值结果ticket
		String ticket = userService.doLogin(user);
		
		//2.判断业务数据是否有效
		if(StringUtils.isEmpty(ticket)) {
			//如果参数为null,则表示用户登陆失败.
			return SysResult.fail();
		}
		
		//3.将获取的ticket密钥信息保存到cookie中. 7天有效 可以实现数据共享.
		Cookie cookie = new Cookie(TICKET, ticket);
		cookie.setDomain("jt.com");
		cookie.setPath("/");
		cookie.setMaxAge(7*24*60*60);
		response.addCookie(cookie);
		
		return SysResult.success();
	}
	
	
	/**
	 * 实现用户的退出操作
	 * url地址:/user/logout.html
	 * 返回值结果:  重定向到系统首页
	 * 业务功能: 
	 * 	 1.删除cookie  设定最大的超时时间0
	 * 	 2.删除redis	      根据ticket信息删除redis
	 * 
	 * 知识补充: 通过request对象获取的cookie信息只有name/value 其他的信息
	 * request对象无权获取.
	 */
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request,HttpServletResponse response) {
		//1.首先获取JT_TICKET的cookie信息
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if(TICKET.equals(cookie.getName())) {
				//删除redis
				String ticket = cookie.getValue();
				jedisCluster.del(ticket);
				
				//删除cookie
				cookie.setDomain("jt.com");
				cookie.setPath("/");
				cookie.setMaxAge(0);	//控制cookie有效期.
				response.addCookie(cookie);
				break;	//跳出循环
			}
		}
		return "redirect:/";  //重定向到系统的根目录
	}
	
}
