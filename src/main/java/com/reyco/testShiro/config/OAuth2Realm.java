package com.reyco.testShiro.config;

import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reyco.testShiro.domain.SysUser;
import com.reyco.testShiro.domain.SysUserToken;
import com.reyco.testShiro.service.ShiroService;

/**
 * 
 * @author reyco
 *
 */
@Component
public class OAuth2Realm extends AuthorizingRealm {

	@Autowired
	private ShiroService shiroService;

	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof OAuth2Token;
	}
	/**
	 * 认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String accessToken = (String) token.getPrincipal();

		// 根据accessToken，查询用户信息
		SysUserToken sysUsertoken = shiroService.queryByToken(accessToken);
		// token失效
		if (sysUsertoken == null || sysUsertoken.getExpireTime().getTime() < System.currentTimeMillis()) {
			throw new IncorrectCredentialsException("token失效，请重新登录");
		}

		// 查询用户信息
		SysUser sysUser = shiroService.queryUser(sysUsertoken.getUserId());
		SimpleAuthenticationInfo info = null;
		if (null != sysUser) {
			info = new SimpleAuthenticationInfo(sysUser,accessToken, getName());
		}
		return info;
	}

	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SysUser user = (SysUser) principals.getPrimaryPrincipal();
		Integer userId = user.getUserId();
		// 用户权限列表
		Set<String> permsSet = shiroService.getUserPermissions(userId);
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setStringPermissions(permsSet);
		return info;
	}
	/**
	 * 清理缓存
	 */
	protected void clearCache() {
		Subject subject = SecurityUtils.getSubject();
		super.clearCache(subject.getPrincipals());
	}
}
