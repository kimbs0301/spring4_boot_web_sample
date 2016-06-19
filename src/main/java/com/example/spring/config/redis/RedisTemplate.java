package com.example.spring.config.redis;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisException;

/**
 * @author gimbyeongsu
 * 
 */
public class RedisTemplate extends AbstractJedisTemplate {
	private static final Logger LOGGER = LoggerFactory.getLogger(RedisTemplate.class);

	private String name;
	@Autowired
	private redis.clients.jedis.JedisPool jedisPool;
	
	public RedisTemplate(String name) {
		LOGGER.debug("생성자 RedisTemplate()");
		this.name = name;
	}

	public RedisTemplate(String name, redis.clients.jedis.JedisPool jedisPool) {
		LOGGER.debug("생성자 RedisTemplate()");
		this.jedisPool = jedisPool;
		this.name = name;
	}

	@Override
	public void set(String key, String value) {
		LOGGER.debug("{}", name);
		Jedis jedis = jedisPool.getResource();
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
		Jedis jedis = jedisPool.getResource();
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
		Jedis jedis = jedisPool.getResource();
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
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.zadd(key, score, member);
		} catch (JedisConnectionException e) {
			LOGGER.error("JedisConnectionException");
			throw e;
		} catch (Exception e) {
			LOGGER.error("", e);
			throw e;
		} finally {
			jedis.close();
		}
	}

	@Override
	public void zaddAndExpire(String key, String member, double score, int seconds) {
		LOGGER.debug("{}", name);
		Jedis jedis = jedisPool.getResource();
		try {
			Transaction tx = jedis.multi();
			tx.zadd(key, score, member);
			tx.expire(key, seconds);
			tx.exec();
		} catch (JedisConnectionException e) {
			LOGGER.error("JedisConnectionException");
			throw e;
		} catch (Exception e) {
			LOGGER.error("", e);
			throw e;
		} finally {
			jedis.close();
		}
	}

	@Override
	public Double zscore(String key, String member) {
		LOGGER.debug("{}", name);
		Jedis jedis = jedisPool.getResource();
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
		Jedis jedis = jedisPool.getResource();
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
		Jedis jedis = jedisPool.getResource();
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
		Jedis jedis = jedisPool.getResource();
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
