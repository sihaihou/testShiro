package com.reyco.testShiro.service;

public interface SysLoginService {
	
	/**
	 * 登录
	 * @param username
	 * @param password
	 * @return
	 */
	Object login(String username,String password);
	
}
