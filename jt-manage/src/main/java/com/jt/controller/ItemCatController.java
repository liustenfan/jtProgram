package com.jt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jt.pojo.ItemCat;
import com.jt.service.ItemCatService;
import com.jt.service.ItemService;
import com.jt.vo.EasyUITree;

//一般要求返回的数据都是JSON
@RestController
@RequestMapping("/item/cat")
public class ItemCatController {
	
	//编码规范: 1.POJO  2.mapper  3.service  4.controller
	
	@Autowired
	private ItemCatService itemCatService;
	/**
	 *  1. 业务需求: 根据商品分类ID值965,其实需要根据965的ID的值查询商品分类名称. 发起ajax请求获取商品名称.
 		2. 页面url请求地址: http://localhost:8091/item/cat/queryItemName
 		3. url请求参数:  itemCatId: 965
 		4. 返回值结果:   要求返回商品分类的名称
	 */
	@RequestMapping("/queryItemName")
	public String findItemCatNameById(Long itemCatId) {
		
		//1.根据id查询商品分类对象信息
		ItemCat itemCat = itemCatService.findItemCatById(itemCatId);
		//2.从对象中获取name属性
		return itemCat.getName();	
	}
	
	
	/**
	 * url:http://localhost:8091/item/cat/list 
	 *   参数: 当展现二三级信息时,会传递父级的Id信息,如果展现1级菜单,则应该设定默认值
	 *   返回值: List<EasyUITree>
	 */
	@RequestMapping("list")
	public List<EasyUITree> findItemCatList
	(@RequestParam(name="id",defaultValue="0")Long parentId){
		
		//1.查询一级商品分类信息,所以
		return itemCatService.findItemCatList(parentId); //数据库操作
		//return itemCatService.findItemCatCache(parentId);		   //缓存操作
	}
	
	
	@RequestMapping("getEasyUITree")
	public EasyUITree getEasyUITree(){
		
		return new EasyUITree(18L, "购物狂欢节", "101");
	}
	
	
	
	
	
	
}
