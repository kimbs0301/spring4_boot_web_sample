package com.example.spring.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;

/**
 * @author gimbyeongsu
 * 
 */
@Profile({ "local", "api" })
@Configuration
@DependsOn(value = { "afterConfig" })
@ComponentScan(basePackages = { "com.example.spring.api" })
public class ApiControllerConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(ApiControllerConfig.class);
	
	public ApiControllerConfig() {
		LOGGER.debug("생성자 ApiControllerConfig()");
	}
}
