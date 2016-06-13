package com.example.app.embedded;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.valves.AccessLogValve;
import org.apache.coyote.http11.Http11Nio2Protocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration.EmbeddedServletContainerCustomizerBeanPostProcessorRegistrar;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.MimeMappings;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;

/**
 * @author gimbyeongsu
 * 
 */
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@Configuration
@Import(EmbeddedServletContainerCustomizerBeanPostProcessorRegistrar.class)
public class EmbeddedTomcatConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(EmbeddedTomcatConfig.class);

	@Autowired
	private Environment environment;

	public EmbeddedTomcatConfig() {
		LOGGER.debug("생성자 EmbeddedTomcatConfig()");
	}

	@Bean
	public EmbeddedServletContainerFactory servletContainer() {
		int port = environment.getRequiredProperty("server.port", Integer.class);
		String contextPath = environment.getRequiredProperty("context.path");
		TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory(contextPath, port);

		factory.setBaseProtocol("org.apache.coyote.http11.Http11Nio2Protocol");
		MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);
		mappings.add("html", "text/html;charset=UTF-8");
		mappings.add("file", "multipart/form-data");
		mappings.add("json", "application/json;charset=UTF-8");
		mappings.add("xml", "application/xml;charset=UTF-8");
		factory.setMimeMappings(mappings);
		factory.setSessionTimeout(10, TimeUnit.MINUTES);
		factory.setUriEncoding(Charset.forName("UTF-8"));

		AccessLogValve accessLogValve = new AccessLogValve();
		accessLogValve.setDirectory("/temp");
		// '%h %l %u %t "%r" %s %b %D'
		accessLogValve.setPattern("%{yyyy-MM-dd HH:mm:ss}t\t%a\t%r\t%{Referer}i\t%{User-Agent}i\t%D\t%I");
		accessLogValve.setSuffix(".log");
		factory.addContextValves(accessLogValve);

		factory.addContextCustomizers(new TomcatContextCustomizer() {

			@Override
			public void customize(Context context) {
				// https://tomcat.apache.org/tomcat-8.0-doc/config/context.html
				context.setCookies(true);
				context.setSessionCookieName("SID");
				context.setSessionTimeout(10);
			}
		});

		factory.addContextLifecycleListeners(new LifecycleListener() {

			@Override
			public void lifecycleEvent(LifecycleEvent event) {

			}
		});

		factory.addConnectorCustomizers(new TomcatConnectorCustomizer() {

			@Override
			public void customize(Connector connector) {
				connector.setEnableLookups(false);
				connector.setURIEncoding("UTF-8");
				connector.setXpoweredBy(false);
				
				connector.setProperty("maxProcessors", "150");
				connector.setProperty("maxThreads", "150");
				connector.setProperty("minSpareThreads", "150");
				connector.setProperty("maxSpareThreads", "150");
				connector.setProperty("socket.directBuffer", "true");
				connector.setProperty("socket.rxBufSize", "25188");
				connector.setProperty("socket.txBufSize", "43800");
				connector.setProperty("socket.appReadBufSize", "32768");
				connector.setProperty("socket.appWriteBufSize", "32768");
				connector.setProperty("socket.bufferPool", "500");
				connector.setProperty("socket.bufferPoolSize", "100000000");
				connector.setProperty("socket.processorCache", "500");
				connector.setProperty("socket.keyCache", "500");
				connector.setProperty("socket.eventCache", "500");
				connector.setProperty("socket.tcpNoDelay", "true");
				connector.setProperty("socket.soKeepAlive", "false");
				connector.setProperty("acceptCount", "30");
				connector.setProperty("connectionTimeout", "3000");
				connector.setProperty("connectionLinger", "0");
				connector.setProperty("socket.soTimeout", "5000");
				connector.setProperty("useComet", "false");
				connector.setProperty("compression", "on");
				connector.setProperty("compressionMinSize", "2048");
				connector.setProperty("compressableMimeType", "text/html,text/xml,text/plain,application/json");
			}
		});

		List<ServletContextInitializer> servletContextInitializers = new ArrayList<>();
		servletContextInitializers.add(new EmbeddedTomcatWebInitalizer());
		factory.setInitializers(servletContextInitializers);

		factory.addAdditionalTomcatConnectors(createSslConnector());
		
		LOGGER.error("");
		
		return factory;
	}
	
	private Connector createSslConnector() {
		Connector connector = new Connector("org.apache.coyote.http11.Http11Nio2Protocol");
		connector.setURIEncoding("UTF-8");
		Http11Nio2Protocol protocol = (Http11Nio2Protocol) connector.getProtocolHandler();
		try {
			File keystore = new ClassPathResource("ssl/tomcat.jks").getFile();
			File truststore = new ClassPathResource("ssl/truststore.jks").getFile();
			connector.setScheme("https");
			connector.setSecure(true);
			connector.setPort(8443);
			connector.setEnableLookups(false);
			
			connector.setProperty("maxProcessors", "10");
			connector.setProperty("maxThreads", "10");
			connector.setProperty("minSpareThreads", "10");
			connector.setProperty("maxSpareThreads", "10");
			connector.setProperty("socket.directBuffer", "true");
			connector.setProperty("socket.rxBufSize", "25188");
			connector.setProperty("socket.txBufSize", "43800");
			connector.setProperty("socket.appReadBufSize", "32768");
			connector.setProperty("socket.appWriteBufSize", "32768");
			connector.setProperty("socket.bufferPool", "50");
			connector.setProperty("socket.bufferPoolSize", "10000000");
			connector.setProperty("socket.processorCache", "500");
			connector.setProperty("socket.keyCache", "500");
			connector.setProperty("socket.eventCache", "500");
			connector.setProperty("socket.tcpNoDelay", "true");
			connector.setProperty("socket.soKeepAlive", "false");
			connector.setProperty("acceptCount", "10");
			connector.setProperty("connectionTimeout", "3000");
			connector.setProperty("connectionLinger", "0");
			connector.setProperty("socket.soTimeout", "5000");
			connector.setProperty("useComet", "false");
			
			protocol.setSSLEnabled(true);
			protocol.setKeystoreFile(keystore.getAbsolutePath());
			protocol.setKeystorePass("123456");
			protocol.setTruststoreFile(truststore.getAbsolutePath());
			protocol.setTruststorePass("123456");
			protocol.setClientAuth("false");
			
			protocol.setKeyAlias("tomcat_server");
			protocol.setSslProtocol("TLS");
			return connector;
		} catch (IOException ex) {
			throw new IllegalStateException("can't access keystore: ["
					+ "keystore" + "] or truststore: [" + "keystore" + "]", ex);
		}
	}
}