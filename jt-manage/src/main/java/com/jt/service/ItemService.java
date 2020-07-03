package com.jt.service;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUITable;

public interface ItemService {

	EasyUITable findItemByPage(Integer page, Integer rows);

	void saveItem(Item item);

	void itemUpdate(Item item, ItemDesc itemDesc);

	void itemDeletes(Long[] ids);

	void updateStatus(Integer status, Long[] ids);

	void saveItem(Item item, ItemDesc itemDesc);

	ItemDesc findItemDescById(Long itemId);
	
}
