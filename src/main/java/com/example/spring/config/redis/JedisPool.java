package com.example.spring.config.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.javatuples.Quintet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.AbstractFactoryBean;

/**
 * @author gimbyeongsu
 * 
 */
public class JedisPool extends AbstractFactoryBean<redis.clients.jedis.JedisPool> {
	private static final Logger LOGGER = LoggerFactory.getLogger(JedisPool.class);

	private redis.clients.jedis.JedisPool jedisPool;
	private GenericObjectPoolConfig poolConfig;
	private Quintet<String, Integer, Integer, Integer, Integer> jedisConfig;

	public JedisPool(GenericObjectPoolConfig poolConfig, Quintet<String, Integer, Integer, Integer, Integer> jedisConfig) {
		LOGGER.debug("생성자 JedisPool()");
		this.poolConfig = poolConfig;
		this.jedisConfig = jedisConfig;
	}

	@Override
	protected redis.clients.jedis.JedisPool createInstance() throws Exception {
		if (jedisPool == null) {
			String host = jedisConfig.getValue0();
			int port = jedisConfig.getValue1();
			int connTimeout = jedisConfig.getValue2();
			int readTimeout = jedisConfig.getValue3();
			int database = jedisConfig.getValue4();
			jedisPool = new redis.clients.jedis.JedisPool(poolConfig, host, port, connTimeout, readTimeout, null,
					database, null);
		}
		return jedisPool;
	}

	@Override
	public Class<?> getObjectType() {
		return redis.clients.jedis.JedisPool.class;
	}

	@Override
	protected void destroyInstance(redis.clients.jedis.JedisPool instance) throws Exception {
		if (jedisPool != null) {
			LOGGER.debug("{}", jedisPool);
			jedisPool.destroy();
		}
	}
}
