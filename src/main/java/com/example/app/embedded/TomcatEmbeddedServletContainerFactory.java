package com.example.app.embedded;

import java.io.File;

import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.EmbeddedServletContainer;
import org.springframework.boot.context.embedded.ServletContextInitializer;

/**
 * @author gimbyeongsu
 * 
 */
public class TomcatEmbeddedServletContainerFactory extends org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory {
	private static final Logger LOGGER = LoggerFactory.getLogger(TomcatEmbeddedServletContainerFactory.class);
	
	private String baseProtocol;
	
	public TomcatEmbeddedServletContainerFactory(String contextPath, int port) {
		super(contextPath, port);
		LOGGER.debug("생성자 TomcatEmbeddedServletContainerFactory()");
	}
	
	public void setBaseProtocol(String baseProtocol) {
		this.baseProtocol = baseProtocol;
		setProtocol(baseProtocol);
	}
	
	@Override
	public EmbeddedServletContainer getEmbeddedServletContainer(ServletContextInitializer... initializers) {
		Tomcat tomcat = new Tomcat();
		File baseDir = createTempDir("tomcat");
		tomcat.setBaseDir(baseDir.getAbsolutePath());
		Connector connector = new Connector(baseProtocol);
		
		tomcat.getService().addConnector(connector);
		customizeConnector(connector);
		tomcat.setConnector(connector);
		tomcat.getHost().setAutoDeploy(false);
		tomcat.getEngine().setBackgroundProcessorDelay(-1);
		for (Connector additionalConnector : getAdditionalTomcatConnectors()) {
			tomcat.getService().addConnector(additionalConnector);
		}
		prepareContext(tomcat.getHost(), initializers);
		return getTomcatEmbeddedServletContainer(tomcat);
	}
}