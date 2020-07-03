package com.jt;

import java.time.Duration;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.jt.pojo.ItemDesc;
import com.jt.pojo.User;

@SpringBootTest
public class TestRedisTemplate {

	//1.专门操作字符串
	@Autowired
	private StringRedisTemplate strTemplate;	//多
	//2.操作任意对象
	@Autowired									
	private RedisTemplate<String,Object> redisTemplate;

	//测试入门案例  
	@Test
	public void test01() {

		strTemplate.opsForValue();	//操作String数据类型
		strTemplate.opsForHash();	//操作hash数据类型
		strTemplate.opsForList();	//操作list数据类型
		strTemplate.opsForSet();	//操作set数据类型
		strTemplate.opsForZSet();	//操作zSet数据类型
	}

	//操作字符串类型 
	@Test
	public void test02() {

		strTemplate.opsForValue().set("aa", "aaa");
		String value = strTemplate.opsForValue().get("aa");
		System.out.println(value);
		//为数据添加超时时间
		strTemplate.opsForValue().set("aaaa","bbbb", Duration.ofDays(1));
		//setNX   如果key不存在时才赋值.
		strTemplate.opsForValue().setIfAbsent("bb", "bbb");
	}


	//操作对象
	@Test
	public void test03() {
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(101L).setItemDesc("AAAA")
				.setCreated(new Date())
				.setUpdated(itemDesc.getCreated());
		
		redisTemplate.opsForValue().set("itemDesc", itemDesc);
		ItemDesc itemDesc2 = (ItemDesc) redisTemplate.opsForValue().get("itemDesc");
		System.out.println(itemDesc2);
	}

}
