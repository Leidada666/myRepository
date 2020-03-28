package com.czxy.userinfo;

import com.czxy.pojo.User;

public class MemberInfo {
	/**
	 * 创建会员
	 */
	public User member(String username, String password, String telephone) {
		//创建一个用户
		User user = new User();
		if(username=="") {
			user.setUsername("default_"+telephone);//用户名不能一致，因为手机号唯一
		}else {
			user.setUsername(username+"_"+telephone);//用户名不能一致，因为手机号唯一
		}
		user.setPassword(password);
		user.setTelephone(telephone);
		user.setRole_id(2);
		
		return user;
	}
}
