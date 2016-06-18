package com.example.spring.logic.common.exception;

/**
 * @author gimbyeongsu
 *
 */
public class CommonException extends RuntimeException {
	private static final long serialVersionUID = -4799961894042750615L;
	private String errorCode;
	private String errorMsg;

	public CommonException(String errorCode, String errorMsg) {
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