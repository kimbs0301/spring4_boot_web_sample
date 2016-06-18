package com.example.spring.config.model;

/**
 * @author gimbyeongsu
 * 
 */
public class ExceptionJson {
	private String url;
	private Throwable throwable;
	
	public ExceptionJson(String url, Throwable throwable) {
		this.url = url;
		this.throwable = throwable;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Throwable getThrowable() {
		return throwable;
	}

	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}
}