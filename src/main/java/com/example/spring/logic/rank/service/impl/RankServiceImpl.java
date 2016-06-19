package com.example.spring.logic.rank.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Tuple;

import com.example.spring.config.redis.AbstractJedisTemplate;
import com.example.spring.logic.rank.model.Rankable;
import com.example.spring.logic.rank.model.UserRank;
import com.example.spring.logic.rank.service.RankService;

/**
 * @author gimbyeongsu
 * 
 */
@Service
public class RankServiceImpl implements RankService {
	private static final Logger LOGGER = LoggerFactory.getLogger(RankServiceImpl.class);

	private static final String KEY = "rank:simple";

	@Qualifier("redisTemplate")
	@Autowired
	private AbstractJedisTemplate redisTemplate;

	@Override
	public void setScore(String member, double score) {
		redisTemplate.zadd(KEY, member, score);
	}

	@Override
	public int getRank(String member) {
		int rank = Integer.MAX_VALUE;
		Double score = redisTemplate.zscore(KEY, member);
		if (score != null) {
			int point = score.intValue();
			Long count = redisTemplate.zcount(KEY, new StringBuilder("(").append(point).toString(), "+inf");
			rank = count == null ? 0 : count.intValue() + 1;
		}
		return rank;
	}

	@Override
	public List<UserRank> getTopRank(int start, int end) {
		List<UserRank> result = new ArrayList<>();
		Set<Tuple> topScores = redisTemplate.zrevrangeWithScores(KEY, start, end);
		for (Tuple each : topScores) {
			UserRank rank = new UserRank();
			result.add(rank);

			rank.setId(each.getElement());
			rank.setPoint((int) each.getScore());
		}
		for (UserRank each : result) {
			LOGGER.debug("id:{} rank:{} point:{}", each.getId(), each.getRank(), each.getPoint());
		}
		calculateRank(result, 1);
		return result;
	}

	private void calculateRank(List<? extends Rankable> sortedRanks, int initRank) {
		int rank = initRank;
		int prevPoint = -1;
		int tieCnt = 0;

		for (Rankable each : sortedRanks) {
			if (tieCnt != 0) {
				if (each.getRankPoint() < prevPoint) {
					rank += tieCnt + 1;
					each.setRank(rank);
					tieCnt = 0;
				}
			} else {
				if (prevPoint == -1) {
					each.setRank(rank);
				} else {
					if (each.getRankPoint() < prevPoint) {
						each.setRank(++rank);
					}
				}
			}

			if (each.getRankPoint() == prevPoint) {
				++tieCnt;
				each.setRank(rank);
			}

			prevPoint = each.getRankPoint();
		}
	}

}
