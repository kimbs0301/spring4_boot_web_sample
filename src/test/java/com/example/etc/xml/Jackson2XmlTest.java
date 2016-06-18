package com.example.etc.xml;

import java.io.Writer;
import java.util.Date;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ctc.wstx.api.WstxInputProperties;
import com.ctc.wstx.stax.WstxOutputFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;

/**
 * <pre>
 * https://github.com/FasterXML/jackson-dataformat-xml
 * https://github.com/FasterXML/jackson-dataformat-xml/issues/32
 * http://wiki.fasterxml.com/JacksonRelease20
 * https://github.com/FasterXML/jackson-dataformat-xml/wiki/Jackson-XML-annotations
 * http://www.programcreek.com/java-api-examples/index.php?api=com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector
 * </pre>
 * 
 * @author gimbyeongsu
 */
public class Jackson2XmlTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(Jackson2XmlTest.class);

	@Test
	public void test_writeValueAsString() throws Exception {
		JacksonXmlModule module = new JacksonXmlModule();
		module.setDefaultUseWrapper(false);

		XmlMapper mapper = new XmlMapper(module);
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);

		String xml = mapper.writeValueAsString(new Simple());
		LOGGER.debug("{}", xml);
	}

	@Test
	public void test_readValue() throws Exception {
		JacksonXmlModule module = new JacksonXmlModule();
		module.setDefaultUseWrapper(false);
		XmlMapper mapper = new XmlMapper(module);
		Simple value = mapper.readValue("<Simple><x>1</x><y>2</y></Simple>", Simple.class);
		LOGGER.debug("{}", value);
	}

	@Test
	public void test_writeValueAsString_ignore_namespace() throws Exception {
		XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
		XmlFactory factory = new XmlFactory(xmlInputFactory, new WstxOutputFactory() {
			@Override
			public XMLStreamWriter createXMLStreamWriter(Writer w) throws XMLStreamException {
				mConfig.setProperty(WstxInputProperties.P_RETURN_NULL_FOR_DEFAULT_NAMESPACE, true);
				XMLStreamWriter result = super.createXMLStreamWriter(w);
				result.setPrefix("xlink", "http://www.w3.org/1999/xlink");
				return result;
			}
		});

		JacksonXmlModule module = new JacksonXmlModule();
		module.setDefaultUseWrapper(false);

		XmlMapper mapper = new XmlMapper(factory, module);

		String xml = mapper.writeValueAsString(new Simple());
		LOGGER.debug("{}", xml);
	}

	@Test
	public void test_writeValueAsString_date_format() throws Exception {
		JacksonXmlModule module = new JacksonXmlModule();
		module.setDefaultUseWrapper(false);
		XmlMapper mapper = new XmlMapper(module);
		mapper.setDateFormat(new ISO8601DateFormat());

		String xml = mapper.writeValueAsString(new Simple());
		LOGGER.debug("{}", xml);
	}

	@Test
	public void test_writeValueAsString_annotation() throws Exception {
		JacksonXmlModule module = new JacksonXmlModule();
		module.setDefaultUseWrapper(false);

		XmlMapper mapper = new XmlMapper(module);
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);

		String xml = mapper.writeValueAsString(new AnnotationTest());
		LOGGER.debug("{}", xml);
	}

	@Test
	public void test_writeValueAsString_annotation2() throws Exception {
		JacksonXmlModule module = new JacksonXmlModule();
		module.setDefaultUseWrapper(false);

		XmlMapper mapper = new XmlMapper(module);
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
		// mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		mapper.configure(ToXmlGenerator.Feature.WRITE_XML_1_1, true);

		String xml = mapper.writeValueAsString(new Simple());
		LOGGER.debug("{}", xml);
	}

	@Test
	public void test_writeValueAsString_annotation3() throws Exception {
		XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
		XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newFactory();
		XmlFactory factory = new XmlFactory(xmlInputFactory, xmlOutputFactory);

		JacksonXmlModule module = new JacksonXmlModule();
		module.setDefaultUseWrapper(false);

		XmlMapper mapper = new XmlMapper(factory, module);
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
		// mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		mapper.configure(ToXmlGenerator.Feature.WRITE_XML_1_1, true);

		String xml = mapper.writeValueAsString(new AnnotationTest());
		LOGGER.debug("{}", xml);
	}

	@Test
	public void test_writeValueAsString_namespace() throws Exception {
		XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
		xmlInputFactory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, Boolean.FALSE);
		XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newFactory();
		// xmlOutputFactory.setProperty(name, value);
		XmlFactory factory = new XmlFactory(xmlInputFactory, xmlOutputFactory);
		JacksonXmlModule module = new JacksonXmlModule();
		module.setDefaultUseWrapper(false);
		XmlMapper mapper = new XmlMapper(factory, module);
		mapper.configure(ToXmlGenerator.Feature.WRITE_XML_1_1, true);
		mapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);

		String xml = mapper.writeValueAsString(new NamespaceTest());
		LOGGER.debug("{}", xml);
	}

	public static class Simple {
		public int x = 1;
		public Date date = new Date();

		@Override
		public String toString() {
			return "Simple [x=" + x + ", date=" + date + "]";
		}
	}

	@JacksonXmlRootElement(localName = "Simple")
	public static class AnnotationTest {
		@JacksonXmlProperty(localName = "x")
		public int x = 1;
		@JacksonXmlProperty(localName = "y")
		public int y = 2;
		@JacksonXmlCData
		@JacksonXmlProperty(localName = "text")
		public String text = "aa";

		@Override
		public String toString() {
			return "AnnotationTest [x=" + x + ", y=" + y + ", text=" + text + "]";
		}
	}

	@JacksonXmlRootElement(localName = "Simple", namespace = "http://blog.naver.com/seban21")
	public static class NamespaceTest {
		@JacksonXmlProperty(localName = "x", namespace = "http://blog.naver.com/seban21")
		public int x = 1;
		@JacksonXmlProperty(localName = "text", namespace = "http://blog.naver.com/seban21")
		public String text = "aa";

		@Override
		public String toString() {
			return "Simple [x=" + x + ", text=" + text + "]";
		}
	}
}
