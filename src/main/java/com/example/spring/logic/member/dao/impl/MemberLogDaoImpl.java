package com.example.spring.logic.member.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.spring.logic.member.dao.MemberLogDao;
import com.example.spring.logic.member.model.MemberLog;

/**
 * @author gimbyeongsu
 * 
 */
@Repository
public class MemberLogDaoImpl implements MemberLogDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(MemberLogDaoImpl.class);

	private JdbcTemplate jdbcTemplate;

	public MemberLogDaoImpl() {
		LOGGER.debug("생성자 MemberLogDaoImpl()");
	}

	@Autowired
	@Qualifier("dataSourceLog")
	public void set(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void insert(MemberLog memberLog) {
		Object[] args = new Object[] { memberLog.getId() };
		int[] types = new int[] { Types.INTEGER };
		jdbcTemplate.update("INSERT INTO member_log(id, crt_date) VALUES(?, current_timestamp)", args, types);
	}

	@Override
	public void insert(final List<MemberLog> memberLogs) {
		jdbcTemplate.batchUpdate("INSERT INTO member_log(id, crt_date) VALUES(?, current_timestamp)",
				new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						MemberLog memberLog = memberLogs.get(i);
						ps.setInt(1, memberLog.getId());
					}

					@Override
					public int getBatchSize() {
						return memberLogs.size();
					}
				});
	}
}
