package com.reyco.testShiro.service;

import com.reyco.testShiro.domain.SysUserToken;

public interface SysUserTokenService {
	
	SysUserToken queryByToken(String token);
	
}
