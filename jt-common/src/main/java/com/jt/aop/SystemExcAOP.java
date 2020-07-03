package com.jt.aop;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jt.vo.SysResult;

//1.标识该类是一个全局异常处理机制
@RestControllerAdvice	//通知
public class SystemExcAOP {

	//该异常处理机制,只对controller抛出的异常有效.
	//爸爸(亿万富翁)  ----自己(富二代)--------儿子(富三代)  亿解决
	
	//只有程序抛出运行时异常时,才会被全局异常处理机制捕获.
	//专业的开发人员:职业操守   自己必须处理的异常称之为检查异常.
	//如果自己不想处理,则将检查异常转化为运行时异常即可.
	@ExceptionHandler(RuntimeException.class)
	public SysResult systeResult(Exception exception) {
		exception.printStackTrace();  //打印报错信息
		return SysResult.fail();
	}
}
