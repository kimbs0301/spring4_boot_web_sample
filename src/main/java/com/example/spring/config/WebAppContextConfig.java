package com.example.spring.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * @author gimbyeongsu
 * 
 */
@Configuration
public class WebAppContextConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(WebAppContextConfig.class);

	public WebAppContextConfig() {
		LOGGER.debug("생성자 WebAppContextConfig()");
	}
	
	// @Bean
	// public ServletRegistrationBean dispatcherRegistration(DispatcherServlet dispatcherServlet) {
	// ServletRegistrationBean registration = new ServletRegistrationBean(
	// dispatcherServlet);
	// registration.addUrlMappings("/sr/*");
	// return registration;
	// }
}
