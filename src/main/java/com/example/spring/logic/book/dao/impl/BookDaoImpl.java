package com.example.spring.logic.book.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.spring.logic.book.dao.BookDao;
import com.example.spring.logic.book.model.Book;

/**
 * @author gimbyeongsu
 * 
 */
@Repository
public class BookDaoImpl implements BookDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(BookDaoImpl.class);

	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("dataSourceData")
	public void set(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	@CacheEvict(value = "simpleCache", key = "#id")
	public void insert(Book param) {
		LOGGER.debug("");
		Object[] args = new Object[] { param.getId(), param.getName() };
		int[] types = new int[] { Types.INTEGER, Types.VARCHAR };
		jdbcTemplate.update(
				"INSERT INTO book(id,name,upd_date,crt_date) VALUES(?,?,current_timestamp,current_timestamp)", args,
				types);
	}

	@Override
	@CacheEvict(value = "simpleCache", key = "#id")
	public void update(Book param) {
		LOGGER.debug("");
		Object[] args = new Object[] { param.getName(), param.getId() };
		int[] types = new int[] { Types.VARCHAR, Types.INTEGER };
		jdbcTemplate.update("UPDATE book SET name=?, upd_date=current_timestamp WHERE id=?", args, types);
	}

	@Override
	@Cacheable(value = "simpleCache", key = "#id")
	public Book get(int id) {
		LOGGER.debug("");
		Object[] args = new Object[] { id };
		int[] types = new int[] { Types.INTEGER };

		try {
			return jdbcTemplate.queryForObject("SELECT * FROM book WHERE id=?", args, types, new RowMapper<Book>() {

				@Override
				public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
					Book book = new Book();
					book.setId(rs.getInt("id"));
					book.setName(rs.getString("name"));
					book.setUpdDate(rs.getDate("upd_date"));
					book.setCrtDate(rs.getDate("crt_date"));
					return book;
				}
			});
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
}
