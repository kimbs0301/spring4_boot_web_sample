package com.example.spring.logic.account.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.spring.logic.account.service.AccountService;

/**
 * @author gimbyeongsu
 * 
 */
@Service
public class AccountServiceImpl implements AccountService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);
	
	public AccountServiceImpl() {
		LOGGER.debug("생성자 AccountServiceImpl()");
	}
	
	@Override
	public void test() {
		LOGGER.debug("");
	}
}
