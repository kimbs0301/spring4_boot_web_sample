package com.example.spring.logic.account.dao.impl;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.spring.logic.account.dao.AccountLogDao;
import com.example.spring.logic.account.model.AccountLog;

/**
 * @author gimbyeongsu
 * 
 */
@Repository
public class AccountLogDaoImpl implements AccountLogDao {
	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("dataSourceLog")
	public void set(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void insert(AccountLog param) {
		Object[] args = new Object[] { param.getText() };
		int[] types = new int[] { Types.VARCHAR };
		jdbcTemplate.update("INSERT INTO account_log(text, crt_date) VALUES(?,current_timestamp)" , args , types);
	}
}
