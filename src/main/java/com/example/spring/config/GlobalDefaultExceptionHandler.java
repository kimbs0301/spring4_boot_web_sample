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
@ControllerAdvice
class GlobalDefaultExceptionHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);

	@Autowired
	private List<AbstractView> views;
	@Autowired
	private AbstractView jsonView;
	@Autowired
	private AbstractView xmlStringView;
	@Autowired
	private AbstractView errorJspView;

	@ExceptionHandler(value = Exception.class)
	public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
		LOGGER.error("{}", e.getMessage());
		// if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
		// LOGGER.error("");
		// throw e;
		// }

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
		mav.addObject("url", req.getRequestURL());
		mav.addObject("stackTrace", e);
		return mav;
	}

	// private ModelAndView xmlModelAndView(HttpServletRequest req, Exception e) {
	// ModelMap modelMap = new ModelMap();
	// XmlModel data = new XmlModel();
	// data.setReqUrl(req.getRequestURL().toString());
	// data.setStackTrace(Throwables.getStackTraceAsString(e));
	// modelMap.put("", data);
	// return new ModelAndView(xmlView, modelMap);
	// }

	private ModelAndView xmlModelAndView(HttpServletRequest req, Exception e) {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<data>aaa</data>");
		ModelAndView mav = new ModelAndView(xmlStringView);
		mav.addObject(sb.toString());
		mav.addObject("xml", sb.toString());
		return mav;
	}

	private ModelAndView defaultModelAndView(HttpServletRequest req, Exception e) {
		ModelAndView mav = new ModelAndView(errorJspView);
		mav.addObject("url", req.getRequestURL());
		mav.addObject("stackTrace", Throwables.getStackTraceAsString(e));
		return mav;
	}

	@PreDestroy
	public void destroy() {
		for (AbstractView each : views) {
			LOGGER.debug("{} {}", each, each.getContentType());
		}
	}
}