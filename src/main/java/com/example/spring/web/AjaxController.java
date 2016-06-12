package com.example.spring.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.spring.logic.search.model.SearchCriteria;
import com.example.spring.logic.search.model.SearchCriteriaResponse;
import com.example.spring.logic.user.model.User;

/**
 * @author gimbyeongsu
 * 
 */
@Controller
@RequestMapping("/ajax")
public class AjaxController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AjaxController.class);

	private final List<User> users = new ArrayList<User>();

	public AjaxController() {
		LOGGER.debug("생성자 AccountController()");
	}

	@PostConstruct
	private void init() {
		User user1 = new User("kimbs", "123456", "kimbs0301@gamil.com", "123-1234-1234", "address 123");
		users.add(user1);
	}
	
	@RequestMapping(value = "/searchCriteria", method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {
		return "ajax";
	}

	@RequestMapping(value = "/searchCriteria", method = RequestMethod.POST)
	public @ResponseBody SearchCriteriaResponse getSearchCriteria(@RequestBody SearchCriteria search) {
		SearchCriteriaResponse result = new SearchCriteriaResponse();

		if (isValidSearchCriteria(search)) {
			List<User> users = findByUserNameOrEmail(search.getUsername(), search.getEmail());
			if (users.size() > 0) {
				result.setCode("200");
				result.setMsg("");
				result.setResult(users);
			} else {
				result.setCode("204");
				result.setMsg("No user!");
			}
		} else {
			result.setCode("400");
			result.setMsg("Search criteria is empty!");
		}
		return result;
	}

	private boolean isValidSearchCriteria(SearchCriteria search) {
		boolean valid = true;
		if (search == null) {
			valid = false;
		}
		if ((StringUtils.isEmpty(search.getUsername())) && (StringUtils.isEmpty(search.getEmail()))) {
			valid = false;
		}
		return valid;
	}

	private List<User> findByUserNameOrEmail(String username, String email) {
		List<User> result = new ArrayList<User>();

		for (User user : users) {
			if ((!StringUtils.isEmpty(username)) && (!StringUtils.isEmpty(email))) {
				if (username.equals(user.getUsername()) && email.equals(user.getEmail())) {
					result.add(user);
					continue;
				} else {
					continue;
				}
			}
			if (!StringUtils.isEmpty(username)) {
				if (username.equals(user.getUsername())) {
					result.add(user);
					continue;
				}
			}
			if (!StringUtils.isEmpty(email)) {
				if (email.equals(user.getEmail())) {
					result.add(user);
					continue;
				}
			}
		}
		return result;
	}
}
