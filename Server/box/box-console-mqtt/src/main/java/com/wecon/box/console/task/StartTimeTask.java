/**
 * @功能说明:  启动定时任务
 * @创建人  : lph
 * @创建时间:  2017年07月26日 下午3:44:06
 * @修改人  : 
 * @修改时间: 
 * @修改描述: 
 * @Copyright (c)2017 福州富昌维控电子科技有限公司-版权所有
 */
package com.wecon.box.console.task;

import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.wecon.common.timer.QuartzManage;
import com.wecon.common.util.TimeTriggerUtil;

public class StartTimeTask {
	private static Logger logger = LoggerFactory.getLogger(StartTimeTask.class);
	private static final String MQTT_JOB = "MqttJob";

	public void init() {
		new Thread() {
			public void run() {
				try {
					// 延迟5秒
					Thread.sleep(5000);
					// 任务管理
					// 实时监控是否连接上mqtt代理服务器
					Scheduler scheduler = QuartzManage.getScheduler();
					Trigger trigger = TimeTriggerUtil.getTrigger(MQTT_JOB, "2", 5);
					QuartzManage.createJob(scheduler, trigger, MQTT_JOB, null, null, MonitorTaskJob.class);
					
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("启动定时任务失败，原因是：" + e.getMessage(), e);
				}
			}
		}.start();
	}

}
