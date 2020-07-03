package com.jt.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.OrderItemMapper;
import com.jt.mapper.OrderMapper;
import com.jt.mapper.OrderShippingMapper;
import com.jt.pojo.Order;
import com.jt.pojo.OrderItem;
import com.jt.pojo.OrderShipping;

@Service
public class OrderServiceImpl implements DubboOrderService {
	
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private OrderShippingMapper orderShippingMapper;
	@Autowired
	private OrderItemMapper orderItemMapper;
	
	
	/**
	 * order:  1.order模块信息    2.orderItem    3.订单物流信息
	 * 应该实现三张表同时入库.
	 */
	@Transactional //控制事务
	@Override
	public String insertOrder(Order order) {
		//1.生成orderID
		String orderId = ""+order.getUserId() + System.currentTimeMillis();
		
		//2.定义入库时间
		Date date = new Date(); //如果项目中有时间获取的工具API,则使用该API,如果没有才使用new date();
		
		//3.实现订单入库
		order.setOrderId(orderId).setStatus(1)
			 .setCreated(date).setUpdated(date);
		orderMapper.insert(order);
		
		//4.完成订单物流入库
		OrderShipping shipping = order.getOrderShipping();
		shipping.setOrderId(orderId);
		shipping.setCreated(date);
		shipping.setUpdated(date);
		orderShippingMapper.insert(shipping);
		
		//5.完成订单商品入库
		List<OrderItem> orderItems = order.getOrderItems();
		for (OrderItem orderItem : orderItems) {
			orderItem.setOrderId(orderId);
			orderItem.setCreated(date);
			orderItem.setUpdated(date);
			orderItemMapper.insert(orderItem);
		}
		
		System.out.println("订单入库成功!!!!!");
		return orderId;
	}

	
	//1.order订单信息   2.orderItems   3.orderShipping
	@Override
	public Order findOrderById(String id) {
		
		Order order = orderMapper.selectById(id);
		OrderShipping shipping = orderShippingMapper.selectById(id);
		QueryWrapper<OrderItem> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("order_id", id);
		List<OrderItem> list = orderItemMapper.selectList(queryWrapper);
		order.setOrderItems(list).setOrderShipping(shipping);
		return order;
	}
	
	
}
