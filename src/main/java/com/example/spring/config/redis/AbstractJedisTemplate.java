package com.example.spring.config.redis;

import java.util.List;
import java.util.Set;

import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Tuple;

/**
 * @author gimbyeongsu
 * 
 */
public abstract class AbstractJedisTemplate {
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractJedisTemplate.class);

	public abstract void set(String key, String value);

	public abstract String get(String key);

	public abstract void del(String key);

	public abstract void zadd(String key, String member, double score);

	public abstract void zaddAndExpire(String key, String member, double score, int seconds);
	
	public abstract Double zscore(String key, String member);
	
	public abstract Long zcount(String key, String min, String max);
	
	public abstract Set<Tuple> zrevrangeWithScores(String key, long start, long end);

	public abstract void zrem(String key, String... members);

	public void deleteKeyList(String name, List<Pair<String, String>> keyPairList,
			List<Triplet<String, String, String>> keyTripletList) {
		for (Pair<String, String> pair : keyPairList) {
			String type = pair.getValue0();
			String key = pair.getValue1();
			if ("SET".equals(type)) {
				del(key);
			} else {
				LOGGER.error("[{}] 알수 없는 키:{}", name, type);
			}
			LOGGER.debug("[{}] {} {}", name, type, key);
		}
		for (Triplet<String, String, String> triplet : keyTripletList) {
			String type = triplet.getValue0();
			String key = triplet.getValue1();
			String member = triplet.getValue2();
			if ("ZADD".equals(type)) {
				zrem(key, member);
			} else {
				LOGGER.error("[{}] 알수 없는 키:{}", name, type);
			}
			LOGGER.debug("[{}] {} {}", name, type, key);
		}
		keyPairList.clear();
		keyTripletList.clear();
	}
}
