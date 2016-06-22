package com.example.spring.logic.member.service;

import java.util.List;

import com.example.spring.logic.member.model.Member;
import com.example.spring.logic.member.model.MemberLog;

/**
 * @author gimbyeongsu
 * 
 */
public interface MemberService {
	public void test();
	
	public void register(Member member);
	
	public void insert(MemberLog memberLog);
	
	public void insert(List<MemberLog> memberLogs);
	
	public void insertForkJoin(List<MemberLog> memberLogs);
}
