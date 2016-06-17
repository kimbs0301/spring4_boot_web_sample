package com.example.spring.config;

import java.util.List;

import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;

import com.google.common.base.Throwables;

/**
 * @author gimbyeongsu
 *
 *         https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc
 */
@ControllerAdvice
class GlobalDefaultExceptionHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);

	@Autowired
	private List<AbstractView> views;
	@Autowired
	private AbstractView errorJspView;

	@ExceptionHandler(value = Exception.class)
	public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
		LOGGER.error("{}", e.getMessage());
		// if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
		// LOGGER.error("");
		// throw e;
		// }

		LOGGER.error("{}", req.getHeader("accept"));
		ModelAndView mav = new ModelAndView();
		mav.addObject("url", req.getRequestURL());
		mav.addObject("message", e.getMessage());
		mav.addObject("stackTraceAsString", Throwables.getStackTraceAsString(e));
		mav.setView(errorJspView);
		return mav;
	}

	@PreDestroy
	public void destroy() {
		for (AbstractView each : views) {
			LOGGER.debug("{} {}", each, each.getContentType());
		}
	}
}