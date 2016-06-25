package com.example.spring.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cassandra.config.CassandraCqlClusterFactoryBean;
import org.springframework.cassandra.config.CompressionType;
import org.springframework.cassandra.core.keyspace.CreateKeyspaceSpecification;
import org.springframework.cassandra.core.keyspace.DropKeyspaceSpecification;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.CassandraEntityClassScanner;
import org.springframework.data.cassandra.config.CassandraSessionFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.convert.CassandraConverter;
import org.springframework.data.cassandra.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.CassandraAdminOperations;
import org.springframework.data.cassandra.core.CassandraAdminTemplate;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.mapping.BasicCassandraMappingContext;
import org.springframework.data.cassandra.mapping.CassandraMappingContext;

import com.datastax.driver.core.AuthProvider;
import com.datastax.driver.core.PoolingOptions;
import com.datastax.driver.core.SocketOptions;
import com.datastax.driver.core.policies.LoadBalancingPolicy;
import com.datastax.driver.core.policies.ReconnectionPolicy;
import com.datastax.driver.core.policies.RetryPolicy;

/**
 * @author gimbyeongsu
 * 
 */
@Configuration
// @EnableCassandraRepositories(basePackages = { "com.example.spring.logic.cassandra.repo" })
public class CassandraConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(CassandraConfig.class);
	
	@Autowired
	private Environment environment;

	public CassandraConfig() {
		LOGGER.debug("생성자 CassandraConfig()");
	}

	@Bean
	public CassandraTemplate cassandraTemplate() throws Exception {
		return new CassandraTemplate(session().getObject(), cassandraConverter());
	}

	@Bean
	public CassandraAdminOperations cassandraAdminTemplate() throws Exception {
		return new CassandraAdminTemplate(session().getObject(), cassandraConverter());
	}

	@Bean
	public CassandraClusterFactoryBean cluster() {
		CassandraClusterFactoryBean bean = new CassandraClusterFactoryBean();
		bean.setAuthProvider(getAuthProvider());
		bean.setCompressionType(getCompressionType());
		bean.setContactPoints(environment.getRequiredProperty("cassandra.contactpoints"));
		bean.setKeyspaceCreations(getKeyspaceCreations());
		bean.setKeyspaceDrops(getKeyspaceDrops());
		bean.setLoadBalancingPolicy(getLoadBalancingPolicy());
		bean.setMetricsEnabled(CassandraCqlClusterFactoryBean.DEFAULT_METRICS_ENABLED);
		bean.setPort(environment.getRequiredProperty("cassandra.port", Integer.class));
		bean.setReconnectionPolicy(getReconnectionPolicy());
		bean.setPoolingOptions(getPoolingOptions());
		bean.setRetryPolicy(getRetryPolicy());
		bean.setShutdownScripts(getShutdownScripts());
		bean.setSocketOptions(getSocketOptions());
		bean.setStartupScripts(getStartupScripts());
		return bean;
	}

	@Bean
	public CassandraSessionFactoryBean session() throws Exception {
		CassandraSessionFactoryBean session = new CassandraSessionFactoryBean();
		session.setCluster(cluster().getObject());
		session.setConverter(cassandraConverter());
		session.setKeyspaceName(environment.getRequiredProperty("cassandra.keyspace"));
		session.setSchemaAction(SchemaAction.NONE);
		session.setStartupScripts(getStartupScripts());
		session.setShutdownScripts(getShutdownScripts());
		return session;
	}

	@Bean
	public CassandraMappingContext cassandraMapping() throws ClassNotFoundException {
		BasicCassandraMappingContext mappingContext = new BasicCassandraMappingContext();
		mappingContext.setInitialEntitySet(CassandraEntityClassScanner.scan(getEntityBasePackages()));
		return mappingContext;
	}

	@Bean
	public CassandraConverter cassandraConverter() throws Exception {
		return new MappingCassandraConverter(cassandraMapping());
	}

	private String[] getEntityBasePackages() {
		return new String[] { "com.example.spring.logic.cassandra.model" };
	}

	private List<String> getStartupScripts() {
		List<String> result = new ArrayList<>();
		return result;
	}

	private SocketOptions getSocketOptions() {
		return null;
	}

	private List<String> getShutdownScripts() {
		List<String> result = new ArrayList<>();
		return result;
	}

	private ReconnectionPolicy getReconnectionPolicy() {
		return null;
	}

	private RetryPolicy getRetryPolicy() {
		return null;
	}

	private PoolingOptions getPoolingOptions() {
		return null;
	}

	private LoadBalancingPolicy getLoadBalancingPolicy() {
		return null;
	}

	private List<DropKeyspaceSpecification> getKeyspaceDrops() {
		return Collections.emptyList();
	}

	private List<CreateKeyspaceSpecification> getKeyspaceCreations() {
		return Collections.emptyList();
	}

	private CompressionType getCompressionType() {
		return null;
	}

	private AuthProvider getAuthProvider() {
		return null;
	}
}
