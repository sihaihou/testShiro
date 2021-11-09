package com.reyco.testShiro.service;

import java.util.Set;

import com.reyco.testShiro.domain.SysUser;
import com.reyco.testShiro.domain.SysUserToken;

public interface ShiroService {
	 /**
     * 获取用户权限列表
     */
    Set<String> getUserPermissions(Integer userId);
    /**
     * 获取token
     * @param token
     * @return
     */
    SysUserToken queryByToken(String token);
    /**
     * 
     * @param sysUserToken
     */
    void SaveToken(SysUserToken sysUserToken);

    /**
     * 根据用户ID，查询用户
     * @param userId
     */
    SysUser queryUser(Integer userId);
    
}
