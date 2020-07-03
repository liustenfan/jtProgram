package com.jt.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;
import com.jt.vo.EasyUITable;
import com.jt.vo.SysResult;

//页面中要求返回的数据都是JSON
@RestController
@RequestMapping("/item")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	/**
	 * 1.url:http://localhost:8091/item/query?page=1&rows=50
	 * 2.参数: page=1 当前页   rows=50 每页的条数.
	 * 3.返回值: EasyUITable Vo对象
	 */
	@RequestMapping("/query")
	public EasyUITable  findItemByPage(Integer page,Integer rows) {
		
		return itemService.findItemByPage(page,rows);
	}
	
	
	/**
	 *  url地址: http://localhost:8091/item/save
	 *     参数: 整合form表单提交   item对象接收参数
	 *     返回值: 系统返回值  SysResult对象
	 *     
	 *  说明:商品信息由item/itemDesc2个对象一个完成新增
	 */
	@RequestMapping("/save")
	public SysResult saveItem(Item item,ItemDesc itemDesc) {
		
		itemService.saveItem(item,itemDesc);
		return SysResult.success(); //用户入库成功!!!!
	}
	
	
	/**
	 * 完成商品更新操作
	 * 1.url: http://localhost:8091/item/update
	 * 2.请求参数:  form表单     使用对象接收.
	 * 3.返回值结果: 系统返回值数据
	 */
	@RequestMapping("/update")
	public SysResult itemUpdate(Item item,ItemDesc itemDesc) {
		
		itemService.itemUpdate(item,itemDesc);
		return SysResult.success();
	}
	
	/**
	 * 完成商品删除操作
	 * 1.url: http://localhost:8091/item/delete
	 * 2.请求参数:  ids: 101,102,103   3个值
	 * 3.返回值结果: 系统返回值数据
	 * 
	 * sql:delete from tb_item where id=100 and id=101 and id=102
	 * 
	 * String[] strIds = ids.split(",");
		Long[] longIds = {};
		for(int i=0;i<strIds.length;i++){
			
			longIds[i] = Long.parseLong(strIds[i]);
		}
		
		说明:根据用户提交的参数,可以自动的实现数据类型的转化.
		规则: 字符串使用","号进行分割,则利用[]形式转化数据.
	 */
	@RequestMapping("/delete")
	public SysResult itemDelete(Long[] ids) {
		
		//1.需要将字符串转化为字符串数组
		itemService.itemDeletes(ids);
		return SysResult.success();
	}
	
	
	/**
	 * 旧请求:http://localhost:8091/item/instock
	 * 		  http://localhost:8091/item/reself
	 * 修改的参数  update tb_item set status = 1/2
	 * 
	 * 修改后的请求:http://localhost:8091/item/1
	 * 		       http://localhost:8091/item/2
	 * 
	 * 
	 * url分析:http://localhost:8091/item/1
	 * 参数:  ids=101,102,103
	 * 返回值: SysResult对象
	 */
	@RequestMapping("/{status}")
	public SysResult updateStatus(@PathVariable Integer status,Long[] ids) {
		
		itemService.updateStatus(status,ids);
		return SysResult.success();
	}
	
	
	/**
	 * 1.url:http://localhost:8091/item/query/item/desc/1474392215
	 * 2.请求参数: 参数位于url中
	 * 3.返回值类型: SysResult对象
	 */
	@RequestMapping("/query/item/desc/{itemId}")
	public SysResult findItemDescById(@PathVariable Long itemId) {
		
		//1.根据id.查询商品详情信息
		ItemDesc itemDesc = itemService.findItemDescById(itemId);
		return SysResult.success(itemDesc);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
