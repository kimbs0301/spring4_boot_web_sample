package com.example.app.junit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring.config.DelegatingWebMvcConfig;
import com.example.spring.config.WebAppContextConfig;
import com.example.spring.config.WebMvcConfig;

/**
 * @author gimbyeongsu
 * 
 */
@Configuration
@ComponentScan(basePackages = { "com.example.spring" }, excludeFilters = {
		@Filter(value = { WebAppContextConfig.class, WebMvcConfig.class, DelegatingWebMvcConfig.class }, type = FilterType.ASSIGNABLE_TYPE),
		@Filter(value = { RestController.class, Controller.class }, type = FilterType.ANNOTATION),
		@Filter(pattern = { "com.example.spring.*.model.*" }, type = FilterType.REGEX) })
public class JunitConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(JunitConfig.class);

	public JunitConfig() {
		LOGGER.debug("생성자 JunitConfig()");
	}
}