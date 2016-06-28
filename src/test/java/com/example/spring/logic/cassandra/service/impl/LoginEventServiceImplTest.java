package com.example.spring.logic.cassandra.service.impl;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.cassandra.core.CassandraTemplate;
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
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { JunitConfig.class })
@WebAppConfiguration
@ActiveProfiles(profiles = { "junit" })
@TestPropertySource(locations = "classpath:application-junit.properties")
public class LoginEventServiceImplTest {

	@Autowired
	private LoginEventService loginEventService;

	@Autowired
	private CassandraTemplate cassandraTemplate;

	@Test
	public void test0_init() throws Exception {
		cassandraTemplate
				.execute("CREATE TABLE login_event(person_id text, event_time timestamp, event_code int, ip_address text, primary key (person_id, event_time)) with CLUSTERING ORDER BY (event_time DESC)");
	}

	@Test
	public void test1() throws Exception {
		loginEventService.test();
	}

	@Test
	public void test2_loopInsert() throws Exception {
		loginEventService.loopInsert();
	}

	@Test
	public void test3_dropTable() throws Exception {
		cassandraTemplate.truncate("login_event");
		cassandraTemplate.execute("DROP TABLE login_event");
	}
}
