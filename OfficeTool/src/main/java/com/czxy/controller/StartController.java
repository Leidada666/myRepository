package com.czxy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 工具启动controller，由此进入登录页面
 */
@Controller
public class StartController {
	@RequestMapping("/OfficeTool")
	public String homePage() {
		//应当判断cookie中是否存有账号和密码
		return "index";
	}
}
