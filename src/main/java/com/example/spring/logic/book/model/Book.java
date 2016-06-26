package com.example.spring.logic.book.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author gimbyeongsu
 * 
 */
public class Book implements Serializable {
	private static final long serialVersionUID = -3372851669420964354L;

	private int id;
	private String name;
	private Date updDate;
	private Date crtDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getUpdDate() {
		return updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	public Date getCrtDate() {
		return crtDate;
	}

	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}
}