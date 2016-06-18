package com.example.spring.common.model;

/**
 * @author gimbyeongsu
 * 
 */
public class Header {
	private String errorCode;
	private String errorMsg;

	public Header() {

	}

	public Header(String errorCode) {
		this.errorCode = errorCode;
	}

	public Header(String errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}