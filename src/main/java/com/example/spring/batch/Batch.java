package com.example.spring.batch;

/**
 * @author gimbyeongsu
 * 
 */
public interface Batch extends Runnable {
	public String getName();

	public String getExpression();

	public boolean isInProgress();

	public void shutdownEnabled();
	
	public boolean isEnabled();
}
