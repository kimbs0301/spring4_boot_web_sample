package com.example.spring.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author gimbyeongsu
 * 
 */
public class AppContextAware implements ApplicationContextAware {
	private static final Logger LOGGER = LoggerFactory.getLogger(AppContextAware.class);
	private static ApplicationContext applicationContext;
	
	public AppContextAware() {
		LOGGER.debug("생성자 AppContextAware()");
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		AppContextAware.applicationContext = applicationContext;
		LOGGER.debug("{}", applicationContext.toString());
	}

	public static ApplicationContext getAppCtx() {
		return applicationContext;
	}
}