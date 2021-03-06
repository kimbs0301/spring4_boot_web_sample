package com.example.spring.logic.account.dao.impl;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.spring.logic.account.dao.AccountDao;
import com.example.spring.logic.account.model.Account;

/**
 * @author gimbyeongsu
 * 
 */
@Repository
public class AccountDaoImpl implements AccountDao {

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	@Qualifier("dataSourceData")
	public void set(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void insert(Account param) {
		Object[] args = new Object[] { param.getId(), param.getAmount() };
		int[] types = new int[] { Types.INTEGER , Types.INTEGER };
		jdbcTemplate.update("INSERT INTO account(id, amount) VALUES(?,?)" , args , types);
	}
}
