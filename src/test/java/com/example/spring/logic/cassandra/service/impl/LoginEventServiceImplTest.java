package com.example.spring.logic.cassandra.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.example.app.junit.JunitConfig;
import com.example.spring.logic.cassandra.service.LoginEventService;

/**
 * @author gimbyeongsu
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { JunitConfig.class })
@WebAppConfiguration
@ActiveProfiles(profiles = { "junit" })
@TestPropertySource(locations = "classpath:application-junit.properties")
public class LoginEventServiceImplTest {

	@Autowired
	private LoginEventService loginEventService;
	
	@Test
	public void test() throws Exception {
		loginEventService.test();
	}
	
	@Test
	public void test_loopInsert() throws Exception {
		loginEventService.loopInsert();
	}
}
