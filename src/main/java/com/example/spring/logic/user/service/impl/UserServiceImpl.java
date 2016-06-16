package com.example.spring.logic.user.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.spring.logic.account.dao.AccountDao;
import com.example.spring.logic.account.dao.AccountLogDao;
import com.example.spring.logic.member.dao.MemberDao;
import com.example.spring.logic.user.dao.UserDao;
import com.example.spring.logic.user.model.User;
import com.example.spring.logic.user.service.UserService;

/**
 * @author gimbyeongsu
 * 
 */
@Service
public class UserServiceImpl implements UserService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private AccountLogDao accountLogDao;
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private UserDao userDao;
	
	@Override
	@Transactional
	public void test() {
		LOGGER.debug("");
		User user = new User();
		user.setId(0);
		user.setUsername("A");
		user.setPassword("B");
		user.setEmail("T@T");
		user.setPhone("123");
		user.setAddress("ADDR");
		userDao.insert(user);
	}
}
