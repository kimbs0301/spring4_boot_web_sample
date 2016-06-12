package com.example.spring.config.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gimbyeongsu
 * 
 */
public class CommonFilter implements Filter {
	private static final Logger LOGGER = LoggerFactory.getLogger(CommonFilter.class);
	
	public CommonFilter() {
		LOGGER.debug("생성자 CommonFilter()");
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		LOGGER.debug("test filter");
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}
}