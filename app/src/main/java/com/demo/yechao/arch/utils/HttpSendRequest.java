package com.demo.yechao.arch.utils;

import java.io.Serializable;
import java.util.Map;

public class HttpSendRequest implements Serializable {

	private static final long serialVersionUID = 3549079991098637171L;

	private String url;

	private Map<String, String> heads;

	private Map<String, String> content;

	private String sign;

	private int timeout = 30000;

	private String charset;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, String> getHeads() {
		return heads;
	}

	public void setHeads(Map<String, String> heads) {
		this.heads = heads;
	}

	public Map<String, String> getContent() {
		return content;
	}

	public void setContent(Map<String, String> content) {
		this.content = content;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

}
