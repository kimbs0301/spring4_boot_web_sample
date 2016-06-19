package com.example.etc.ssl;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.Ignore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gimbyeongsu
 * 
 */
public class HttpsTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpsTest.class);

	@Ignore
	@Test
	public void test_ssl() throws Exception {
		SSLContext sslcontext = SSLContext.getInstance("TLS");
		X509TrustManager tm = new X509TrustManager() {
			@Override
			public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
			}

			@Override
			public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
			}

			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		sslcontext.init(null, new TrustManager[] { tm }, null);
		SSLSocketFactory socketFactory = new SSLSocketFactory(sslcontext, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		//
		DefaultHttpClient httpclient = new DefaultHttpClient();
		Scheme https = new Scheme("https", 8443, socketFactory);
		httpclient.getConnectionManager().getSchemeRegistry().register(https);
		HttpRequestInterceptor postHttpRequestInterceptor = new HttpRequestInterceptor() {
			public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
				request.setHeader(HTTP.USER_AGENT, "httpclient_4.1.3");
				request.setHeader(HTTP.CONTENT_TYPE, "application/json");
				request.setHeader("Accept", "application/json");
				request.setHeader("Cache-Control", "no-cache");
			}
		};
		httpclient.addRequestInterceptor(postHttpRequestInterceptor);

		HttpGet httpget = new HttpGet("https://localhost:8443/mvc/account/member/11.json");
		LOGGER.debug("executing request:{}", httpget.getRequestLine());
		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		LOGGER.debug("----------------------------------");
		LOGGER.debug("{}", response.getStatusLine());
		if (entity != null) {
			LOGGER.debug("Response content length:{}", entity.getContentLength());
			LOGGER.debug("{}", IOUtils.toString(entity.getContent(), "UTF-8"));
		}
		EntityUtils.consume(entity);
	}
}