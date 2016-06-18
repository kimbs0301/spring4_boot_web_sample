package com.example.spring.config;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;

import com.example.spring.common.model.Header;
import com.example.spring.config.model.ExceptionJson;
import com.example.spring.config.model.ExceptionXml;
import com.example.spring.logic.util.MimeUtils;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;

/**
 * <pre>
 * https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc
 * </pre>
 * 
 * @author gimbyeongsu
 */
@Profile({ "local", "svc" })
@ControllerAdvice
class GlobalDefaultExceptionHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);

	// @Autowired
	// private List<AbstractView> views;
	@Autowired
	private AbstractView jsonView;
	@Autowired
	private AbstractView xmlStringView;
	@Autowired
	private AbstractView errorJspView;

	public GlobalDefaultExceptionHandler() {
		LOGGER.debug("생성자 GlobalDefaultExceptionHandler()");
	}

	@ExceptionHandler(value = Exception.class)
	public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
		LOGGER.error("{}", e.getMessage());
		if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
			LOGGER.debug("");
			// throw e;
		}

		LOGGER.debug("{}", req.getHeader("accept"));
		String accept = req.getHeader("accept");
		if (!Strings.isNullOrEmpty(accept)) {
			if (MimeUtils.getMimeSet(accept).contains("application/json")) {
				return jsonModelAndView(req, e);
			} else if (MimeUtils.getMimeSet(accept).contains("application/xml")) {
				return xmlModelAndView(req, e);
			}
			return defaultModelAndView(req, e);
		}
		return defaultModelAndView(req, e);
	}

	private ModelAndView jsonModelAndView(HttpServletRequest req, Exception e) {
		ModelAndView mav = new ModelAndView(jsonView);
		mav.addObject("header", new Header("UNKNOWN", ""));
		ExceptionJson body = new ExceptionJson(req.getRequestURL().toString(), e);
		mav.addObject("body", body);
		return mav;
	}

	private ModelAndView xmlModelAndView(HttpServletRequest req, Exception e) {
		ExceptionXml xml = new ExceptionXml();
		xml.setUrl(req.getRequestURL().toString());
		xml.setStackTrace(Throwables.getStackTraceAsString(e));
		ModelAndView mav = new ModelAndView(xmlStringView);
		mav.addObject("xml", xml);
		return mav;
	}

	private ModelAndView defaultModelAndView(HttpServletRequest req, Exception e) {
		ModelAndView mav = new ModelAndView(errorJspView);
		mav.addObject("url", req.getRequestURL().toString());
		mav.addObject("stackTrace", Throwables.getStackTraceAsString(e));
		return mav;
	}

	// @PreDestroy
	// public void destroy() {
	// for (AbstractView each : views) {
	// LOGGER.debug("{} {}", each, each.getContentType());
	// }
	// }
}