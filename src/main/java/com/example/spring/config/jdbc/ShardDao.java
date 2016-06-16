package com.example.spring.config.jdbc;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author gimbyeongsu
 * 
 */
public abstract class ShardDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(ShardDao.class);
	
	private JdbcTemplate[] jdbcTemplate;
	private int shardSize;
	
	@Resource
	private List<DataSource> dataSourceShards;
	
	public ShardDao() {
		LOGGER.debug("생성자 ShardDao()");
	}
	
	@PostConstruct
	public void init() {
		LOGGER.debug("ShardDao.init {}", this);
		shardSize = dataSourceShards.size();
		jdbcTemplate = new JdbcTemplate[shardSize];
		for (int i = 0 ; i < shardSize; ++i) {
			DataSource dataSource = dataSourceShards.get(i);
			jdbcTemplate[i] = new JdbcTemplate(dataSource);
		}
	}
	
	public JdbcTemplate getJdbcTemplate(int id) {
		final int shardId = id % shardSize;
		return jdbcTemplate[shardId];
	}
}
