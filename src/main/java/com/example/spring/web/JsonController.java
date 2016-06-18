package com.example.spring.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.spring.common.model.Channel;
import com.example.spring.common.model.Header;
import com.example.spring.logic.common.exception.Exceptions;
import com.example.spring.logic.user.model.User;

/**
 * @author gimbyeongsu
 * 
 */
@Controller
@RequestMapping("/json")
public class JsonController {
	private static final Logger LOGGER = LoggerFactory.getLogger(JsonController.class);

	public JsonController() {
		LOGGER.debug("생성자 JsonController");
	}

	/**
	 * curl -v -H "Content-Type:application/json; charset=utf-8" -H "Accept: application/json; charset=utf-8" "http://localhost:8080/mvc/json/error.json" | python -m json.tool
	 * 
	 * @return
	 */
	@RequestMapping(value = "/error", method = RequestMethod.GET, produces = { "application/json" })
	public String error() {
		"".substring(0, 10); // exception

		return "error";
	}

	/**
	 * curl -v -H "Content-Type:application/json; charset=utf-8" -H "Accept: application/json; charset=utf-8" "http://localhost:8080/mvc/json/common/error.json" | python -m json.tool
	 * 
	 * @return
	 */
	@RequestMapping(value = "/common/error", method = RequestMethod.GET, produces = { "application/json" })
	public String commonError() {
		try {
			"".substring(0, 10); // exception
		} catch (Exception e) {
			throw Exceptions.UNKNOWN_LOGIC;
		}

		return "error";
	}

	/**
	 * curl -v -H "Content-Type:application/json; charset=utf-8" -H "Accept: application/json; charset=utf-8" -d '{"id":1,"username":"KKK"}' "http://localhost:8080/mvc/json/data.json" | python -m json.tool
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/data", method = RequestMethod.POST, produces = { "application/json" })
	public @ResponseBody Channel goData(@RequestBody User user) {
		Channel channel = new Channel(new Header("OK", ""));
		channel.setBody(user);
		return channel;
	}
}
