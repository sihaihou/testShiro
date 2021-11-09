package com.reyco.testShiro.dao;

import org.apache.ibatis.annotations.Select;

import com.reyco.testShiro.domain.Account;

public interface AccountDao {
	
	@Select("select * from account where id=#{id}")
	Account getAccount(Integer id);
	
	@Select("select * from account where username=#{username}")
	Account getAccountByUsername(String username);
	
}
