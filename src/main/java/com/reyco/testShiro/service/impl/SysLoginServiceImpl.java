package com.reyco.testShiro.service.impl;

import java.util.Date;
import java.util.UUID;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reyco.testShiro.dao.AccountDao;
import com.reyco.testShiro.domain.Account;
import com.reyco.testShiro.domain.SysUserToken;
import com.reyco.testShiro.service.ShiroService;
import com.reyco.testShiro.service.SysLoginService;

@Service
public class SysLoginServiceImpl implements SysLoginService{
	
	@Autowired
	private AccountDao accountDao;
	@Autowired
	ShiroService shiroService;
	
	@Override
	public Object login(String username, String password) {
		Account account = accountDao.getAccountByUsername(username);
		if(account==null) {
			throw new RuntimeException("登录失败,用户名或密码错误。");
		}
		SimpleHash simpleHash = new SimpleHash("md5", password, account.getSalt(), 2);
		String encryptionPassword = simpleHash.toString();
		System.out.println("encryptionPassword:"+encryptionPassword);
		if(!account.getPassword().equals(encryptionPassword)) {
			throw new RuntimeException("登录失败,用户名或密码错误。");
		}
		SysUserToken sysUserToken = new SysUserToken();
		sysUserToken.setUserId(account.getId());
		sysUserToken.setToken(UUID.randomUUID().toString().replace("-", ""));
		Date updateTime = new Date();
		sysUserToken.setUpdateTime(updateTime);
		Long time = updateTime.getTime()+1000*60*60;
		Date expireTime = new Date(time);
		sysUserToken.setExpireTime(expireTime);
		//
		shiroService.SaveToken(sysUserToken);
		return sysUserToken;
	}
	/**
	 * 测试密码
	 * @param args
	 */
	public static void main(String[] args) {
		Md5Hash md5Hash = new Md5Hash("123456","admin",2);
		System.out.println(md5Hash.toString());
		SimpleHash simpleHash = new SimpleHash("md5", "123456", "admin", 2);
		System.out.println(simpleHash.toString());
		if(simpleHash.toString().equals("928bfd2577490322a6e19b793691467e")) {
			System.out.println("true");
		}
	}
}
