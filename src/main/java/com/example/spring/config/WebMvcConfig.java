package com.example.spring.config;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.web.OrderedHiddenHttpMethodFilter;
import org.springframework.boot.context.web.OrderedHttpPutFormContentFilter;
import org.springframework.boot.context.web.OrderedRequestContextFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.servlet.view.xml.MappingJackson2XmlView;

import com.example.spring.config.filter.CommonFilter;
import com.example.spring.config.viewresolver.JsonViewResolver;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * @author gimbyeongsu
 * 
 */
@Configuration
@DependsOn(value = { "rootConfig" })
public class WebMvcConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(WebMvcConfig.class);

	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private XmlMapper xmlMapper;

	public WebMvcConfig() {
		LOGGER.debug("생성자 WebMvcConfig()");
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
	public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
		ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
		resolver.setOrder(1);
		resolver.setContentNegotiationManager(manager);
		List<ViewResolver> resolvers = new ArrayList<ViewResolver>();
		resolvers.add(jsonViewResolver());
		resolvers.add(jspViewResolver());
		resolvers.add(new BeanNameViewResolver());
		resolver.setViewResolvers(resolvers);
		return resolver;
	}

	@Bean
	public ViewResolver jsonViewResolver() {
		LOGGER.debug("{}", objectMapper);
		return new JsonViewResolver(objectMapper);
	}

	@Bean
	public ViewResolver jspViewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}

	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver commonsMultipartResolver() {
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		commonsMultipartResolver.setDefaultEncoding("utf-8");
		commonsMultipartResolver.setMaxUploadSize(1024 * 1024 * 1); // 1MB
		return commonsMultipartResolver;
	}

	@Bean(name = "jsonView")
	public MappingJackson2JsonView mappingJackson2JsonView() {
		MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
		jsonView.setContentType("application/json; charset=UTF-8");
		return jsonView;
	}

	@Bean(name = "xmlView")
	public MappingJackson2XmlView mappingJackson2XmlView() {
		MappingJackson2XmlView xmlView = new MappingJackson2XmlView(xmlMapper);
		xmlView.setContentType("application/xml; charset=UTF-8");
		return xmlView;
	}

	@Bean(name = "errorJspView")
	public InternalResourceView internalResourceView() {
		InternalResourceView jspView = new InternalResourceView("/WEB-INF/views/error.jsp");
		jspView.setContentType("text/html; charset=UTF-8");
		return jspView;
	}

	// @Bean(name = "simpleMappingExceptionResolver")
	// public SimpleMappingExceptionResolver createSimpleMappingExceptionResolver() {
	// SimpleMappingExceptionResolver r = new SimpleMappingExceptionResolver();
	//
	// Properties mappings = new Properties();
	// mappings.setProperty("DatabaseException", "databaseError");
	// mappings.setProperty("InvalidCreditCardException", "creditCardError");
	//
	// r.setExceptionMappings(mappings);
	// r.setDefaultErrorView("error");
	// r.setExceptionAttribute("ex");
	// r.setWarnLogCategory("example.MvcLogger");
	// return r;
	// }
}