package com.czxy.controller;

import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.czxy.dao.RoleRepositoryDao;
import com.czxy.pojo.Role;
import com.czxy.pojo.User;
import com.czxy.service.LoginService;

/**
 * 进行登录验证
 */
@Controller
@RequestMapping("/loginValidate")
public class LoginController {
	@Autowired
	private LoginService loginServiceImpl;
	
	@Autowired
	private RoleRepositoryDao roleRepositoryDao;
	
	@RequestMapping("/login")
	public String login(String telephone, String password,HttpServletRequest req, HttpServletResponse resp) {
		User user = this.loginServiceImpl.login(telephone, password);
		if(user != null) {
			Optional<Role> roleOne = roleRepositoryDao.findById(user.getRole_id());
			Role role = roleOne.get();
			req.getSession().setAttribute("user", user);
			req.getSession().setAttribute("menu", role.getMenu());
			//登录成功，往cookie中添加用户信息，使用三天免登陆
			Cookie ck = new Cookie("u_telephone", user.getTelephone());
			ck.setPath("/OfficeTool");
			ck.setMaxAge(3*24*3600);
			resp.addCookie(ck);
			return "main/homePage";
		}else {
			return "redirect:/OfficeTool";
		}
	}
}
