package com.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.mapper.ItemDescMapper;
import com.jt.pojo.ItemDesc;

@RestController
public class TestItemDescController {

	@Autowired
	private ItemDescMapper itemDescMapper;

	//ITEMDESC::0
	@RequestMapping("/findItemDesc")
	@Cacheable(cacheNames="ITEMDESC",key="#itemId")  //定义业务名称
	public ItemDesc findItemDescById(Long itemId) {

		System.out.println("查询数据库!!!!");
		return itemDescMapper.selectById(itemId);
	}

	//如果需要实现缓存更新,则必须将更新后的结果进行返回
	@RequestMapping("/update")
	@CachePut(cacheNames="ITEMDESC",key="#itemDesc.getItemId()")
	public ItemDesc update(ItemDesc itemDesc) {

		System.out.println("执行更新操作");
		itemDescMapper.updateById(itemDesc);
		return itemDescMapper.selectById(itemDesc.getItemId());
	}

	//如果需要实现缓存更新,则必须将更新后的结果进行返回
	@RequestMapping("/delete")
	@CacheEvict(cacheNames="ITEMDESC",key="#itemId")
	public String update(Long itemId) {

		System.out.println("删除数据");
		itemDescMapper.deleteById(itemId);
		return "删除成功!!!";
	}


}
