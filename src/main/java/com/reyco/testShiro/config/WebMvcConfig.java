package com.reyco.testShiro.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

	@Autowired
	private AuthorizationInterceptor authorizationInterceptor;

	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authorizationInterceptor)
		.addPathPatterns("/**")
		.excludePathPatterns("/sys/**");
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
		.allowCredentials(true)
		.allowedOrigins("*")
		.allowedMethods("GET", "PUT", "DELETE","POST", "OPTIONS")
		.maxAge(3600);
	}
}
