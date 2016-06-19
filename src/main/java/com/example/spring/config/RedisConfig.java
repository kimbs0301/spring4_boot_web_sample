package com.example.spring.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.javatuples.Quintet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.Protocol;
import redis.clients.util.Sharded;

import com.example.spring.config.redis.JedisPool;
import com.example.spring.config.redis.RedisTemplate;
import com.example.spring.config.redis.RedisTemplateRollback;
import com.example.spring.config.redis.ShardRedisTemplate;
import com.example.spring.config.redis.ShardRedisTemplateRollback;
import com.example.spring.config.redis.ShardedJedisPool;

/**
 * @author gimbyeongsu
 * 
 */
@Configuration
public class RedisConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(RedisConfig.class);

	public RedisConfig() {
		LOGGER.debug("생성자 RedisConfig()");
	}

	@Bean
	public JedisPool JedisPool() {
		GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
		poolConfig.setMaxTotal(50);
		poolConfig.setMaxIdle(30);
		poolConfig.setMaxWaitMillis(2000);
		poolConfig.setTestOnBorrow(true);
		poolConfig.setTestWhileIdle(true);
		poolConfig.setMinEvictableIdleTimeMillis(60000);
		poolConfig.setTimeBetweenEvictionRunsMillis(30000);
		poolConfig.setNumTestsPerEvictionRun(-1);

		String host = "127.0.0.1";
		int port = 6379;
		int connTimeout = 3000;
		int readTimeout = 5000;
		int database = Protocol.DEFAULT_DATABASE;
		Quintet<String, Integer, Integer, Integer, Integer> jedisConfig = Quintet.with(host, port, connTimeout,
				readTimeout, database);

		JedisPool jedisPool = new JedisPool(poolConfig, jedisConfig);
		return jedisPool;
	}

	@Profile({ "local", "svc" })
	@Bean
	public RedisTemplate redisTemplate() {
		RedisTemplate redisTemplate = new RedisTemplate("DEFAULT");
		return redisTemplate;
	}

	@Profile({ "junit" })
	@Bean(name = "redisTemplate")
	public RedisTemplateRollback redisTemplateRollback() {
		RedisTemplateRollback redisTemplate = new RedisTemplateRollback("DEFAULT");
		return redisTemplate;
	}

	@Bean
	public ShardedJedisPool shardedJedisPool() {
		GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
		poolConfig.setMaxTotal(50);
		poolConfig.setMaxIdle(30);
		poolConfig.setMaxWaitMillis(2000);
		poolConfig.setTestOnBorrow(true);
		poolConfig.setTestWhileIdle(true);
		poolConfig.setMinEvictableIdleTimeMillis(60000);
		poolConfig.setTimeBetweenEvictionRunsMillis(30000);
		poolConfig.setNumTestsPerEvictionRun(-1);

		List<JedisShardInfo> shardInfos = new ArrayList<JedisShardInfo>();
		JedisShardInfo jedisShardInfo0 = new JedisShardInfo("127.0.0.1", 6381, 3000, 5000, Sharded.DEFAULT_WEIGHT);
		jedisShardInfo0.setPassword(null);
		shardInfos.add(jedisShardInfo0);
		JedisShardInfo jedisShardInfo1 = new JedisShardInfo("127.0.0.1", 6380, 3000, 5000, Sharded.DEFAULT_WEIGHT);
		jedisShardInfo1.setPassword(null);
		shardInfos.add(jedisShardInfo1);

		ShardedJedisPool jedisPool = new ShardedJedisPool(poolConfig, shardInfos);
		return jedisPool;
	}

	@Profile({ "local", "svc" })
	@Bean
	public ShardRedisTemplate shardRedisTemplate() {
		ShardRedisTemplate redisTemplate = new ShardRedisTemplate("SHARD");
		return redisTemplate;
	}

	@Profile({ "junit" })
	@Bean(name = "shardRedisTemplate")
	public ShardRedisTemplateRollback shardRedisTemplateRollback() {
		ShardRedisTemplateRollback redisTemplate = new ShardRedisTemplateRollback("SHARD");
		return redisTemplate;
	}
}
