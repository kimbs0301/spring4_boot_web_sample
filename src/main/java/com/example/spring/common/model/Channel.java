package com.example.spring.common.model;

/**
 * @author gimbyeongsu
 * 
 */
public class Channel {
	private Header header;
	private Object body;

	public Channel() {

	}

	public Channel(Header header) {
		this.header = header;
	}

	public Channel(Header header, Object body) {
		this.header = header;
		this.body = body;
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

}
