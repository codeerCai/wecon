package com.wecon.box.console.main;

import org.apache.logging.log4j.LogManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * Created by zengzhipeng on 2017/7/25.
 */
public class MqttManager {
	private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(MqttManager.class.getName());
	

	public static void main(String[] args) throws InterruptedException {
		String profile = "dev";
		if (args.length > 0) {
			profile = args[0];
		}
		logger.info("run profile:" + profile);
		System.setProperty("spring.profiles.active", profile);
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
//		GetPublishClient getPublishClient=applicationContext.getBean(GetPublishClient.class);
//		MqttClient client=getPublishClient.conMqtt();
//		System.out.println(client);
		
		// RedisPiBoxApi redisPiBoxApi =
		// applicationContext.getBean(RedisPiBoxApi.class);
		/*
		 * TestTask taskTest = new TestTask(); taskTest.start();
		 */
	}

}
