package com.czxy.service;

import com.czxy.pojo.User;

public interface LoginService {
	/**
	 * 根据手机号和密码登录
	 */
	User login(String telephone, String password);
	
	/**
	 * 根据cookie中的手机号登录
	 */
	User loginByCookie(String telephone);
}
