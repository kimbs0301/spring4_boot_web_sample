package com.example.spring.logic.book.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.junit.JunitSpringAnnotation;
import com.example.spring.logic.book.service.BookService;

/**
 * @author gimbyeongsu
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@JunitSpringAnnotation
@Transactional
public class BookServiceImplTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(BookServiceImplTest.class);

	@Autowired
	private BookService bookService;

	@Test
	public void test() throws Exception {
		LOGGER.debug("");
		bookService.test();
	}
}
