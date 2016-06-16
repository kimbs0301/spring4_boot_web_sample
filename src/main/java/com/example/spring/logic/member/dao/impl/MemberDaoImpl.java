package com.example.spring.logic.member.dao.impl;

import java.sql.Types;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.example.spring.config.jdbc.ShardDao;
import com.example.spring.logic.member.dao.MemberDao;
import com.example.spring.logic.member.model.Member;

/**
 * @author gimbyeongsu
 * 
 */
@Repository
public class MemberDaoImpl extends ShardDao implements MemberDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(MemberDaoImpl.class);
	
	public MemberDaoImpl() {
		LOGGER.debug("생성자 MemberDaoImpl()");
	}

	// private JdbcTemplate[] jdbcTemplate;
	// private int shardSize;
	//
	// @Resource
	// private List<DataSource> dataSourceShards;
	//
	// @PostConstruct
	// public void init() {
	// shardSize = dataSourceShards.size();
	// jdbcTemplate = new JdbcTemplate[shardSize];
	// for (int i = 0 ; i < shardSize; ++i) {
	// DataSource dataSource = dataSourceShards.get(i);
	// jdbcTemplate[i] = new JdbcTemplate(dataSource);
	// }
	// }

	// @Override
	// public void insert(Member param) {
	// LOGGER.debug("");
	// int shardId = param.getId() % shardSize;
	// Object[] args = new Object[] { param.getId(), param.getName() };
	// int[] types = new int[] { Types.INTEGER , Types.VARCHAR };
	// jdbcTemplate[shardId].update("INSERT INTO member(id, name) VALUES(?,?)" , args , types);
	// }

	@Override
	public void insert(Member param) {
		LOGGER.debug("");
		Object[] args = new Object[] { param.getId(), param.getName() };
		int[] types = new int[] { Types.INTEGER, Types.VARCHAR };
		getJdbcTemplate(param.getId()).update("INSERT INTO member(id, name) VALUES(?,?)", args, types);
	}
}
