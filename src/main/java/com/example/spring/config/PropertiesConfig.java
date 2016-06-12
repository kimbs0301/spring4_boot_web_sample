package com.example.spring.config;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;

/**
 * @author gimbyeongsu
 * 
 */
@Configuration
public class PropertiesConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesConfig.class);

	@Autowired
	private Environment environment;

	public PropertiesConfig() {
		LOGGER.debug("생성자 PropertiesConfig()");
	}

	@Bean(name = "configProperties")
	public PropertiesFactoryBean configProperties() {
		PropertiesFactoryBean properties = new PropertiesFactoryBean();
		String[] profiles = environment.getActiveProfiles();
		ClassPathResource[] classPathResources = new ClassPathResource[profiles.length];
		for (int i = 0; i < profiles.length; ++i) {
			classPathResources[i] = new ClassPathResource("application-" + profiles[i] + ".properties");
		}
		properties.setLocations(classPathResources);
		return properties;
	}

	@PreDestroy
	public void destroy() {
		LOGGER.debug("spring.profiles.active={}", environment.getRequiredProperty("spring.profiles.active"));
	}
}