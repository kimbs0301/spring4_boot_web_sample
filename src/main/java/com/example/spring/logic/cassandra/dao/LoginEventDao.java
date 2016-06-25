package com.example.spring.logic.cassandra.dao;

import java.util.List;

import com.example.spring.logic.cassandra.model.LoginEvent;
import com.example.spring.logic.cassandra.model.LoginEventKey;

/**
 * @author gimbyeongsu
 * 
 */
public interface LoginEventDao {
	public void insert(LoginEvent loginEvent);
	
	public LoginEvent get(LoginEventKey loginEventKey);
	
	public List<LoginEvent> selectIn(String cql);
}
