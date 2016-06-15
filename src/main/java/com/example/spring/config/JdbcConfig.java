package com.example.spring.config;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author gimbyeongsu
 * 
 */
@Configuration
@EnableTransactionManagement
public class JdbcConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(JdbcConfig.class);

	public JdbcConfig() {
		LOGGER.debug("생성자 JdbcConfig()");
	}

	@Bean(name = "dataSource0")
	public static DataSource dataSourceTest0() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/test0");
		dataSource.setUsername("root");
		dataSource.setPassword("123456");
		dataSource.setMaxTotal(5);
		dataSource.setValidationQuery("select 1");
		dataSource.setTestOnBorrow(true);
		dataSource.setTestWhileIdle(true);
		dataSource.setTimeBetweenEvictionRunsMillis(10000);
		dataSource.setMinEvictableIdleTimeMillis(60000);
		dataSource.setMaxWaitMillis(5000);
		return dataSource;
	}

	@Bean(name = "dataSource1")
	public static DataSource dataSourceTest1() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/test1");
		dataSource.setUsername("root");
		dataSource.setPassword("123456");
		dataSource.setMaxTotal(5);
		dataSource.setValidationQuery("select 1");
		dataSource.setTestOnBorrow(true);
		dataSource.setTestWhileIdle(true);
		dataSource.setTimeBetweenEvictionRunsMillis(10000);
		dataSource.setMinEvictableIdleTimeMillis(60000);
		dataSource.setMaxWaitMillis(5000);
		return dataSource;
	}

	@Bean(name = "txManager")
	public ChainedTransactionManager transactionManager() {
		DataSourceTransactionManager dataSourceTransactionManager0 = new DataSourceTransactionManager();
		dataSourceTransactionManager0.setDataSource(dataSourceTest0());
		DataSourceTransactionManager dataSourceTransactionManager1 = new DataSourceTransactionManager();
		dataSourceTransactionManager1.setDataSource(dataSourceTest1());
		ChainedTransactionManager txManager = new ChainedTransactionManager(dataSourceTransactionManager0, dataSourceTransactionManager1);
		return txManager;
	}
}
