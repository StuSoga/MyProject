package com.zero.app.web.controller;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zero on 16/3/2.
 */
public abstract class AbstractAppController {

	protected final Logger LOG= LoggerFactory.getLogger(this.getClass());

	@Autowired
	private HttpServletRequest request;

	/*
	 * 获得访客的IP地址
	 */
	protected String getIpAddr() {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public boolean isFromIOS() {

		String userAgent = request.getHeader("user-agent");

		if (StringUtils.isBlank(userAgent)) {
			return false;
		}

		return StringUtils.upperCase(userAgent).indexOf("IPHONE") != -1;

	}

	public boolean isFromAndroid() {

		String userAgent = request.getHeader("user-agent");

		if (StringUtils.isBlank(userAgent)) {
			return false;
		}

		return StringUtils.upperCase(userAgent).indexOf("ANDROID") != -1;
	}
}
