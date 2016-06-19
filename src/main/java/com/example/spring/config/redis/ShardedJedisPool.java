package com.example.spring.config.redis;

import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.AbstractFactoryBean;

import redis.clients.jedis.JedisShardInfo;
import redis.clients.util.Hashing;
import redis.clients.util.Sharded;

/**
 * @author gimbyeongsu
 * 
 */
public class ShardedJedisPool extends AbstractFactoryBean<redis.clients.jedis.ShardedJedisPool> {
	private static final Logger LOGGER = LoggerFactory.getLogger(ShardedJedisPool.class);

	private redis.clients.jedis.ShardedJedisPool shardedJedisPool;
	private GenericObjectPoolConfig poolConfig;
	private List<JedisShardInfo> shardInfos;

	public ShardedJedisPool(GenericObjectPoolConfig poolConfig, List<JedisShardInfo> shardInfos) {
		LOGGER.debug("생성자 ShardedJedisPool()");
		this.poolConfig = poolConfig;
		this.shardInfos = shardInfos;
	}

	@Override
	protected redis.clients.jedis.ShardedJedisPool createInstance() throws Exception {
		Hashing algo = Hashing.MD5;
		Pattern keyTagPattern = Sharded.DEFAULT_KEY_TAG_PATTERN;

		if (shardedJedisPool == null) {
			shardedJedisPool = new redis.clients.jedis.ShardedJedisPool(poolConfig, shardInfos, algo, keyTagPattern);
		}
		return shardedJedisPool;
	}

	@Override
	public Class<?> getObjectType() {
		return redis.clients.jedis.ShardedJedisPool.class;
	}

	@Override
	protected void destroyInstance(redis.clients.jedis.ShardedJedisPool instance) throws Exception {
		if (shardedJedisPool != null) {
			LOGGER.debug("{}", shardedJedisPool);
			shardedJedisPool.destroy();
		}
	}
}