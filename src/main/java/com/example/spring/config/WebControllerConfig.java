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
@Profile({ "local", "web" })
@Configuration
@DependsOn(value = { "afterConfig" })
@ComponentScan(basePackages = { "com.example.spring.web" })
public class WebControllerConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(WebControllerConfig.class);

	public WebControllerConfig() {
		LOGGER.debug("생성자 WebControllerConfig()");
	}
}
