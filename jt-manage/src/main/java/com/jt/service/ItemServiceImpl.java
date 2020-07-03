package com.jt.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jt.mapper.ItemDescMapper;
import com.jt.mapper.ItemMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUITable;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private ItemDescMapper itemDescMapper;

	/**
	 *编辑代码技巧: 记忆部分关键字,之后根据方法提示,完成任务.
	 * 说明:利用MP的方式实现分页操作
	 * 
	 */
	@Override
	public EasyUITable findItemByPage(Integer current, Integer size) {
		
		//1.定义Page 简单分页对象
		Page<Item> page = new Page<>(current, size);
		//2.按照updated 降序排列
		OrderItem orderItem = new OrderItem();
		orderItem.setColumn("updated")
				 .setAsc(false);
		page.addOrder(orderItem);
		//3.执行分页查询操作
		IPage<Item> iPage = itemMapper.selectPage(page,null);
		//4.封装返回值	long-->int
		return new EasyUITable((int)iPage.getTotal(), iPage.getRecords());
	}

	@Override
	@Transactional	//控制数据库事务  sql插入数据库 代码报错了!!   同时成功/失败
	public void saveItem(Item item) {
		
		item.setCreated(new Date())
			.setUpdated(item.getCreated()); //保证用户入库时间一致
		itemMapper.insert(item);
	}

	//MP更新操作时,如果使用byId的方式操作,则where id = #{xxxxx},根据主键更新.
	//其余的参数都会作为更新的数据进行操作.
	@Override
	@Transactional
	public void itemUpdate(Item item,ItemDesc itemDesc) {
		
		item.setUpdated(new Date());
		itemMapper.updateById(item);
		
		itemDesc.setItemId(item.getId())
				.setUpdated(item.getUpdated());
		itemDescMapper.updateById(itemDesc);
		
	}

	
	//关联删除
	@Transactional
	@Override
	public void itemDeletes(Long[] ids) {
		//Long[] ids  数组不是集合
		List<Long> idList = Arrays.asList(ids);
		itemMapper.deleteBatchIds(idList);
		itemDescMapper.deleteBatchIds(idList);
	}
	
	/**
	 * 批量的数据修改
	 * entity: 修改数据的值
	 * updateWrapper: 修改的条件构造器
	 * sql: update tb_item set status=1,updated=#{date}
	 * 		where id in (101,102,103);
	 */
	@Override
	public void updateStatus(Integer status, Long[] ids) {
		Item item = new Item();
		item.setStatus(status)
			.setUpdated(new Date());
		//拼接修改条件
		UpdateWrapper<Item> updateWrapper = new UpdateWrapper<>();
		List<Long> idList = Arrays.asList(ids);
		updateWrapper.in("id", idList);
		itemMapper.update(item, updateWrapper);
	}

	/**
	 * 现在需要同时入库2张表数据
	 * 1.item    2.itemDesc
	 * 
	 */
	@Override
	@Transactional
	public void saveItem(Item item, ItemDesc itemDesc) {
		item.setStatus(1)
			.setCreated(new Date())
			.setUpdated(item.getCreated()); //保证用户入库时间一致
		itemMapper.insert(item);		//只有item对象入库之后,才会有主键值
		
		//思路:能否将入库操作完成之后,自动的实现数据的回显!!!!
		//MP中的新增操作的会自动的实现数据的回显.
		//如果不用MP则应该如何实现回显??
		itemDesc.setItemId(item.getId());		//添加主键信息
		itemDesc.setCreated(item.getCreated())	//保证时间一致
				.setUpdated(item.getCreated());
		itemDescMapper.insert(itemDesc);
	}

	@Override
	public ItemDesc findItemDescById(Long itemId) {
		
		return itemDescMapper.selectById(itemId);
	}
	
	
	
	
	
	
	
	/**
	 * 1.自己手写sql
	 * 2.利用MP方式进行分页
	 * 
	 * arg1:数据的起始位置
	 * arg2:查询的记录数
	 * 规定:每页20条
	 * 分页介绍:
	 * 		SELECT * FROM tb_item LIMIT arg1,arg2
	 * 第一页:  
	 * 		SELECT * FROM tb_item LIMIT 0,20      含头不含尾  0-19的下标
	 * 第二页:  
	 * 		SELECT * FROM tb_item LIMIT 20,20     下标20-39
	 * 第三页:  
	 * 		SELECT * FROM tb_item LIMIT 40,20	下标40-59
	 * 第N页:  
	 * 		SELECT * FROM tb_item LIMIT (page-1)rows,rows
	 */
	/*@Override
	public EasyUITable findItemByPage(Integer page, Integer rows) {
		
		//1.分页查询
		int start = (page-1)*rows;	//定义起始位置
		List<Item> itemList = itemMapper.findItemByPage(start,rows);
		//2.获取记录数
		int total = itemMapper.selectCount(null);
		
		return new EasyUITable(total, itemList);
	}*/
	
}
