package com.example.spring.logic.rank.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.app.junit.JunitSpringAnnotation;
import com.example.spring.config.redis.AbstractJedisTemplate;
import com.example.spring.config.redis.AbstractJedisTemplateRollback;
import com.example.spring.logic.rank.model.UserRank;
import com.example.spring.logic.rank.service.RankService;

/**
 * @author gimbyeongsu
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@JunitSpringAnnotation
public class RankServiceImplTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(RankServiceImplTest.class);

	@Qualifier("redisTemplate")
	@Autowired
	private AbstractJedisTemplate redisTemplate;
	@Autowired
	private RankService rankService;

	private static Map<String, Integer> TEST_MEMBER_MAP = new HashMap<>();

	@BeforeClass
	public static void setUp() {
		TEST_MEMBER_MAP.put("user001", 100);
		TEST_MEMBER_MAP.put("user002", 98);
		TEST_MEMBER_MAP.put("user003", 99);
		TEST_MEMBER_MAP.put("user004", 98);
		TEST_MEMBER_MAP.put("user005", 95);
	}

	@After
	public void afterRedisRollback() {
		((AbstractJedisTemplateRollback) redisTemplate).deleteKeyList();
	}

	@Test
	public void test_setScore() throws Exception {
		for (String member : TEST_MEMBER_MAP.keySet()) {
			LOGGER.debug("{}", member);
			rankService.setScore(member, TEST_MEMBER_MAP.get(member));
		}
	}

	@Test
	public void test_getTopRank() throws Exception {
		for (String member : TEST_MEMBER_MAP.keySet()) {
			LOGGER.debug("{}", member);
			rankService.setScore(member, TEST_MEMBER_MAP.get(member));
		}

		int start = 0;
		int end = 10;
		List<UserRank> result = rankService.getTopRank(start, end);
		for (UserRank each : result) {
			LOGGER.debug("id:{} rank:{} point:{}", each.getId(), each.getRank(), each.getPoint());
		}
	}
}
