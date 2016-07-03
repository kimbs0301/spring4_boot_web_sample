package com.example.spring.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.spring.logic.common.exception.Exceptions;
import com.example.spring.logic.common.service.CommonService;

/**
 * @author gimbyeongsu
 * 
 */
@Controller
public class CommonController {
	private static final Logger LOGGER = LoggerFactory.getLogger(CommonController.class);

	@Autowired
	private CommonService commonService;

	public CommonController() {
		LOGGER.debug("생성자 CommonController()");
	}

	/**
	 * curl -v "http://localhost:8080/mvc/"
	 * 
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index() {
		return "index";
	}

	/**
	 * curl -v "http://localhost:8080/mvc/inMemoryCacheRefresh/all"
	 * 
	 * @return
	 */
	@RequestMapping(value = "/inMemoryCacheRefresh/{cacheName}", method = RequestMethod.GET)
	public ResponseEntity<Void> inMemoryCacheRefresh(@PathVariable String cacheName) {
		commonService.pushInMemoryCacheRefresh(cacheName);

		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<Void>(headers, HttpStatus.NO_CONTENT);
	}

	/**
	 * curl -v "http://localhost:8080/mvc/shutdown"
	 * 
	 * @return
	 */
	@RequestMapping(value = "/shutdown", method = RequestMethod.GET)
	public ResponseEntity<Void> shutdown(UriComponentsBuilder ucBuilder) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
				LOGGER.info("");
				LOGGER.info("");
				LOGGER.info("shutdown start");
				System.exit(0);
			}
		}).start();
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<Void>(headers, HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/error/{errorPage}", method = RequestMethod.GET)
	public String error(@PathVariable String errorPage) {
		LOGGER.error("{}", errorPage);
		return new StringBuilder().append("/error/").append(errorPage).toString();
	}

	/**
	 * curl -v "http://localhost:8080/mvc/test/error"
	 * 
	 * @return
	 */
	@RequestMapping(value = "/test/error", method = RequestMethod.GET)
	public String testError() {
		"".substring(0, 10); // exception

		return "index";
	}

	/**
	 * curl -v "http://localhost:8080/mvc/test/common/error"
	 * 
	 * @return
	 */
	@RequestMapping(value = "/test/common/error", method = RequestMethod.GET)
	public String testCommonError() {
		try {
			"".substring(0, 10); // exception
		} catch (Exception e) {
			throw Exceptions.UNKNOWN_LOGIC;
		}

		return "index";
	}
}
