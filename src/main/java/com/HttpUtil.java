package com;


import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import javax.naming.CommunicationException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * http工具类
 */
public class HttpUtil {

	private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

	public static String getHttp(String url) throws CommunicationException {
		String responseMsg = "";
		int httpStatusCode = 0;

		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setContentCharset("UTF-8");
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(300000);

		GetMethod getMethod = new GetMethod(url);
		String infoForLog = "get url=" + url;
		logger.info(infoForLog);

		try {
			httpClient.executeMethod(getMethod);
			httpStatusCode = getMethod.getStatusCode();
			if (httpStatusCode == HttpStatus.SC_OK) {
				responseMsg = getMethod.getResponseBodyAsString().trim();
				logger.info("get url=" + url + ",response code=" + httpStatusCode + ",response msg=" + responseMsg);
			} else {
				String errMsg = "get调用 接口失败，httpStatusCode：" + httpStatusCode + "，返回信息："
						+ getMethod.getResponseBodyAsString().trim();
				logger.error(errMsg);
				throw new CommunicationException(errMsg);
			}
		} catch (HttpException e) {
			logger.error(e.getMessage(), e);
			throw new CommunicationException(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new CommunicationException(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new CommunicationException(e.getMessage());
		} finally {
			getMethod.releaseConnection();
		}
		return responseMsg;
	}

	public static String postHttp(String url, String[][] props) throws CommunicationException {
		String responseMsg = "";
		int httpStatusCode = 0;

		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setContentCharset("UTF-8");
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(300000);

		PostMethod postMethod = new PostMethod(url);
		String infoForLog = "post url=" + url;
		for (String[] prop : props) {
			infoForLog += ", " + prop[0] + "=" + prop[1];
			postMethod.addParameter(prop[0], prop[1]);
		}
		logger.info(infoForLog);

		try {
			httpClient.executeMethod(postMethod);
			httpStatusCode = postMethod.getStatusCode();
			if (httpStatusCode == HttpStatus.SC_OK) {
				responseMsg = postMethod.getResponseBodyAsString().trim();
				logger.info("post url=" + url + ",response code=" + httpStatusCode + ",response msg=" + responseMsg);
			} else {
				String errMsg = "post调用 接口失败，httpStatusCode：" + httpStatusCode + "，返回信息："
						+ postMethod.getResponseBodyAsString().trim();
				logger.error(errMsg);
				throw new CommunicationException(errMsg);
			}
		} catch (HttpException e) {
			logger.error(e.getMessage(), e);
			throw new CommunicationException(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new CommunicationException(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new CommunicationException(e.getMessage());
		} finally {
			postMethod.releaseConnection();
		}
		return responseMsg;
	}

	public static String postHttp(String url, Map<String, String> param) throws CommunicationException {
		String responseMsg = "";
		int httpStatusCode = 0;

		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setContentCharset("UTF-8");
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(300000);

		PostMethod postMethod = new PostMethod(url);
		String infoForLog = "post url=" + url;
		// 添加参数
		for (Entry<String, String> entry : param.entrySet()) {
			infoForLog += ", " + entry.getKey() + "=" + entry.getValue();
			postMethod.addParameter(entry.getKey(), entry.getValue());
		}
		logger.info(infoForLog);

		try {
			httpClient.executeMethod(postMethod);
			httpStatusCode = postMethod.getStatusCode();
			if (httpStatusCode == HttpStatus.SC_OK) {
				responseMsg = postMethod.getResponseBodyAsString().trim();
				logger.info("post url=" + url + ",response code=" + httpStatusCode + ",response msg=" + responseMsg);
			} else {
				String errMsg = "post调用 接口失败，httpStatusCode：" + httpStatusCode + "，返回信息："
						+ postMethod.getResponseBodyAsString().trim();
				logger.error(errMsg);
				throw new CommunicationException(errMsg);
			}
		} catch (HttpException e) {
			logger.error(e.getMessage(), e);
			throw new CommunicationException(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new CommunicationException(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new CommunicationException(e.getMessage());
		} finally {
			postMethod.releaseConnection();
		}
		return responseMsg;
	}
}
