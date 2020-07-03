package com.jt.config;

import java.util.HashSet;
import java.util.Set;

import javax.print.attribute.HashAttributeSet;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

//代表早期的配置文件
@Configuration
@PropertySource("classpath:/properties/redis.properties")
public class RedisConfig {
	
	@Value("${redis.nodes}")
	private String nodes;  //node,node,node,node
	
	
	@Bean
	public JedisCluster jedisCluster() {
		Set<HostAndPort> set = new HashSet<>();
		String[] nodeArray = nodes.split(",");
		for (String node : nodeArray) {  //node=ip:port
			String host = node.split(":")[0];
			int port = Integer.parseInt(node.split(":")[1]);
			set.add(new HostAndPort(host, port));
		}
		return new JedisCluster(set);
	}
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
/*	@Value("${redis.host}")
	private String host;
	@Value("${redis.port}")
	private Integer port;
	
	//bean注解  将生成的jedis对象交给Spring容器管理
	@Bean
	public Jedis jedis() {
		
		return new Jedis(host,port);
	}*/
