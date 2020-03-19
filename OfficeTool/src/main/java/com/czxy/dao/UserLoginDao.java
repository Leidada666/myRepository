package com.czxy.dao;


import org.springframework.data.repository.Repository;

import com.czxy.pojo.User;

/**
 * 用户登录
 */
public interface UserLoginDao extends Repository<User, Integer> {
	//登录
	User findByTelephoneAndPassword(String telephone, String password);
	//注册检验
	User findByTelephone(String telephone);
}
