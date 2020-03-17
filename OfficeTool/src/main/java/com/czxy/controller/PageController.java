package com.czxy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 控制iframe的页面
 */
@Controller
@RequestMapping("/if")
public class PageController {
	@RequestMapping("/{folder}/{page}")
	public String page(@PathVariable String folder,@PathVariable String page) {
		return "/"+folder+"/"+page;
	}
}
