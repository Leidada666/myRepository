package com.czxy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czxy.dao.UserRegisterDao;
import com.czxy.pojo.User;
import com.czxy.service.UsersService;

@Service
public class UsersServiceImpl implements UsersService {
	
	@Autowired
	private UserRegisterDao userRegisterDao;
	@Override
	public List<User> selAll() {
		List<User> findAll = userRegisterDao.findAll();
		for(User u : findAll) {
			System.out.println(u);
		}
		return findAll;
	}

}
