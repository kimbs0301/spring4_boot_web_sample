package com.example.spring.logic.search.model;

import java.util.List;

import com.example.spring.logic.user.model.User;

/**
 * @author gimbyeongsu
 * 
 */
public class SearchCriteriaResponse {
	private String msg;
	private String code;
	private List<User> result;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<User> getResult() {
		return result;
	}

	public void setResult(List<User> result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "SearchCriteriaResponse [msg=" + msg + ", code=" + code + ", result=" + result + "]";
	}
}
