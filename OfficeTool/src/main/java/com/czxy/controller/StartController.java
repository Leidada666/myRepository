package com.czxy.controller;

import java.util.Optional;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.czxy.dao.RoleRepositoryDao;
import com.czxy.pojo.Menu;
import com.czxy.pojo.Role;
import com.czxy.pojo.User;
import com.czxy.service.LoginService;

/**
 * 工具启动controller，由此进入登录页面
 */
@Controller
public class StartController {
	@Autowired
	private LoginService loginServiceImpl;
	
	@Autowired
	private RoleRepositoryDao roleRepositoryDao;
	
	@RequestMapping("/OfficeTool")
	public String homePage(HttpServletRequest req) {
		//判断cookie中是否存有手机号
		Cookie[] ck = req.getCookies();
		if(ck != null) {
			String u_tel = "";
			//遍历cookie信息
			for(Cookie c : ck) {
				if("u_telephone".equals(c.getName())) {
					u_tel = c.getValue();
					User loginByCookie = this.loginServiceImpl.loginByCookie(u_tel);
					if(loginByCookie != null) {
						Optional<Role> roleOne = this.roleRepositoryDao.findById(loginByCookie.getId());
						Role role = roleOne.get();
						req.getSession().setAttribute("user", loginByCookie);
						req.getSession().setAttribute("menu", role.getMenu());
						return "/main/homePage";
					}
				}
			}
		}
		return "index";
	}
}
