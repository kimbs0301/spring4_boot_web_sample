package com.example.spring.logic.common.service;

/**
 * @author gimbyeongsu
 * 
 */
public interface CommonService {
	public void pushInMemoryCacheRefresh(String cacheName);

	public void inMemoryCacheRefresh(String cacheName);
}
