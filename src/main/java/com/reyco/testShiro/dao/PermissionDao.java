package com.reyco.testShiro.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.reyco.testShiro.domain.Permission;

public interface PermissionDao {
	
	
	@Select("select * from user_permission_view where id=#{userId}")
	List<Permission> listByUserId(Integer userId);
	
}
