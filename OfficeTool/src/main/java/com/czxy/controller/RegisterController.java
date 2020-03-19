package com.czxy.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.czxy.pojo.User;
import com.czxy.service.RegisterService;
import com.czxy.userinfo.MemberInfo;

@Controller
@RequestMapping("/registerValidate")
public class RegisterController {
	
	private MemberInfo m;
	
	@Autowired
	private RegisterService registerServiceImpl;
	
	@RequestMapping("/registerPage")
	public String registerPage(User user) {
		return "/userinfo/register";
	}
	
	@RequestMapping("/register")
	public String register(HttpServletRequest req, String yesPwd, @ModelAttribute("user") @Valid User user,  BindingResult result) throws Exception {
		if(user.getPassword().equals(yesPwd)) {
			m = new MemberInfo();
			User userRgs = this.m.member(user.getUsername(), user.getPassword(), user.getTelephone());
			int register = this.registerServiceImpl.register(userRgs);
			if(register==0) {
				System.out.println("注册失败");
				req.getSession().setAttribute("registerMsg", "0");
				return "userinfo/registerMsg";
			}else if(register == 1){
				req.getSession().setAttribute("registerMsg", "1");
				return "userinfo/registerMsg";
			}else if(register == 2) {
				req.getSession().setAttribute("registerMsg", user.getTelephone());
				return "userinfo/registerMsg";
			}
		}else {
			req.getSession().setAttribute("registerMsg", "9");
			return "userinfo/registerMsg";
		}
		return "index";
	}
}
