package com.czxy.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.czxy.service.UpdUserInfoService;

/**
 * 用于展示个人中心，进行密码修改等
 */
@Controller
@RequestMapping("/PersonalShow")
public class UserInfoController {
	@Autowired
	private UpdUserInfoService updUserInfoServiceImpl;
	
	//展示个人中心
	@RequestMapping("/personal")
	public String show(HttpServletRequest req, Model model) {
		model.addAttribute("showUser", req.getSession().getAttribute("user"));
		return "/userinfo/personal";
	}
	
	//修改个人信息
	@RequestMapping("/updateUserInfo")
	public String updUser(String username, String telephone, String id) {
		System.out.println("name : "+username + "telephone : "+telephone + "id : "+id);
		Integer id1 = Integer.parseInt(id);
		this.updUserInfoServiceImpl.updateUserInfo(username, telephone, id1);
		return "forward:/Quit/quit";
	}
}
