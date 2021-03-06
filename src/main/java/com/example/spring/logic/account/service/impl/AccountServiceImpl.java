package com.example.spring.logic.account.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.spring.logic.account.dao.AccountDao;
import com.example.spring.logic.account.dao.AccountLogDao;
import com.example.spring.logic.account.model.Account;
import com.example.spring.logic.account.model.AccountLog;
import com.example.spring.logic.account.service.AccountService;

/**
 * @author gimbyeongsu
 * 
 */
@Service
public class AccountServiceImpl implements AccountService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

	@Autowired
	private AccountDao accountDao;
	@Autowired
	private AccountLogDao accountLogDao;

	public AccountServiceImpl() {
		LOGGER.debug("생성자 AccountServiceImpl()");
	}

	@Override
	public void test() {
		LOGGER.debug("");
	}

	@Override
	@Transactional
	public void test2() {
		Account account = new Account();
		account.setId(11);
		account.setAmount(99);
		accountDao.insert(account);
		
		AccountLog accountLog = new AccountLog();
		accountLog.setText("AA");
		accountLogDao.insert(accountLog);
	}
}
