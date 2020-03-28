package com.czxy.test;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.czxy.App;
import com.czxy.dao.RoleRepositoryDao;
import com.czxy.pojo.Menu;
import com.czxy.pojo.Role;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=App.class)
public class RoleRepositoryTest {
	@Autowired
	private RoleRepositoryDao roleRepositoryDao;
	
	//菜单和角色关联
		@Test
		public void testSaveManager() {
			//第一步：创建角色对象
			Role role = new Role();
			role.setRole_name("管理员");
			//第二步：创建菜单对象
			Menu menu1 = new Menu();
			menu1.setMenu_name("生成文件");
			menu1.setMenu_url("creteFile/fileUpLoad");
			
			Menu menu2 = new Menu();
			menu2.setMenu_name("信息统计表");
			menu2.setMenu_url("pieChart/fileUpLoadToPie");
			
			Menu menu3 = new Menu();
			menu3.setMenu_name("成员信息");
			menu3.setMenu_url("userinfo/users");
			//第三步：关联--@ManyToMany(cascade=CascadeType.PERSIST)
			role.getMenu().add(menu1);
			role.getMenu().add(menu2);
			role.getMenu().add(menu3);
			menu1.getRole().add(role);
			menu2.getRole().add(role);
			menu3.getRole().add(role);
			//保存
			this.roleRepositoryDao.save(role);
		}
		
		
		@Test
		public void testSaveMember() {
			//第一步：创建角色对象
			Role role = new Role();
			role.setRole_name("会员");
			//第二步：创建菜单对象
			Menu menu1 = new Menu();
			menu1.setMenu_name("生成文件");
			menu1.setMenu_url("creteFile/fileUpLoad");
			
			Menu menu2 = new Menu();
			menu2.setMenu_name("信息统计表");
			menu2.setMenu_url("pieChart/fileUpLoadToPie");
			
			//第三步：关联--@ManyToMany(cascade=CascadeType.PERSIST)
			role.getMenu().add(menu1);
			role.getMenu().add(menu2);
			menu1.getRole().add(role);
			menu2.getRole().add(role);
			//保存
			this.roleRepositoryDao.save(role);
		}
		/**
		 * 测试查询
		 */
		@Test
		public void testSelectRole() {
			List<Role> role = this.roleRepositoryDao.findAll();
			Role role2 = role.get(1);
			Set<Menu> menu = role2.getMenu();
			Iterator<Menu> iterator = menu.iterator();
			while(iterator.hasNext()) {
				Menu next = iterator.next();
				System.out.println(next.getMenu_name());
			}
		}
}
