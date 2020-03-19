package com.czxy.service;

import com.czxy.pojo.User;
/**
 * 0:注册失败
 * 1：注册成功
 * 2：手机号已被注册
 */
public interface RegisterService {
	/**
	 * 0:注册失败
	 * 1：注册成功
	 * 2：手机号已被注册
	 */
	int register(User user);
}
