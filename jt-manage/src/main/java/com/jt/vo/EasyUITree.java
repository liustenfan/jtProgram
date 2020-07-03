package com.jt.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
//树形结构的格式要求.
@Data	//get 获取属性值   set  为属性赋值
@Accessors(chain=true)
@NoArgsConstructor
@AllArgsConstructor
public class EasyUITree {
	
	private Long id;
	private String text;	//文本信息
	private String state;	//节点状态 open/closed
	
}
