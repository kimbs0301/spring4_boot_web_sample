package com.example.spring.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.web.OrderedHiddenHttpMethodFilter;
import org.springframework.boot.context.web.OrderedHttpPutFormContentFilter;
import org.springframework.boot.context.web.OrderedRequestContextFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.example.spring.config.filter.CommonFilter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author gimbyeongsu
 * 
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class RootConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(RootConfig.class);

	@Autowired
	private ObjectMapper objectMapper;
	
	public RootConfig() {
		LOGGER.debug("생성자 RootConfig()");
	}

	@Bean
	public FilterRegistrationBean commonFilter() {
		FilterRegistrationBean registration = new FilterRegistrationBean(new CommonFilter());
		// registration.setEnabled(false);
		registration.setName("commonFilter");
		registration.addUrlPatterns("/*");
		return registration;
	}
	
	@Bean
	public FilterRegistrationBean characterEncodingFilter() {
		FilterRegistrationBean registration = new FilterRegistrationBean(new CharacterEncodingFilter("UTF-8", true));
		registration.setName("characterEncodingFilter");
		registration.addUrlPatterns("/*");
		return registration;
	}
	
	@Bean
	public FilterRegistrationBean hiddenHttpMethodFilter() {
		FilterRegistrationBean registration = new FilterRegistrationBean(new OrderedHiddenHttpMethodFilter());
		registration.setName("hiddenHttpMethodFilter");
		registration.addUrlPatterns("/*");
		return registration;
	}
	
	@Bean
	public FilterRegistrationBean httpPutFormContentFilter() {
		FilterRegistrationBean registration = new FilterRegistrationBean(new OrderedHttpPutFormContentFilter());
		registration.setName("httpPutFormContentFilter");
		registration.addUrlPatterns("/*");
		return registration;
	}
	
	@Bean
	public FilterRegistrationBean requestContextFilter() {
		FilterRegistrationBean registration = new FilterRegistrationBean(new OrderedRequestContextFilter());
		registration.setEnabled(true);
		registration.setName("requestContextFilter");
		registration.addUrlPatterns("/*");
		return registration;
	}

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper om = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return om;
	}
}