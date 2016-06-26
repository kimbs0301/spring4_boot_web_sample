package com.example.spring.logic.book.dao;

import com.example.spring.logic.book.model.Book;

/**
 * @author gimbyeongsu
 * 
 */
public interface BookDao {
	public void insert(Book param);
	
	public void update(Book param);

	public Book get(int id);
}
