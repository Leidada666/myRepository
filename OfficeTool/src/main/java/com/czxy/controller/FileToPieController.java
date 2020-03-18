package com.czxy.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.czxy.create.CreatePie;
import com.czxy.create.PieChartToHTML;
import com.czxy.exception.FileOperateException;

@Controller
@RequestMapping("/fileToPie")
public class FileToPieController {
	
	private CreatePie createPie = new CreatePie();
	
	private MultipartFile[] multipartFile;
	
	private List<List<String>> filesHead;
	
	@RequestMapping("/pieTitle")
	public String pieTitle(HttpServletRequest req ,@PathVariable("files") MultipartFile[] files) throws FileOperateException {
		List<List<String>> allFileHead = createPie.fileTitle(files);
		//以第一个文件的表头为主
		req.getSession().setAttribute("title", allFileHead.get(0));
		multipartFile = new MultipartFile[files.length];
		multipartFile = files;
		
		filesHead = new ArrayList<List<String>>();
		filesHead = allFileHead;
		return "/pieChart/title";
	}
	
	/**
	 * 读取选中的信息
	 */
	@RequestMapping("/fileDataByTitle")
	public String pieData(@RequestParam("titles")String[] titles, HttpServletRequest req) throws Exception {
		List<List<String>> fileDataByTitle = this.createPie.fileDataByTitle(multipartFile, filesHead, titles);
		req.getSession().setAttribute("list", fileDataByTitle);
		return "/pieChart/pieChart";
	}
	
	/**
	 * 生成图表
	 * @throws Exception 
	 */
	@RequestMapping("/look")
	@ResponseBody
	public void go(HttpServletRequest req ,@RequestParam("pieTitle") String pieTitle, HttpServletResponse res) throws Exception  {
		List<List<String>> finalList = (List<List<String>>) req.getSession().getAttribute("list");
		PieChartToHTML pie = new PieChartToHTML();
		pie.pieOut(res, finalList, pieTitle);
	}
}
