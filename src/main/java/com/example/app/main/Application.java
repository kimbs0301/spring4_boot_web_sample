package com.example.app.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;

import com.example.app.embedded.EmbeddedTomcatConfig;
import com.example.spring.config.WebAppContextConfig;

/**
 * @author gimbyeongsu
 * 
 */
@Configurable
@ComponentScan(basePackages = { "com.example.spring" }, basePackageClasses = { EmbeddedTomcatConfig.class }, excludeFilters = {
		@Filter(value = { WebAppContextConfig.class }, type = FilterType.ASSIGNABLE_TYPE),
		@Filter(pattern = { "com.example.spring.*.model.*" }, type = FilterType.REGEX) })
public class Application {
	private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
	
	public Application() {
		LOGGER.debug("생성자 Application()");
	}

	public static void main(String[] args) throws Exception {
		LOGGER.debug("start");
		SpringApplicationBuilder springApplicationBuilder = new SpringApplicationBuilder(Application.class);
		SpringApplication springApplication = springApplicationBuilder.build();
		springApplication.run();
	}
}
