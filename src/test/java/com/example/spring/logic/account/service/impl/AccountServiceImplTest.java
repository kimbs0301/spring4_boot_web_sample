package com.example.spring.logic.account.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.junit.JunitConfig;
import com.example.spring.logic.account.service.AccountService;

/**
 * @author gimbyeongsu
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { JunitConfig.class })
@WebAppConfiguration
@ActiveProfiles(profiles = { "junit" })
@TestPropertySource(locations = "classpath:application-junit.properties")
@Transactional
public class AccountServiceImplTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImplTest.class);
	
	@Autowired
	private AccountService accountService;
	
	@Test
	@Rollback(true)
	public void test2() throws Exception {
		LOGGER.debug("");
		accountService.test2();
	}
}
