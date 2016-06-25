package com.example.spring.logic.cassandra.service.impl;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.spring.logic.cassandra.dao.LoginEventDao;
import com.example.spring.logic.cassandra.model.LoginEvent;
import com.example.spring.logic.cassandra.model.LoginEventKey;
import com.example.spring.logic.cassandra.service.LoginEventService;

/**
 * @author gimbyeongsu
 * 
 */
@Service
public class LoginEventServiceImpl implements LoginEventService {
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginEventServiceImpl.class);
	
	@Autowired
	private LoginEventDao loginEventDao;

	@Override
	public void test() {
		LOGGER.debug("");
		
		LoginEventKey loginEventKey = new LoginEventKey();
		loginEventKey.setPersonId("10000");
		loginEventKey.setEventTime(new DateTime(2016, 6, 20, 1, 1, 1).toDate());
		LoginEvent loginEvent = new LoginEvent();
		loginEvent.setPk(loginEventKey);
		loginEvent.setEventCode(1);
		loginEvent.setIpAddress("127.0.0.1");
		
		loginEventDao.insert(loginEvent);
		
		LoginEvent result = loginEventDao.get(loginEventKey);
		LOGGER.debug("{}", result);
		
		LoginEventKey loginEventKey2 = new LoginEventKey();
		loginEventKey2.setPersonId("10001");
		loginEventKey2.setEventTime(new DateTime(2016, 6, 20, 1, 1, 1).toDate());
		loginEvent.setPk(loginEventKey2);
		loginEventDao.insert(loginEvent);
		
		String cql = "select * from login_event where person_id in ('10000','10001')";
		List<LoginEvent> list = loginEventDao.selectIn(cql);
		for (LoginEvent each : list) {
			LOGGER.debug("{}", each);
		}
	}

	@Override
	public void loopInsert() {
		LOGGER.debug("start");
		
		for (int i = 100; i < 200; ++i) {
			LoginEventKey loginEventKey = new LoginEventKey();
			loginEventKey.setPersonId(Integer.toString(i));
			loginEventKey.setEventTime(new Date());
			LoginEvent loginEvent = new LoginEvent();
			loginEvent.setPk(loginEventKey);
			loginEvent.setEventCode(1);
			
			loginEventDao.insert(loginEvent);
		}
		LOGGER.debug("end");
	}
}
