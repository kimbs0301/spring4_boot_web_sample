package com.example.spring.logic.member.model;

import java.util.Date;

/**
 * @author gimbyeongsu
 * 
 */
public class MemberLog {
	private boolean isRollback;
	private long logId;
	private int id;
	private Date crtDate;

	public boolean isRollback() {
		return isRollback;
	}

	public void setRollback(boolean isRollback) {
		this.isRollback = isRollback;
	}

	public long getLogId() {
		return logId;
	}

	public void setLogId(long logId) {
		this.logId = logId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCrtDate() {
		return crtDate;
	}

	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}
}