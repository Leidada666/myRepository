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
		public void testSaveManyToMany() {
			//第一步：创建角色对象
			Role role = new Role();
			role.setRole_name("项目经理");
			//第二步：创建菜单对象
			Menu menu1 = new Menu();
			menu1.setMenu_name("生成文件");
			menu1.setMenu_url("FileUpLoad.jsp");
			
			Menu menu2 = new Menu();
			menu1.setMenu_name("信息统计");
			menu1.setMenu_url("FileUpLoadToPie.jsp");
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
		public void testSelectManyToMany() {
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
