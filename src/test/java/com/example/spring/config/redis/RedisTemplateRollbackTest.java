package com.example.spring.config.redis;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.app.junit.JunitSpringAnnotation;

/**
 * @author gimbyeongsu
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@JunitSpringAnnotation
public class RedisTemplateRollbackTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(RedisTemplateRollbackTest.class);
	
	@Autowired
	private AbstractJedisTemplate redisTemplate;
	
	@After
	public void afterRedisRollback() {
		((AbstractJedisTemplateRollback) redisTemplate).deleteKeyList();
	}
	
	@Test
	public void test_set() throws Exception {
		redisTemplate.set("KKK", "111");
		String value = redisTemplate.get("KKK");
		LOGGER.debug("{}", value);
	}
}
