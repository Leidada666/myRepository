package com.czxy.userinfo;

import com.czxy.pojo.Menu;
import com.czxy.pojo.Role;
import com.czxy.pojo.User;

public class MemberInfo {
	/**
	 * 创建会员
	 */
	public User member(String username, String password, String telephone) {
		//第一步:创建一个用户
		User user = new User();
		if(username=="") {
			user.setUsername("default_"+telephone);//用户名不能一致，因为手机号唯一
		}else {
			user.setUsername(username+"_"+telephone);//用户名不能一致，因为手机号唯一
		}
		user.setPassword(password);
		user.setTelephone(telephone);
		
		//第二步：创建一个角色
		Role role = new Role();
		role.setRole_name("会员");
		
		//第三步：关联角色和用户
		role.getUser().add(user);
		user.setRole(role);
		
		//第四步：创建菜单对象
		Menu menu1 = new Menu();
		menu1.setMenu_name("生成文件");
		menu1.setMenu_url("createFile/fileUpLoad");
		
		Menu menu2 = new Menu();
		menu2.setMenu_name("信息统计表");
		menu2.setMenu_url("pieChart/fileUpLoadToPie");
		
		//第五步：关联菜单和角色
		role.getMenu().add(menu1);
		role.getMenu().add(menu2);
		menu1.getRole().add(role);
		menu2.getRole().add(role);
		
		return user;
	}
}
