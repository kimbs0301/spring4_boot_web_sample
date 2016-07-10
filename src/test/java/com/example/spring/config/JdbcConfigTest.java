package com.example.spring.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.junit.JunitSpringAnnotation;

/**
 * @author gimbyeongsu
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@JunitSpringAnnotation
@Transactional
public class JdbcConfigTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(JdbcConfigTest.class);
	
	@Test
	public void test() throws Exception {
		LOGGER.debug("");
	}
}
