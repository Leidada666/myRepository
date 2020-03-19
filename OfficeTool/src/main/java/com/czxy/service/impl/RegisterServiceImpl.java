package com.czxy.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czxy.dao.UserLoginDao;
import com.czxy.dao.UserRegisterDao;
import com.czxy.pojo.User;
import com.czxy.service.RegisterService;

/**
 * 普通会员注册业务
 */
@Service
public class RegisterServiceImpl implements RegisterService{
	
	@Autowired
	private UserRegisterDao userRegisterDao;
	
	//进行注册检验用户是否存在
	@Autowired
	private UserLoginDao userLoginDao;
	
	/**
	 * 0:注册失败
	 * 1：注册成功
	 * 2：手机号已被注册
	 */
	@Override
	public int register(User user) {
		
		User findByTelephone = userLoginDao.findByTelephone(user.getTelephone());
		if(findByTelephone!=null) {
			//手机号已被注册
			return 2;
		}else {
			User save = this.userRegisterDao.save(user);
			if(save!=null) {
				//注册成功
				return 1;
			}else {
				//注册失败
				return 0;
			}
		}
	}
}
