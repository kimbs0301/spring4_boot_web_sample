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
import org.springframework.core.env.Environment;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

/**
 * @author gimbyeongsu
 * 
 */
@Configuration
@EnableTransactionManagement
public class JdbcConfig implements TransactionManagementConfigurer {
	private static final Logger LOGGER = LoggerFactory.getLogger(JdbcConfig.class);
	
	@Autowired
	private Environment environment;
	
	public JdbcConfig() {
		LOGGER.debug("생성자 JdbcConfig()");
	}

	@Bean(name = "dataSourceData", destroyMethod = "close")
	public DataSource dataSourceData() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
		dataSource.setUrl(environment.getRequiredProperty("jdbc.data.url"));
		dataSource.setUsername(environment.getRequiredProperty("jdbc.data.username"));
		dataSource.setPassword(environment.getRequiredProperty("jdbc.data.password"));
		dataSource.setMaxTotal(5);
		dataSource.setValidationQuery("select 1");
		dataSource.setTestOnBorrow(true);
		dataSource.setTestWhileIdle(true);
		dataSource.setTimeBetweenEvictionRunsMillis(10000);
		dataSource.setMinEvictableIdleTimeMillis(60000);
		dataSource.setMaxWaitMillis(5000);
		return dataSource;
	}

	@Bean(name = "dataSourceLog", destroyMethod = "close")
	public DataSource dataSourceLog() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
		dataSource.setUrl(environment.getRequiredProperty("jdbc.log.url"));
		dataSource.setUsername(environment.getRequiredProperty("jdbc.log.username"));
		dataSource.setPassword(environment.getRequiredProperty("jdbc.log.password"));
		dataSource.setMaxTotal(5);
		dataSource.setValidationQuery("select 1");
		dataSource.setTestOnBorrow(true);
		dataSource.setTestWhileIdle(true);
		dataSource.setTimeBetweenEvictionRunsMillis(10000);
		dataSource.setMinEvictableIdleTimeMillis(60000);
		dataSource.setMaxWaitMillis(5000);
		return dataSource;
	}

	@Bean(name = "dataSourceShard0", destroyMethod = "close")
	public DataSource dataSourceShard0() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
		dataSource.setUrl(environment.getRequiredProperty("jdbc.shard0.url"));
		dataSource.setUsername(environment.getRequiredProperty("jdbc.shard0.username"));
		dataSource.setPassword(environment.getRequiredProperty("jdbc.shard0.password"));
		dataSource.setMaxTotal(5);
		dataSource.setValidationQuery("select 1");
		dataSource.setTestOnBorrow(true);
		dataSource.setTestWhileIdle(true);
		dataSource.setTimeBetweenEvictionRunsMillis(10000);
		dataSource.setMinEvictableIdleTimeMillis(60000);
		dataSource.setMaxWaitMillis(5000);
		return dataSource;
	}

	@Bean(name = "dataSourceShard1", destroyMethod = "close")
	public DataSource dataSourceShard1() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
		dataSource.setUrl(environment.getRequiredProperty("jdbc.shard1.url"));
		dataSource.setUsername(environment.getRequiredProperty("jdbc.shard1.username"));
		dataSource.setPassword(environment.getRequiredProperty("jdbc.shard1.password"));
		dataSource.setMaxTotal(5);
		dataSource.setValidationQuery("select 1");
		dataSource.setTestOnBorrow(true);
		dataSource.setTestWhileIdle(true);
		dataSource.setTimeBetweenEvictionRunsMillis(10000);
		dataSource.setMinEvictableIdleTimeMillis(60000);
		dataSource.setMaxWaitMillis(5000);
		return dataSource;
	}

	// @DependsOn(value = { "dataSourceShard0", "dataSourceShard1" })
	@Bean(name = "dataSourceShards")
	public List<DataSource> dataSourceShards() {
		List<DataSource> dataSources = new ArrayList<>();
		dataSources.add(dataSourceShard0());
		dataSources.add(dataSourceShard1());
		return dataSources;
	}

	@Bean
	public DataSource dataLazyDataSource() {
		return new LazyConnectionDataSourceProxy(dataSourceData());
	}

	@Bean
	public DataSource logLazyDataSource() {
		return new LazyConnectionDataSourceProxy(dataSourceLog());
	}

	@Bean
	public DataSource shard0LazyDataSource() {
		return new LazyConnectionDataSourceProxy(dataSourceShard0());
	}

	@Bean
	public DataSource shard1LazyDataSource() {
		return new LazyConnectionDataSourceProxy(dataSourceShard1());
	}

	@Bean
	public PlatformTransactionManager dataTransactionManager() {
		return new DataSourceTransactionManager(dataSourceData());
	}

	@Bean
	public PlatformTransactionManager logTransactionManager() {
		return new DataSourceTransactionManager(dataSourceLog());
	}

	@Bean
	public PlatformTransactionManager shard0TransactionManager() {
		return new DataSourceTransactionManager(dataSourceShard0());
	}

	@Bean
	public PlatformTransactionManager shard1TransactionManager() {
		return new DataSourceTransactionManager(dataSourceShard1());
	}

	// @Bean(name = "txManager")
	// public PlatformTransactionManager transactionManager() {
	// LOGGER.debug("@@@@@@");
	// ChainedTransactionManager txManager = new ChainedTransactionManager(dataTransactionManager(),
	// logTransactionManager(), shard0TransactionManager(), shard1TransactionManager());
	// return txManager;
	// }

	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		ChainedTransactionManager txManager = new ChainedTransactionManager(dataTransactionManager(),
				logTransactionManager(), shard0TransactionManager(), shard1TransactionManager());
		return txManager;
	}
}
