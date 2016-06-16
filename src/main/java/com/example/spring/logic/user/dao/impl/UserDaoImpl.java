package com.example.spring.logic.user.dao.impl;

import java.sql.Types;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.example.spring.config.jdbc.ShardDao;
import com.example.spring.logic.user.dao.UserDao;
import com.example.spring.logic.user.model.User;

/**
 * @author gimbyeongsu
 * 
 */
@Repository
public class UserDaoImpl extends ShardDao implements UserDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);
	
	public UserDaoImpl() {
		LOGGER.debug("생성자 UserDaoImpl()");
	}

	@Override
	public void insert(User param) {
		Object[] args = new Object[] { param.getId(), param.getUsername(), param.getPassword(), param.getEmail(), param.getPhone(), param.getAddress() };
		int[] types = new int[] { Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR };
		getJdbcTemplate(param.getId()).update("INSERT INTO user(id, username, password, email, phone, address) VALUES(?,?,?,?,?,?)", args, types);
	}
}
