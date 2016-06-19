package com.example.spring.config.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Tuple;

/**
 * @author gimbyeongsu
 * 
 */
public class ShardRedisTemplateRollback extends AbstractJedisTemplateRollback {
	private static final Logger LOGGER = LoggerFactory.getLogger(ShardRedisTemplateRollback.class);

	private String name;
	private ShardRedisTemplate shardRedisTemplate;
	private List<Pair<String, String>> keyPairList = new ArrayList<>();
	private List<Triplet<String, String, String>> keyTripletList = new ArrayList<>();

	public ShardRedisTemplateRollback(String name) {
		LOGGER.debug("생성자 ShardRedisTemplateRollback()");
		this.name = name;
	}

	@Autowired
	public void setShardedJedisPool(redis.clients.jedis.ShardedJedisPool shardedJedisPool) {
		shardRedisTemplate = new ShardRedisTemplate(name, shardedJedisPool);
	}

	@Override
	public void deleteKeyList() {
		super.deleteKeyList("SHARD", keyPairList, keyTripletList);
	}

	@Override
	public void set(String key, String value) {
		shardRedisTemplate.set(key, value);
		keyPairList.add(Pair.with("SET", key));
	}

	@Override
	public String get(String key) {
		return shardRedisTemplate.get(key);
	}

	@Override
	public void del(String key) {
		shardRedisTemplate.del(key);
	}

	@Override
	public void zadd(String key, String member, double score) {
		shardRedisTemplate.zadd(key, member, score);
		keyTripletList.add(Triplet.with("ZADD", key, member));
	}

	@Override
	public void zaddAndExpire(String key, String member, double score, int seconds) {
		shardRedisTemplate.zaddAndExpire(key, member, score, seconds);
		keyTripletList.add(Triplet.with("ZADD", key, member));
	}

	@Override
	public void zrem(String key, String... members) {
		shardRedisTemplate.zrem(key, members);
	}

	@Override
	public Long zcount(String key, String min, String max) {
		return shardRedisTemplate.zcount(key, min, max);
	}

	@Override
	public Set<Tuple> zrevrangeWithScores(String key, long start, long end) {
		return shardRedisTemplate.zrevrangeWithScores(key, start, end);
	}

	@Override
	public Double zscore(String key, String member) {
		return shardRedisTemplate.zscore(key, member);
	}
}
