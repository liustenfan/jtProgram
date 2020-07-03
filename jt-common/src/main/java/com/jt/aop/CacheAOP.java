package com.jt.aop;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jt.anno.CacheFind;
import com.jt.util.ObjectMapperUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

@Component	//将该类交给Spring容器管理
@Aspect		//标识我是一个切面
public class CacheAOP {

	@Autowired
	private JedisCluster jedis;
	//private Jedis jedis;

	/**
	 * 根据@CacheFind注解,实现缓存控制
	 * 
	 * 1.切入点表达式    "@annotation(cacheFind)"  只拦截cacheFind注解
	 * 2.通知方法如何控制
	 * 
	 * 缓存实现的策略:
	 * 	1.拼接key     用户输入的内容 + 动态参数
	 */

	@SuppressWarnings("unchecked")
	@Around("@annotation(cacheFind)")
	public Object cacheAround(ProceedingJoinPoint joinPoint,CacheFind cacheFind) {
		String key = cacheFind.key();
		String strArg0 = joinPoint.getArgs()[0].toString();	//获取其中的第一个参数
		key = key +"::" + strArg0;
		Object result = null;
		//1.判断redis中是否有该记录  没有 需要查询数据  有 直接返回数据
		try {
			if(jedis.exists(key)) {	//redis中有结果
				
				String json = jedis.get(key);	//获取redis中的记录.
				MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
				Class returnClass =methodSignature.getReturnType();
				//动态的获取目标方法的返回值类型.
				result = ObjectMapperUtil.toObj(json, returnClass);
				System.out.println("查询AOP缓存");
			}else { //表示redis中没有记录
				result = joinPoint.proceed();	//查询数据库
				String json = ObjectMapperUtil.toJSON(result);
				int seconds = cacheFind.seconds();
				System.out.println("AOP执行数据库查询");
				if(seconds>0)
					jedis.setex(key, seconds, json); //添加超时时间
				else 
					jedis.set(key, json);			 //不需要超时时间
			}
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return result;
	}















	//切面  = 切入点表达式 + 通知方法

	//可以理解为 就是一个if判断
	@Pointcut("bean(itemCatServiceImpl)")   //只对特定的某个类有效
	//@Pointcut("within(com.jt.*.ItemCatServiceImpl)")
	//拦截com.jt.service下边的所有类的所有方法并且所有的参数
	//@Pointcut("execution(* com.jt.service..*.*(..))")
	public void joinPoint() {

	}


	/**
	 * 记录程序的执行状态  获取哪个类,哪个方法执行的
	 */
	//@Before("joinPoint()")
	public void before(JoinPoint joinPoint) {
		Date date = new Date();
		String strDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		String typeName = joinPoint.getSignature().getDeclaringTypeName();
		String methodName = joinPoint.getSignature().getName();
		System.out.println("我是前置通知");
		System.out.println(strDate+":"+typeName+":"+methodName);
	}


	/*定义环绕通知  控制程序的执行过程*/
	//@Around("joinPoint()")
	public Object around(ProceedingJoinPoint joinPoint) {
		System.out.println("我是环绕通知开始");
		Object result = null;
		try {
			result = joinPoint.proceed();	//执行真实的目标方法
		} catch (Throwable e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}

		System.out.println("我是环绕通知结束");
		return result;
	}






	/**
	 * 通知类型:
	 * 		1.前置通知:  在目标方法执行之前执行
	 * 		2.后置通知:	在目标方法执行之后执行	
	 * 		3.异常通知:  在目标方法执行之后发生异常时执行
	 * 		4.最终通知:  在程序执行最后执行的通知方法
	 * 		5.环绕通知:  在目标方法执行前后都要执行的通知方法.
	 * 
	 * 记忆:
	 * 		1.如果要对程序的执行的流程进行控制,则首选环绕通知.  最重要的通知方法
	 * 		2.如果需要对程序的执行的状态进行记录,则使用其他四大通知类型.
	 * 
	 * 
	 * 切入点表达式:
	 * 		bean= Spring容器管理的对象称之为bean
	 * 		1. bean(bean的ID)      只能拦截某个bean的操作.执行通知方法  1个
	 * 		2. within(包名.类名)    按类匹配,类可以有多个
	 * 		//上述的切入点表达式控制的粒度较粗   只能控制到类级别.
	 * 
	 * 		//可以控制到方法参数级别
	 * 		3. execution(返回值类型  包名.类名.方法名(参数列表))  控制粒度较细
	 * 		4. annotation(包名.注解名)  按照指定的注解进行匹配.
	 * 
	 */
}
