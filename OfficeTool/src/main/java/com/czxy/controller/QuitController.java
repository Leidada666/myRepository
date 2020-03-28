package com.czxy.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Quit")
public class QuitController {
	@RequestMapping("/quit")
	public String quit(HttpServletRequest req, HttpServletResponse resp) {
		req.getSession().removeAttribute("user");
		req.getSession().removeAttribute("menu");
		//退出登录，使cookie失效
		Cookie ck = new Cookie("u_telephone", "111111");
		ck.setPath("/OfficeTool");
		ck.setMaxAge(0);
		resp.addCookie(ck);
		return "/index";
	}
}
