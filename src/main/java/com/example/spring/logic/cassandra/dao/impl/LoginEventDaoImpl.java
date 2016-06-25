package com.example.spring.logic.cassandra.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.stereotype.Repository;

import com.example.spring.logic.cassandra.dao.LoginEventDao;
import com.example.spring.logic.cassandra.model.LoginEvent;
import com.example.spring.logic.cassandra.model.LoginEventKey;

/**
 * @author gimbyeongsu
 * 
 */
@Repository
public class LoginEventDaoImpl implements LoginEventDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginEventDaoImpl.class);

	@Autowired
	private CassandraTemplate cassandraTemplate;

	public LoginEventDaoImpl() {
		LOGGER.debug("생성자 LoginEventDaoImpl()");
	}

	@Override
	public void insert(LoginEvent loginEvent) {
		cassandraTemplate.insert(loginEvent);
	}

	@Override
	public LoginEvent get(LoginEventKey loginEventKey) {
		return cassandraTemplate.selectOneById(LoginEvent.class, loginEventKey);
	}

	@Override
	public List<LoginEvent> selectIn(String cql) {
		return cassandraTemplate.select(cql, LoginEvent.class);
	}
}
