package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
@NoArgsConstructor
@AllArgsConstructor
public class SysResult {
	
	private Integer status;		//200 表示成功 201表示失败. 
	private String  msg;		//服务器返回客户端消息
	private Object  data;		//服务器返回客户端数据
	
	public static SysResult fail() {
		
		return new SysResult(201,"业务执行失败", null);
	}
	
	public static SysResult success() {
		
		return new SysResult(200,"业务执行成功", null);
	}
	
	public static SysResult success(Object data) {
		
		return new SysResult(200,"业务执行成功", data);
	}
	
	public static SysResult success(String msg,Object data) {
		
		return new SysResult(200, msg, data);
	}
}
