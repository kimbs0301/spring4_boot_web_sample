package com.example.spring.web;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.spring.common.model.Channel;
import com.example.spring.common.model.Header;
import com.example.spring.logic.user.model.User;

/**
 * @author gimbyeongsu
 * 
 */
@Controller
@RequestMapping("/xml")
public class XmlController {
	private static final Logger LOGGER = LoggerFactory.getLogger(XmlController.class);

	public XmlController() {
		LOGGER.debug("생성자 XmlController()");
	}

	@RequestMapping(value = "/error", method = RequestMethod.GET, produces = { "application/xml" })
	public @ResponseBody List<User> error() {
		String s = "";
		s.substring(0, 10); // exception

		return new ArrayList<>();
	}

	@RequestMapping(value = "/data", method = RequestMethod.POST, produces = { "application/xml" })
	public @ResponseBody Channel data(@RequestBody User req) {
		Channel channel = new Channel(new Header("OK",""));
		
		List<User> users = new ArrayList<>();
		User user = new User();
		user.setId(11);
		user.setAddress("aaa");
		user.setEmail("bbb");
		user.setPassword("a");
		user.setPhone("123");
		user.setUsername("b");
		users.add(user);
		
		channel.setBody(users);
		return channel;
	}
}
