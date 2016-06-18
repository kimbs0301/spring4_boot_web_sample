package com.example.spring.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring.logic.member.model.Member;
import com.example.spring.logic.member.service.MemberService;

/**
 * @author gimbyeongsu
 * 
 */
@RestController
@RequestMapping("/member")
public class MemberController {
	private static final Logger LOGGER = LoggerFactory.getLogger(MemberController.class);

	@Autowired
	private MemberService memberService;

	public MemberController() {
		LOGGER.debug("생성자 MemberController()");
	}

	/**
	 * curl -v -X POST -H "Content-Type:application/json; charset=utf-8" -H "Accept: application/json" -d '{"id":111,"name":"KKK"}' "http://localhost:8080/mvc/member/register.json"
	 * 
	 * @param member
	 * @return
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public @ResponseBody Member register(@RequestBody Member member) {
		memberService.register(member);
		return member;
	}
}
