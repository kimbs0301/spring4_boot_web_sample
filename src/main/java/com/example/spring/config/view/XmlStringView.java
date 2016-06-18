package com.example.spring.config.view;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

import com.example.spring.common.model.Channel;
import com.example.spring.common.model.Header;
import com.example.spring.config.model.ExceptionXml;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * @author gimbyeongsu
 *
 */
@Component("xmlStringView")
public class XmlStringView extends AbstractView {
	private static final Logger LOGGER = LoggerFactory.getLogger(XmlStringView.class);

	@Autowired
	private XmlMapper xmlMapper;

	public XmlStringView() {
		LOGGER.debug("생성자 XmlStringView()");
		setContentType("application/xml;charset=utf-8");
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OutputStream out = response.getOutputStream();
		// response.setHeader("Content-Type", "application/xml;charset=utf-8");

		ExceptionXml xml = (ExceptionXml) model.get("xml");
		Channel channel = new Channel(new Header("UNKNOWN", ""), xml);
		InputStream in = new ByteArrayInputStream(xmlMapper.writeValueAsBytes(channel));
		try {
			FileCopyUtils.copy(in, out);
			out.flush();
		} catch (Exception e) {
			throw e;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException ioe) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException ioe) {
				}
			}
		}
	}
}
