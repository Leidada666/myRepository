package com.czxy.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.czxy.pojo.User;

/**
 *  拦截除StartController外的所有请求
 *  判断当前是否有用户登录，如果没有则跳转到登录页面
 */
@Component
public class IsLoginInterceptor implements HandlerInterceptor{
	//在jsp执行完成后执行
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
	//在控制器执行完成，进入到jsp之前进行
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}
	
	//在进入控制器之前执行
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		User user = (User) request.getSession().getAttribute("user");
		System.out.println("拦截"+user);
		if(user == null) {
			System.out.println("执行拦截器，重新登录");
			response.sendRedirect("/OfficeTool");
			return false;
		}
		return true;
	}
}
