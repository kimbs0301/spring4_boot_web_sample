package com.example.spring.logic.member.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.spring.logic.account.dao.AccountDao;
import com.example.spring.logic.account.dao.AccountLogDao;
import com.example.spring.logic.account.model.Account;
import com.example.spring.logic.account.model.AccountLog;
import com.example.spring.logic.member.dao.MemberDao;
import com.example.spring.logic.member.dao.MemberLogDao;
import com.example.spring.logic.member.model.Member;
import com.example.spring.logic.member.model.MemberLog;
import com.example.spring.logic.member.service.MemberService;

/**
 * @author gimbyeongsu
 * 
 */
@Service
public class MemberServiceImpl implements MemberService {
	private static final Logger LOGGER = LoggerFactory.getLogger(MemberServiceImpl.class);

	@Autowired
	private AccountDao accountDao;
	@Autowired
	private AccountLogDao accountLogDao;
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private MemberLogDao memberLogDao;

	public MemberServiceImpl() {
		LOGGER.debug("생성자 MemberServiceImpl()");
	}

	@Override
	@Transactional
	public void test() {
		LOGGER.debug("");

		Account account = new Account();
		account.setId(12);
		account.setAmount(100);
		accountDao.insert(account);

		AccountLog accountLog = new AccountLog();
		accountLog.setText("BB");
		accountLogDao.insert(accountLog);

		Member member0 = new Member();
		member0.setId(0);
		member0.setName("name0");
		memberDao.insert(member0);

		Member member1 = new Member();
		member1.setId(1);
		member1.setName("name1");
		memberDao.insert(member1);
	}

	@Override
	@Transactional
	public void register(Member member) {
		LOGGER.debug("");

		memberDao.insert(member);

		Account account = new Account();
		account.setId(member.getId());
		account.setAmount(100);
		accountDao.insert(account);

		AccountLog accountLog = new AccountLog();
		accountLog.setText("BB");
		accountLogDao.insert(accountLog);
	}

	@Override
	@Transactional
	public void insert(MemberLog memberLog) {
		memberLogDao.insert(memberLog);
	}

	@Override
	@Transactional
	public void insert(List<MemberLog> memberLogs) {
		memberLogDao.insert(memberLogs);
	}
}
