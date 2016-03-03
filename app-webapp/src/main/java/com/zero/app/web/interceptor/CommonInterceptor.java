package com.zero.app.web.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zero on 16/3/2.
 */
public class CommonInterceptor extends HandlerInterceptorAdapter {

	private static final Logger LOG= LoggerFactory.getLogger(CommonInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		LOG.info("请求客户端：" + request.getHeader("user-agent"));
		LOG.info("请求SessionId：" + request.getSession().getId());

		return super.preHandle(request, response, handler);
	}
}
