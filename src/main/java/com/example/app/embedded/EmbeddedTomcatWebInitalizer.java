package com.example.app.embedded;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.example.spring.config.WebMvcConfig;

/**
 * @author gimbyeongsu
 * 
 */
public class EmbeddedTomcatWebInitalizer implements ServletContextInitializer {
	private static final Logger LOGGER = LoggerFactory.getLogger(EmbeddedTomcatWebInitalizer.class);

	public EmbeddedTomcatWebInitalizer() {
		LOGGER.debug("생성자 EmbeddedTomcatWebInitalizer()");
	}

	@Override
	public void onStartup(ServletContext container) throws ServletException {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();

		context.register(WebMvcConfig.class);
		context.setServletContext(container);

		DispatcherServlet dispatcher = new DispatcherServlet(context);
		ServletRegistration.Dynamic servlet = container.addServlet("dispatcher", dispatcher);

		servlet.setLoadOnStartup(1);
		servlet.addMapping("/");
	}
}