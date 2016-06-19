package com.example.spring.config.redis;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisException;

/**
 * @author gimbyeongsu
 * 
 */
public class ShardRedisTemplate extends AbstractJedisTemplate {
	private static final Logger LOGGER = LoggerFactory.getLogger(ShardRedisTemplate.class);

	private String name;
	@Autowired
	private redis.clients.jedis.ShardedJedisPool shardedJedisPool;
	
	public ShardRedisTemplate(String name) {
		LOGGER.debug("생성자 ShardRedisTemplate()");
		this.name = name;
	}

	public ShardRedisTemplate(String name, redis.clients.jedis.ShardedJedisPool shardedJedisPool) {
		LOGGER.debug("생성자 ShardRedisTemplate()");
		this.shardedJedisPool = shardedJedisPool;
		this.name = name;
	}

	@Override
	public void set(String key, String value) {
		LOGGER.debug("{}", name);
		ShardedJedis jedis = shardedJedisPool.getResource();
		try {
			jedis.set(key, value);
		} catch (JedisConnectionException e) {
			LOGGER.error("JedisConnectionException");
			throw e;
		} catch (JedisException e) {
			LOGGER.error("", e);
			throw e;
		} finally {
			jedis.close();
		}
	}

	@Override
	public String get(String key) {
		LOGGER.debug("{}", name);
		ShardedJedis jedis = shardedJedisPool.getResource();
		try {
			return jedis.get(key);
		} catch (JedisConnectionException e) {
			LOGGER.error("JedisConnectionException");
			throw e;
		} catch (JedisException e) {
			LOGGER.error("", e);
			throw e;
		} finally {
			jedis.close();
		}
	}

	@Override
	public void del(String key) {
		LOGGER.debug("{}", name);
		ShardedJedis jedis = shardedJedisPool.getResource();
		try {
			jedis.del(key);
		} catch (JedisConnectionException e) {
			LOGGER.error("JedisConnectionException");
			throw e;
		} catch (JedisException e) {
			LOGGER.error("", e);
			throw e;
		} finally {
			jedis.close();
		}
	}

	@Override
	public void zadd(String key, String member, double score) {
		LOGGER.debug("{}", name);
		ShardedJedis jedis = shardedJedisPool.getResource();
		try {
			jedis.zadd(key, score, member);
		} catch (JedisConnectionException e) {
			LOGGER.error("JedisConnectionException");
			throw e;
		} catch (JedisException e) {
			LOGGER.error("", e);
			throw e;
		} finally {
			jedis.close();
		}
	}

	@Override
	public void zaddAndExpire(String key, String member, double score, int seconds) {
		LOGGER.debug("{}", name);
		ShardedJedis jedis = shardedJedisPool.getResource();
		try {
			jedis.zadd(key, score, member);
			jedis.expire(key, seconds);
		} catch (JedisConnectionException e) {
			LOGGER.error("JedisConnectionException");
			throw e;
		} catch (JedisException e) {
			LOGGER.error("", e);
			throw e;
		} finally {
			jedis.close();
		}
	}

	@Override
	public Double zscore(String key, String member) {
		LOGGER.debug("{}", name);
		ShardedJedis jedis = shardedJedisPool.getResource();
		try {
			return jedis.zscore(key, member);
		} catch (JedisConnectionException e) {
			LOGGER.error("JedisConnectionException");
			throw e;
		} catch (JedisException e) {
			LOGGER.error("", e);
			throw e;
		} finally {
			jedis.close();
		}
	}

	@Override
	public Long zcount(String key, String min, String max) {
		LOGGER.debug("{}", name);
		ShardedJedis jedis = shardedJedisPool.getResource();
		try {
			return jedis.zcount(key, min, max);
		} catch (JedisConnectionException e) {
			LOGGER.error("JedisConnectionException");
			throw e;
		} catch (JedisException e) {
			LOGGER.error("", e);
			throw e;
		} finally {
			jedis.close();
		}
	}
	
	@Override
	public Set<Tuple> zrevrangeWithScores(String key, long start, long end) {
		LOGGER.debug("{}", name);
		ShardedJedis jedis = shardedJedisPool.getResource();
		try {
			return jedis.zrevrangeWithScores(key, start, end);
		} catch (JedisConnectionException e) {
			LOGGER.error("JedisConnectionException");
			throw e;
		} catch (JedisException e) {
			LOGGER.error("", e);
			throw e;
		} finally {
			jedis.close();
		}
	}

	@Override
	public void zrem(String key, String... members) {
		LOGGER.debug("{}", name);
		ShardedJedis jedis = shardedJedisPool.getResource();
		try {
			jedis.zrem(key, members);
		} catch (JedisConnectionException e) {
			LOGGER.error("JedisConnectionException");
			throw e;
		} catch (JedisException e) {
			LOGGER.error("", e);
			throw e;
		} finally {
			jedis.close();
		}
	}
}
