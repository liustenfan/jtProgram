package com.jt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.jt.inter.UserInterceptor;

@Configuration				          //web.xml配置文件
public class MvcConfigurer implements WebMvcConfigurer{
	
	//开启匹配后缀型配置
	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		
		configurer.setUseSuffixPatternMatch(true);
	}
	
	
	@Autowired
	private UserInterceptor userInterceptor;
	
	//配置拦截器  registry拦截器的注册中心
	// /cart/**  拦截购物下的所有的请求
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(userInterceptor)
				.addPathPatterns("/cart/**","/order/**");
	}
}
