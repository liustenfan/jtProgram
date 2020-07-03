package com.jt.conf;


import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jt.quartz.OrderQuartz;


@Configuration
public class OrderQuartzConfig {
	
	//定义任务详情
	@Bean
	public JobDetail orderjobDetail() {
		//指定job的名称和持久化保存任务
		return JobBuilder
				.newJob(OrderQuartz.class)	//引入自定义的任务
				.withIdentity("orderQuartz")//指定任务名称
				.storeDurably()
				.build();
	}
	//定义触发器
	@Bean
	public Trigger orderTrigger() {
		/*SimpleScheduleBuilder builder = SimpleScheduleBuilder.simpleSchedule()
				.withIntervalInMinutes(1)	//定义时间周期
				.repeatForever();*/
		//通过调度器,指定程序多久执行一次.
		//0 0/1 * * * ? 时间表达式 规定任务多久执行一次
		CronScheduleBuilder scheduleBuilder 
			= CronScheduleBuilder.cronSchedule("0 0/1 * * * ?");
		return TriggerBuilder
				.newTrigger()
				.forJob(orderjobDetail())
				.withIdentity("orderQuartz")
				.withSchedule(scheduleBuilder).build();
	}
}
