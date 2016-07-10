package com.example.spring.logic.user.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.junit.JunitSpringAnnotation;
import com.example.spring.logic.member.service.impl.MemberServiceImplTest;
import com.example.spring.logic.user.service.UserService;

/**
 * @author gimbyeongsu
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@JunitSpringAnnotation
@Transactional
public class UserServiceImplTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(MemberServiceImplTest.class);
	
	@Autowired
	private UserService userService;
	
	@Test
	@Rollback(true)
	public void test() throws Exception {
		LOGGER.debug("");
		userService.test();
	}
}
