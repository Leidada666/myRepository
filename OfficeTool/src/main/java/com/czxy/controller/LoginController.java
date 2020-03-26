package com.czxy.controller;

import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.czxy.pojo.Menu;
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
	
	@RequestMapping("/login")
	public String login(String telephone, String password,HttpServletRequest req, HttpServletResponse resp) {
		User user = this.loginServiceImpl.login(telephone, password);
		if(user != null) {
			Role role = user.getRole();
			Set<Menu> menu = role.getMenu();
			req.getSession().setAttribute("user", user);
			req.getSession().setAttribute("menu", menu);
			//登录成功，往cookie中添加用户信息，使用三天免登陆
			Cookie ck = new Cookie("u_telephone", user.getTelephone());
			ck.setPath("/OfficeTool");
//			ck.setPath("/");
			ck.setMaxAge(3*24*3600);
			resp.addCookie(ck);
			return "/main/homePage";
		}else {
			return "redirect:/OfficeTool";
		}
	}
}
