package com.jt.quartz;



import java.util.Calendar;
import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.OrderMapper;
import com.jt.pojo.Order;

//准备订单定时任务
@Component
public class OrderQuartz extends QuartzJobBean{
	
	@Autowired
	private OrderMapper orderMapper;
	
	/**
	 * 当规定的执行时间一到,触发器就会开启线程,执行指定的任务.
	 * 业务需求:
	 * 		要求将超时订单关闭. 要求30分钟   status 由1改为6
	 * 如何定义超时:
	 * 		now() - created  > 30分钟  订单超时
	 * 		created < now -30
	 * Sql:
	 * 		update tb_order set status = 6,updated = #{date}
	 * 		where created < (now -30) and status = 1;
	 * 
	 */
	@Override
	@Transactional
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		//对时间进行计算
		Calendar calendar = Calendar.getInstance();	//实例化对象 获取当前时间
		calendar.add(Calendar.MINUTE, -30);
		Date timeOut = calendar.getTime();
		
		Order entity = new Order();
		entity.setStatus(6).setUpdated(new Date());
		UpdateWrapper<Order> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("status", 1)
					 .lt("created",timeOut);
		orderMapper.update(entity, updateWrapper);
		System.out.println("定时任务执行成功!!!!");
	}
}
