package com.jt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.pojo.Item;

public interface ItemMapper extends BaseMapper<Item>{
	
	@Select("SELECT * FROM tb_item order by updated desc LIMIT #{start},#{rows} ")
	List<Item> findItemByPage(Integer start, Integer rows);
	
}
