package com.jt.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 如果后端服务器需要进行跨域的访问,则需要开启跨域的配置
 * @author LYJ
 *
 */
@Configuration	//标识配置类
public class WebMVCConfig implements WebMvcConfigurer{
	//sso.jt.com
	
	//新增跨域访问
    @Override
    public void addCorsMappings(CorsRegistry registry) {
    	registry.addMapping("/**") //请求拦截的路径 所有的请求路径
                //.allowedOrigins("http://www.jt.com")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "DELETE", "PUT", "OPTIONS","HEAD")   //请求类型
                .allowCredentials(true) //是否允许携带cookie
                .maxAge(3600);          //校验请求的有效期
    			//跨域刚开始时需要试探性的访问一次   是否允许跨域!!!
    }
	
}
