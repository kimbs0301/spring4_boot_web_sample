package com.example.spring.logic.common.service;

/**
 * @author gimbyeongsu
 * 
 */
public interface CommonService {
	public void startInMemoryRefresh(String cacheName);

	public void inMemoryRefresh(String cacheName);
}
