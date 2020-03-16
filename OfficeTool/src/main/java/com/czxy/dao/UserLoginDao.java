package com.czxy.dao;


import org.springframework.data.repository.Repository;

import com.czxy.pojo.User;

/**
 * 用户登录
 */
public interface UserLoginDao extends Repository<User, Integer> {
	
	User findByUsernameAndPassword(String username, String password);
}
