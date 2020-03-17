package com.czxy.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GolbalException {
	@ExceptionHandler(value= {com.czxy.exception.FileToFileException.class})
	public ModelAndView fileTypeExceptionHandler(FileToFileException e) {
		ModelAndView mv = new ModelAndView();
		//返回错误信息
		mv.addObject("error", e.getMsg());
		//指定视图
		mv.setViewName("error/fileTypeError");
		return mv;
	}
}
