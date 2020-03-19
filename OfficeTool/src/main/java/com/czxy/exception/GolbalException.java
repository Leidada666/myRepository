package com.czxy.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.exceptions.TemplateInputException;

@ControllerAdvice
public class GolbalException {
	@ExceptionHandler(value= {com.czxy.exception.FileOperateException.class})
	public ModelAndView fileTypeExceptionHandler(FileOperateException e) {
		ModelAndView mv = new ModelAndView();
		//返回错误信息
		mv.addObject("error", e.getMsg());
		//指定视图
		mv.setViewName("error/fileTypeError");
		return mv;
	}
	
	@ExceptionHandler(value= {java.lang.NullPointerException.class})
	public ModelAndView fileTypeExceptionHandler(NullPointerException e) {
		ModelAndView mv = new ModelAndView();
		//返回错误信息
		System.out.println(e.getMessage());
		mv.addObject("error", "系统发生故障，重试");
		//指定视图
		mv.setViewName("error/fileTypeError");
		return mv;
	}
	
	@ExceptionHandler(value = {java.lang.NumberFormatException.class})
	public ModelAndView numberFormatExceptionHandler(NumberFormatException e) {
		ModelAndView mv = new ModelAndView();
		//返回错误信息
		System.out.println(e.getMessage());
		mv.addObject("error", "请检查成绩单中是否存在非数字的数据！！！");
		//指定视图
		mv.setViewName("error/fileTypeError");
		return mv;
	}
	
	@ExceptionHandler(value = {org.thymeleaf.exceptions.TemplateInputException.class})
	public ModelAndView numberFormatExceptionHandler(TemplateInputException e) {
		ModelAndView mv = new ModelAndView();
		//返回错误信息
		System.out.println(e.getMessage());
		mv.addObject("error", "请检查成绩单中是否存在非数字的数据！！！");
		//指定视图
		mv.setViewName("error/thymeleafError");
		return mv;
	}
}
