package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

//该POJO对象是商品分类信息
@TableName("tb_item_cat")	//标识对象与表关系
@Data
@Accessors(chain=true)
//@NoArgsConstructor
//@AllArgsConstructor
public class ItemCat extends BasePojo{
	
	@TableId(type=IdType.AUTO)
	private Long id;			//主键标识
	private Long parentId;		//定义父级分类id
	private String name;		//商品分类名称
	private Integer status;		//商品分类状态
	private Integer sortOrder;	//商品分类排序号
	private Boolean isParent;	//是否为父级.
	
}
