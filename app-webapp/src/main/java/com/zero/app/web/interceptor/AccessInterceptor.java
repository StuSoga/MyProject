package com.zero.app.web.interceptor;

import com.zero.base.annotation.NeedLogin;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zero on 16/3/2.
 */
public class AccessInterceptor extends HandlerInterceptorAdapter {


	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		if (!(handler instanceof HandlerMethod)){
			return true;
		}

		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Object controller = handlerMethod.getBean();

		NeedLogin needLogin = controller.getClass().getAnnotation(NeedLogin.class);
		if (needLogin == null) {
			needLogin = handlerMethod.getMethod().getAnnotation(NeedLogin.class);
		}

		if (needLogin == null) {
			return true;
		}

		return super.preHandle(request, response, handler);
	}
}
