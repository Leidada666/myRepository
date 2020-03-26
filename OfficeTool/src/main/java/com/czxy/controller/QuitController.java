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
//		Cookie[] cookies = req.getCookies();
//		for(Cookie c : cookies) {
//			if("u_telephone".equals(c.getName())) {
//				c.setMaxAge(0);
//				resp.addCookie(c);
//			}
//		}
		return "/index";
	}
}
