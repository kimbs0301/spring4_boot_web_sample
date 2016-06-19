package com.example.spring.logic.rank.service;

import java.util.List;

import com.example.spring.logic.rank.model.UserRank;

/**
 * @author gimbyeongsu
 * 
 */
public interface RankService {
	public void setScore(String member, double score);
	
	public int getRank(String member);
	
	public List<UserRank> getTopRank(int start, int end);
}
