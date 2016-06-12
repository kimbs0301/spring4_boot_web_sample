package com.example.spring.config.viewresolver;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author gimbyeongsu
 * 
 */
public class JsonViewResolver implements ViewResolver {
	private static final Logger LOGGER = LoggerFactory.getLogger(JsonViewResolver.class);
	
	private ObjectMapper objectMapper;
	
	public JsonViewResolver(ObjectMapper objectMapper) {
		LOGGER.debug("생성자 JsonViewResolver()");
		this.objectMapper = objectMapper;
	}
	
	@Override
	public View resolveViewName(String viewName, Locale locale) throws Exception {
		MappingJackson2JsonView view = new MappingJackson2JsonView(objectMapper);
		return view;
	}
}