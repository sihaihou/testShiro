package com.reyco.testShiro.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reyco.testShiro.domain.SysUserToken;
import com.reyco.testShiro.service.ShiroService;
import com.reyco.testShiro.service.SysUserTokenService;

@Service
public class SysUserTokenServiceimpl implements SysUserTokenService {
	
	@Autowired
	private ShiroService shiroService;
	
	@Override
	public SysUserToken queryByToken(String token) {
		return shiroService.queryByToken(token);
	}

}
