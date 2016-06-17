package com.example.spring.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.Ordered;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author gimbyeongsu
 * 
 */
@AutoConfigureOrder(Ordered.LOWEST_PRECEDENCE)
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class RootConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(RootConfig.class);

	@Autowired
	private ObjectMapper objectMapper;

	public RootConfig() {
		LOGGER.debug("생성자 RootConfig()");
	}

	@Bean(name = "objectMapper")
	public ObjectMapper objectMapper() {
		ObjectMapper om = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return om;
	}
}