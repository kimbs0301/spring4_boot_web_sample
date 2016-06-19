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
public class RedisTemplateRollback extends AbstractJedisTemplateRollback {
	private static final Logger LOGGER = LoggerFactory.getLogger(RedisTemplateRollback.class);

	private String name;
	private RedisTemplate redisTemplate;
	private List<Pair<String, String>> keyPairList = new ArrayList<>();
	private List<Triplet<String, String, String>> keyTripletList = new ArrayList<>();

	public RedisTemplateRollback(String name) {
		LOGGER.debug("생성자 RedisTemplateRollback()");
		this.name = name;
	}

	@Autowired
	public void setJedisPool(redis.clients.jedis.JedisPool jedisPool) {
		redisTemplate = new RedisTemplate(name, jedisPool);
	}

	@Override
	public void deleteKeyList() {
		super.deleteKeyList("DEFAULT", keyPairList, keyTripletList);
	}

	@Override
	public void set(String key, String value) {
		redisTemplate.set(key, value);
		keyPairList.add(Pair.with("SET", key));
	}

	@Override
	public String get(String key) {
		return redisTemplate.get(key);
	}

	@Override
	public void del(String key) {
		redisTemplate.del(key);
	}

	@Override
	public void zadd(String key, String member, double score) {
		redisTemplate.zadd(key, member, score);
		keyTripletList.add(Triplet.with("ZADD", key, member));
	}

	@Override
	public void zaddAndExpire(String key, String member, double score, int seconds) {
		redisTemplate.zaddAndExpire(key, member, score, seconds);
		keyTripletList.add(Triplet.with("ZADD", key, member));
	}

	@Override
	public Double zscore(String key, String member) {
		return redisTemplate.zscore(key, member);
	}

	@Override
	public Long zcount(String key, String min, String max) {
		return redisTemplate.zcount(key, min, max);
	}

	@Override
	public Set<Tuple> zrevrangeWithScores(String key, long start, long end) {
		return redisTemplate.zrevrangeWithScores(key, start, end);
	}

	@Override
	public void zrem(String key, String... members) {
		redisTemplate.zrem(key, members);
	}
}
