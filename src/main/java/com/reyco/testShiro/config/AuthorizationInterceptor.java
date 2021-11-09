package com.reyco.testShiro.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.reyco.testShiro.annotation.AuthIgnore;
import com.reyco.testShiro.domain.SysUserToken;
import com.reyco.testShiro.service.SysUserTokenService;

@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	private SysUserTokenService sysUserTokenService;
	
	public static final String USER_KEY = "userId";
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		AuthIgnore annotation;
		if (handler instanceof HandlerMethod) {
			annotation = ((HandlerMethod) handler).getMethodAnnotation(AuthIgnore.class);
		} else {
			return true;
		}
		// 如果有@AuthIgnore注解，则不验证token
		if (annotation != null) {
			return true;
		}

		// 从header中获取token
		String token = request.getHeader("token");
		// 如果header中不存在token，则从参数中获取token
		if (StringUtils.isBlank(token)) {
			token = request.getParameter("token");
		}
		// token为空
		if (StringUtils.isBlank(token)) {
			throw new RuntimeException("token不能为空");
		}

		// 查询token信息
		SysUserToken usertoken = sysUserTokenService.queryByToken(token);
		if (usertoken == null || usertoken.getExpireTime().getTime() < System.currentTimeMillis()) {
			throw new RuntimeException("token失效，请重新登录");
		}
		// 设置userId到request里，后续根据userId，获取用户信息
		request.setAttribute(USER_KEY, usertoken.getUserId());
		return true;
	}
}
