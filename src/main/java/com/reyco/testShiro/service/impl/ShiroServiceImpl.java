package com.reyco.testShiro.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reyco.testShiro.dao.AccountDao;
import com.reyco.testShiro.dao.PermissionDao;
import com.reyco.testShiro.domain.Account;
import com.reyco.testShiro.domain.Permission;
import com.reyco.testShiro.domain.SysUser;
import com.reyco.testShiro.domain.SysUserToken;
import com.reyco.testShiro.service.ShiroService;

@Service
public class ShiroServiceImpl implements ShiroService{
	
	@Autowired
	private PermissionDao permissionDao;
	@Autowired
	private AccountDao accountDao;
	
	private Map<String,SysUserToken> sysUserTokenMap = new HashMap<>();
	
	@Override
	public Set<String> getUserPermissions(Integer userId) {
		Set<String> permsSet = null;
		List<Permission> permissions = permissionDao.listByUserId(userId);
		for(Permission permission : permissions) {
			if(permsSet==null) {
				permsSet = new HashSet<>();
			}
			permsSet.add(permission.getPercode());
		}
		return permsSet;
	}

	@Override
	public SysUserToken queryByToken(String token) {
		return sysUserTokenMap.get(token);
	}

	@Override
	public SysUser queryUser(Integer userId) {
		Account account = accountDao.getAccount(userId);
		SysUser sysUser = new SysUser();
		sysUser.setUserId(account.getId());
		sysUser.setUsername(account.getUsername());
		sysUser.setPassword(account.getPassword());
		sysUser.setSalt(account.getSalt());
		return sysUser;
	}

	@Override
	public void SaveToken(SysUserToken sysUserToken) {
		sysUserTokenMap.put(sysUserToken.getToken(), sysUserToken);
	}

}
