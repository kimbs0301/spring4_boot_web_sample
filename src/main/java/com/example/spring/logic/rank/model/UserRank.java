package com.example.spring.logic.rank.model;

/**
 * @author gimbyeongsu
 * 
 */
public class UserRank implements Rankable {
	private String id;
	private int rank;
	private int point;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getRank() {
		return rank;
	}

	@Override
	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	@Override
	public int getRankPoint() {
		return point;
	}
}