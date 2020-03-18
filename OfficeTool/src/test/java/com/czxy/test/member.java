package com.czxy.test;

import java.util.Iterator;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.czxy.App;
import com.czxy.dao.UserLoginDao;
import com.czxy.dao.UserRegisterDao;
import com.czxy.pojo.Menu;
import com.czxy.pojo.Role;
import com.czxy.pojo.User;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=App.class)
public class member {
	@Autowired
	private UserLoginDao userLoginDao;
	@Autowired
	private UserRegisterDao userRegisterDao;
	
	@Test
	public void save() {
		//第一步:创建一个用户
		User user = new User();
		user.setUsername("zs");
		user.setPassword("zs123");
		
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
		
		userRegisterDao.save(user);
	}
	
	@Test
	public void findUser() {
		User user = userLoginDao.findByUsernameAndPassword("zs", "zs123");
		System.out.println(user);
		Role role = user.getRole();
		System.out.println(role);
		Set<Menu> menu = role.getMenu();
		Iterator<Menu> it = menu.iterator();
		while(it.hasNext()) {
			System.out.println(it.next());
		}
		
	}
}
