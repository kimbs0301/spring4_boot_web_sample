package com.example.spring.config;

import java.io.Writer;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.Ordered;

import com.ctc.wstx.api.WstxInputProperties;
import com.ctc.wstx.stax.WstxOutputFactory;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;

/**
 * @author gimbyeongsu
 * 
 */
@AutoConfigureOrder(Ordered.LOWEST_PRECEDENCE)
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class RootConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(RootConfig.class);

	@Autowired
	private ObjectMapper objectMapper;

	public RootConfig() {
		LOGGER.debug("생성자 RootConfig()");
	}

	@Bean(name = "objectMapper")
	public ObjectMapper objectMapper() {
		ObjectMapper om = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return om;
	}

	@Bean(name = "xmlMapper")
	public XmlMapper xmlMapper() {
		XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
		WstxOutputFactory xmlOutputFactory = new WstxOutputFactory() {

			@Override
			public XMLStreamWriter createXMLStreamWriter(Writer w) throws XMLStreamException {
				mConfig.setProperty(WstxInputProperties.P_RETURN_NULL_FOR_DEFAULT_NAMESPACE, true);
				XMLStreamWriter result = super.createXMLStreamWriter(w);
				result.setPrefix("xlink", "http://www.w3.org/1999/xlink");
				return result;
			}
		};
		XmlFactory factory = new XmlFactory(xmlInputFactory, xmlOutputFactory);

		JacksonXmlModule module = new JacksonXmlModule();
		module.setDefaultUseWrapper(false);

		XmlMapper mapper = new XmlMapper(factory, module);
		mapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
		return mapper;
	}
}