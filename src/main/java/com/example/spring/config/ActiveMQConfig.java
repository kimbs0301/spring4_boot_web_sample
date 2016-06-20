package com.example.spring.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * @author gimbyeongsu
 *
 */
@Configuration
@DependsOn(value = { "rootConfig" })
public class ActiveMQConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(ActiveMQConfig.class);

	public ActiveMQConfig() {
		LOGGER.debug("생성자 ActiveMQConfig()");
	}
	
	// http://shengwangi.blogspot.kr/2014/10/spring-jms-with-activemq-helloworld-example-send.html

	@Bean
	public ActiveMQConnectionFactory activeMQConnectionFactory() {
		ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory("tcp://192.168.203.143:61616");
		return activeMQConnectionFactory;
	}
}
