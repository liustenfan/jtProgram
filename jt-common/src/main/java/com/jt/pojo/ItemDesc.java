package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@TableName("tb_item_desc")
@Data	//toString方法只会包含当前属性数据
@Accessors(chain=true)
@NoArgsConstructor
@AllArgsConstructor
public class ItemDesc extends BasePojo{
	@TableId					//只设定主键,不设置自增.
	private Long itemId;		//商品id号,id应该与商品表ID一致
	private String itemDesc;	//商品详情信息

}
