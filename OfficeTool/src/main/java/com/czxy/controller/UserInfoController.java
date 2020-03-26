package com.czxy.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 用于展示个人中心，进行密码修改等
 */
@Controller
@RequestMapping("/UserShow")
public class UserInfoController {
	
	@RequestMapping("/show")
	public String show(HttpServletRequest req, Model model) {
		model.addAttribute("showUser", req.getSession().getAttribute("user"));
		return "userinfo/users";
	}
}
