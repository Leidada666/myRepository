package com.czxy.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.czxy.dao.UserLoginDao;
import com.czxy.pojo.User;
import com.czxy.service.LoginService;
@Service
public class LoginServiceImpl implements LoginService {
	@Autowired
	private UserLoginDao userLoginDao;
	
	@Override
	@Cacheable(value="user")
	public User login(String username, String password) {
		return this.userLoginDao.findByUsernameAndPassword(username, password);
	}

}
