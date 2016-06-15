package com.example.spring.logic.account.dao.impl;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.example.spring.logic.account.dao.AccountDao;
import com.example.spring.logic.account.model.Account;

@Repository
public class AccountDaoImpl extends JdbcDaoSupport implements AccountDao {

	@Autowired
	@Qualifier("dataSource0")
	public void set(DataSource dataSource) {
		setDataSource(dataSource);
	}

	@Override
	public void insert(Account param) {
		Object[] args = new Object[] { param.getId(), "OK" };
		int[] types = new int[] { Types.INTEGER , Types.VARCHAR };
		getJdbcTemplate().update("INSERT INTO member_log(user_id, action, crt_date) VALUES(?,?,current_timestamp)" , args , types);
	}
}
