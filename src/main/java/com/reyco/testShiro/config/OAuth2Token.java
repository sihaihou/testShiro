package com.reyco.testShiro.config;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * Token
 * @author reyco
 *
 */
public class OAuth2Token implements AuthenticationToken{
	
	private String token;
	
	public OAuth2Token(String token) {
		super();
		this.token = token;
	}

	@Override
	public Object getPrincipal() {
		return token;
	}

	@Override
	public Object getCredentials() {
		return token;
	}

}
