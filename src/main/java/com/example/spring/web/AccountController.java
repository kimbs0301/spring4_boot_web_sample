package com.example.spring.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring.config.aop.ParamAop;
import com.example.spring.logic.account.model.Account;
import com.example.spring.logic.account.service.AccountService;
import com.example.spring.logic.member.model.Member;

/**
 * @author gimbyeongsu
 * 
 */
@RestController
@RequestMapping("/account")
public class AccountController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	private AccountService accountService;

	public AccountController() {
		LOGGER.debug("생성자 AccountController()");
	}

	/**
	 * curl -v -H "Accept: application/json" "http://localhost:8080/mvc/account/member/11.json"
	 * 
	 * @param memberId
	 * @return
	 */
	@RequestMapping(value = "/member/{memberId}", method = RequestMethod.GET)
	public @ResponseBody Account getAccount(@PathVariable int memberId, @ParamAop Member member) {
		member.setId(memberId);
		LOGGER.debug("{}", member);
		
		Account account = new Account();
		account.setId(1);
		account.setAmount(99);
		
		accountService.test();
		
		return account;
	}
}
