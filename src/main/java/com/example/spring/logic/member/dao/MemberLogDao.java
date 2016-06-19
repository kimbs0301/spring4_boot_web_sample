package com.example.spring.logic.member.dao;

import java.util.List;

import com.example.spring.logic.member.model.MemberLog;

/**
 * @author gimbyeongsu
 * 
 */
public interface MemberLogDao {
	public void insert(MemberLog memberLog);

	public void insert(List<MemberLog> memberLogs);
}
