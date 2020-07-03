package com.jt.vo;

import java.util.List;

import com.jt.pojo.Item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
@Data
@Accessors(chain=true)
@NoArgsConstructor
@AllArgsConstructor
public class EasyUITable {
	
	private Integer total;  	//1.定义总记录数
	private List<Item> rows;	//2.定义返回值数据
	
}
