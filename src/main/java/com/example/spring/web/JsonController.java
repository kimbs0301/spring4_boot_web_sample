package com.example.spring.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author gimbyeongsu
 * 
 */
@Controller
@RequestMapping("/json")
public class JsonController {

	@RequestMapping(value = "/error", method = RequestMethod.GET, produces = { "application/json" })
	public String goError() {
		String s = "";
		s.substring(0, 10); // exception
		
		return "error";
	}
}
