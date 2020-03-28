package com.czxy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.czxy.pojo.User;
import com.czxy.service.UsersService;
import com.google.gson.Gson;

/**
 * 用于展示给管理员所有用户信息
 */
@Controller
@RequestMapping("/ShowUser")
public class ShowUsersController {
	
	@Autowired
	private UsersService usersServiceImpl;
	
	@RequestMapping("/users")
	@ResponseBody
	public String user(){
		List<User> selAll = this.usersServiceImpl.selAll();
		Gson gson = new Gson();
		String json = gson.toJson(selAll);
		return json;
	}
}
