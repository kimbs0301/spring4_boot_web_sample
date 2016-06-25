package com.example.spring.logic.cassandra.model;

import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

/**
 * @author gimbyeongsu
 * 
 */
@Table(value = "login_event")
public class LoginEvent {
	@PrimaryKey
	private LoginEventKey pk;
	@Column(value = "event_code")
	private int eventCode;
	@Column(value = "ip_address")
	private String ipAddress;

	public LoginEventKey getPk() {
		return pk;
	}

	public void setPk(LoginEventKey pk) {
		this.pk = pk;
	}

	public int getEventCode() {
		return eventCode;
	}

	public void setEventCode(int eventCode) {
		this.eventCode = eventCode;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	@Override
	public String toString() {
		return "LoginEvent [pk=" + pk + ", eventCode=" + eventCode + ", ipAddress=" + ipAddress + "]";
	}
}