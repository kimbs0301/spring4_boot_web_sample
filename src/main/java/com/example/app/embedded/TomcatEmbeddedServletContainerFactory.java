package com.example.app.embedded;

import java.io.File;

import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardThreadExecutor;
import org.apache.catalina.startup.Tomcat;
import org.apache.coyote.http11.Http11NioProtocol;
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
	
	public TomcatEmbeddedServletContainerFactory(String contextPath, int port) {
		super(contextPath, port);
		LOGGER.debug("생성자 TomcatEmbeddedServletContainerFactory()");
	}
	
	@Override
	public EmbeddedServletContainer getEmbeddedServletContainer(ServletContextInitializer... initializers) {
		Tomcat tomcat = new Tomcat();
		
		StandardThreadExecutor executor = new StandardThreadExecutor();
		executor.setThreadPriority(5);
		executor.setName("threadPool");
		executor.setNamePrefix("HTTP-");
		executor.setMaxThreads(10);
		executor.setMinSpareThreads(5);
		executor.setMaxQueueSize(100);
		tomcat.getService().addExecutor(executor);
		
		File baseDir = createTempDir("tomcat");
		tomcat.setBaseDir(baseDir.getAbsolutePath());
		Connector connector = new Connector(DEFAULT_PROTOCOL);
		
		Http11NioProtocol proto = (Http11NioProtocol) connector.getProtocolHandler();
		proto.setExecutor(executor);
		
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