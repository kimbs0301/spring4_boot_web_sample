package com.example.spring.config.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

/**
 * @author gimbyeongsu
 * 
 */
@Component("fileDownloadView")
public class FileDownloadView extends AbstractView {
	private static final Logger LOGGER = LoggerFactory.getLogger(FileDownloadView.class);

	public FileDownloadView() {
		// setContentType("applicaiton/download;charset=utf-8");
		setContentType("application/octet-stream;charset=utf-8");
	}

	private void setDownloadFileName(String fileName, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		String userAgent = request.getHeader("User-Agent");
		boolean isIe = userAgent.indexOf("MSIE") != -1;
		if (isIe) {
			fileName = URLEncoder.encode(fileName, "utf-8");
		} else {
			fileName = new String(fileName.getBytes("utf-8"));
		}
		LOGGER.debug("fileName:{}", fileName);
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
	}

	private void downloadFile(File downloadFile, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OutputStream out = response.getOutputStream();
		FileInputStream in = new FileInputStream(downloadFile);
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

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			this.setResponseContentType(request, response);
			File downloadFile = (File) model.get("downloadFile");
			LOGGER.debug("downloadFile:{}", downloadFile);
			this.setDownloadFileName(downloadFile.getName(), request, response);
			response.setContentLength((int) downloadFile.length());
			this.downloadFile(downloadFile, request, response);
		} catch (Exception e) {
			throw e;
		}
	}
}