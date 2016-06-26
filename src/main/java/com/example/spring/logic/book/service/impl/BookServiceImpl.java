package com.example.spring.logic.book.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.spring.logic.book.dao.BookDao;
import com.example.spring.logic.book.model.Book;
import com.example.spring.logic.book.service.BookService;

/**
 * @author gimbyeongsu
 * 
 */
@Service
public class BookServiceImpl implements BookService {
	private static final Logger LOGGER = LoggerFactory.getLogger(BookServiceImpl.class);

	@Autowired
	private BookDao bookDao;

	public BookServiceImpl() {
		LOGGER.debug("생성자 BookServiceImpl()");
	}

	@Override
	public void test() {
		Book book = new Book();
		book.setId(100);
		book.setName("KKK");
		bookDao.insert(book);

		book.setName("테스트");
		bookDao.update(book);

		LOGGER.debug("");
		bookDao.get(100);
		LOGGER.debug("");
		bookDao.get(100);
		LOGGER.debug("");
		bookDao.get(100);
		LOGGER.debug("");
	}
}
