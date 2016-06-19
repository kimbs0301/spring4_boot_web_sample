package com.example.spring.config.redis;

/**
 * @author gimbyeongsu
 * 
 */
public abstract class AbstractJedisTemplateRollback extends AbstractJedisTemplate {
	public abstract void deleteKeyList();
}
