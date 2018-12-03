package com.demo.yechao.arch.utils;

import java.io.Serializable;
import java.util.Map;

public class HttpSendResponse implements Serializable {

	private static final long serialVersionUID = 7925034973240389212L;

	private Integer responseStatus;

	private Map<String, String> responseHeaders;

	private String responseBody;

	public Integer getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(Integer responseStatus) {
		this.responseStatus = responseStatus;
	}

	public Map<String, String> getResponseHeaders() {
		return responseHeaders;
	}

	public void setResponseHeaders(Map<String, String> responseHeaders) {
		this.responseHeaders = responseHeaders;
	}

	public String getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}

}
