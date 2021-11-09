package com.reyco.testShiro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reyco.testShiro.service.SysLoginService;

@RestController
@RequestMapping("sys")
public class LoginController {
	
	@Autowired
	private SysLoginService sysLoginService;
	
	
	@PostMapping("login")
	public Object login(String username,String password) {
		Object login = sysLoginService.login(username, password);
		return login;
	}
}
