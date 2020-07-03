package com.jt;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class TestCluster {
	
	/**
	 * 测试redis集群
	 */
	@Test
	public void test01() {
		//2.准备redis节点信息
		Set<HostAndPort>  nodes = new HashSet<>();
		nodes.add(new HostAndPort("192.168.126.129", 7000));
		nodes.add(new HostAndPort("192.168.126.129", 7001));
		nodes.add(new HostAndPort("192.168.126.129", 7002));
		nodes.add(new HostAndPort("192.168.126.129", 7003));
		nodes.add(new HostAndPort("192.168.126.129", 7004));
		nodes.add(new HostAndPort("192.168.126.129", 7005));
		
		//1.实例化工具API
		JedisCluster  jedisCluster = new JedisCluster(nodes); 		
		jedisCluster.set("www", "redis集群测试");
		//www的key到底保存到了哪台主机中呢???
		System.out.println(jedisCluster.get("abc"));
	}
}
