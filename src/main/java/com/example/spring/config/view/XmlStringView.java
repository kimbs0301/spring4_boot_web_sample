package com.example.spring.config.view;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

/**
 * @author gimbyeongsu
 *
 */
@Component("xmlStringView")
public class XmlStringView extends AbstractView {

	public XmlStringView() {
		setContentType("application/xml;charset=utf-8");
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OutputStream out = response.getOutputStream();
		// response.setHeader("Content-Type", "application/xml;charset=utf-8");
		String contents = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><data>111</data>";
		InputStream in = new ByteArrayInputStream(contents.getBytes("UTF-8"));
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
