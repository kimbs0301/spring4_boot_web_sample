package com.example.spring.config.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * @author gimbyeongsu
 *
 */
@JacksonXmlRootElement(localName = "exception")
public class ExceptionXml {
	@JacksonXmlCData
	private String url;
	@JacksonXmlCData
	private String stackTrace;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getStackTrace() {
		return stackTrace;
	}

	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}
}