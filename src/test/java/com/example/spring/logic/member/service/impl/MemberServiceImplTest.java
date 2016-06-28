package com.example.spring.logic.member.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Ignore;
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
import com.example.spring.logic.member.model.MemberLog;
import com.example.spring.logic.member.service.MemberService;

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
public class MemberServiceImplTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(MemberServiceImplTest.class);

	@Autowired
	private MemberService memberService;

	@Test
	@Rollback(true)
	public void test() throws Exception {
		LOGGER.debug("");
		memberService.test();
	}

	@Ignore
	@Test
	public void test_insertForkJoin() throws Exception {
		LOGGER.debug("");
		Random rand = new Random();
		List<MemberLog> memberLogs = new ArrayList<>();
		for (int i = 0; i < 10000; ++i) {
			MemberLog log = new MemberLog();
			memberLogs.add(log);

			log.setRollback(rand.nextBoolean());
			log.setId(i);
		}

		memberService.insertForkJoin(memberLogs);
	}
}
