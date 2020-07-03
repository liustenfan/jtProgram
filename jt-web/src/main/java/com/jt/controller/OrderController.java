package com.jt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.pojo.Order;
import com.jt.pojo.OrderItem;
import com.jt.service.DubboCartService;
import com.jt.service.DubboItemService;
import com.jt.service.DubboOrderService;
import com.jt.thread.UserThreadLocal;
import com.jt.vo.SysResult;

@Controller
@RequestMapping("/order")
public class OrderController {
	
	@Reference
	private DubboCartService cartService;
	@Reference
	private DubboOrderService orderService;
	
	
	/**
	 * 跳转到订单确认页面
	 * 1.url:http://www.jt.com/order/create.html
	 * 2.请求参数: 无
	 * 3.返回值结果: 订单页面逻辑名称
	 * 4.页面取值信息: ${carts}  获取购物车记录
	 */
	@RequestMapping("/create")
	public String create(Model model) {
		
		//1.获取用户id
		Long userId = UserThreadLocal.get().getId();
		List<Cart> carts = cartService.findCartList(userId);
		//2.封装页面数据信息
		model.addAttribute("carts", carts);
		
		return "order-cart";	//跳转指定的页面
	}
	
	
	/**
	 *  业务说明: 完成订单入库操作
	 *  1.url地址信息: http://www.jt.com/order/submit
	 *  2.参数: 利用Order对象进行接收
	 *  3.返回值结果: SysResult VO对象   需要返回orderId
	 *  
	 *  //3.减去库存
		/*List<OrderItem> orderItemList = order.getOrderItems();
		for (OrderItem orderItem : orderItemList) {
			String itemId = orderItem.getItemId();
			int num = orderItem.getNum();
			itemService.updateItemNum(itemId,num);
		}
		*/
	@RequestMapping("/submit")
	@ResponseBody
	public SysResult submit(Order order) {
		//1.获取userId信息
		Long userId = UserThreadLocal.get().getId();
		order.setUserId(userId);
		//2.完成订单入库操作
		String orderId = orderService.insertOrder(order);
		//3.返回数据
		return SysResult.success(orderId);
	}
	
	
	@RequestMapping("/success")
	public String findOrderById(String id,Model model) {
		
		Order order = orderService.findOrderById(id);
		model.addAttribute("order", order);
		return "success";
	}
	
	
	
	
}
