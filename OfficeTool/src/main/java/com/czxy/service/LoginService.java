package com.czxy.service;

import com.czxy.pojo.User;

public interface LoginService {
	
	User login(String telephone, String password);
}
