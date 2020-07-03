package com.jt.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.anno.CacheFind;
import com.jt.mapper.ItemCatMapper;
import com.jt.pojo.ItemCat;
import com.jt.util.ObjectMapperUtil;
import com.jt.vo.EasyUITree;

import redis.clients.jedis.Jedis;

@Service	//该类会被spring容器管理 Map<类名首字母小写,类的对象>
public class ItemCatServiceImpl implements ItemCatService {
	
	@Autowired
	private ItemCatMapper itemCatMapper;
	
	//该配置为AOP提供理论基础
	//@Autowired
	private Jedis jedis;

	//根据Id查询商品分类信息
	@Override
	public ItemCat findItemCatById(Long itemCatId) {
		
		return itemCatMapper.selectById(itemCatId);
	}

	//目的为了简化代码的耦合性.每个方法都应该完成自己独立的任务
	public List<ItemCat> findItemCatListByParentId(Long parentId){
		
		QueryWrapper<ItemCat> queryWrapper = new QueryWrapper<ItemCat>();
		queryWrapper.eq("parent_id", parentId);
		return itemCatMapper.selectList(queryWrapper);
	}
	
	@CacheFind(key="ITEM_CAT_PARENTID")
	@Override
	public List<EasyUITree> findItemCatList(Long parentId) {
		//1.先获取所有的一级商品分类信息.
		List<ItemCat> itemCatList = findItemCatListByParentId(parentId);
		//2.将CartList转化为VOlist
		List<EasyUITree> treeList = new ArrayList<>(itemCatList.size());
		for (ItemCat itemCat : itemCatList) {
			Long id = itemCat.getId();
			String text = itemCat.getName();
			//如果是父级则默认关闭,如果是子级则默认打开.
			String state = itemCat.getIsParent()?"closed":"open";
			EasyUITree easyUITree = new EasyUITree(id, text, state);
			treeList.add(easyUITree);
		}
		return treeList;
	}

	/**
	 * 思路:
	 * 	1.定义查询redis的key, key要求唯一的
	 *  2.第一次查询先查询redis.
	 *  	没有数据: 表示缓存中没有数据, 查询数据库,之后将数据保存到redis中
	 *  	有数据:   证明缓存中有值, 直接返回给用户即可.
	 *  
	 *  耦合性高,代码不便于维护
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<EasyUITree> findItemCatCache(Long parentId) {
		String key = "ITEM_CAT_PARENTID_"+parentId;
		List<EasyUITree> treeList = new ArrayList<>();
		//1.判断redis中是否有记录
		if(jedis.exists(key)) {
			//表示redis中有记录.
			String json = jedis.get(key);
			treeList = 
					ObjectMapperUtil.toObj(json, treeList.getClass());
			System.out.println("实现redis缓存查询");
		}else {
			//redis中没有记录,需要先查询数据库.
			treeList = findItemCatList(parentId);
			//将数据库记录转化为json之后保存到redis中
			String json = ObjectMapperUtil.toJSON(treeList);
			jedis.set(key, json);
			System.out.println("第一次查询数据库!!!!!");
		}
		
		return treeList;
	}

	
	
	
	
}
