package cn.czxy.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import cn.czxy.check.FileCheck;
import cn.czxy.create.CreateFile;
import cn.czxy.piechart.FileData;
import cn.czxy.piechart.PieChartToJSP;

@Controller
public class FileController {
	//生成图表相关类
	private FileData fileData = new FileData();
	
	//获取的文件MultipartFile数组
	private MultipartFile[] multipartFile = null;
	
	//获取getFileDataByTitle的文件信息
	private List<List<String>> createChart = null;
	
	
	//检验文件是否为.xlsx
	FileCheck fileCheck = new FileCheck();
	/**
	 * 文件上传，生成文件
	 */
	@RequestMapping("fileupload")
	public String createFile(HttpServletRequest req, String addr, MultipartFile template, @RequestParam("files")MultipartFile[] files, @RequestParam(defaultValue="new")String filename) {
		if(template.isEmpty() || files[0].isEmpty()) {
			req.setAttribute("result", "0");
			req.setAttribute("message", "必须选择文件");
			return "CreateFile/FileDownLoad.jsp";
		}
		
		int xlsx = fileCheck.isXLSX(template, files);
		//格式正确
		if(xlsx==0) {
			CreateFile create = new CreateFile();
			Map<String, List<String>> map = create.isSuccess(template, files, filename, addr);
			List<String> result = map.get("result");
			req.setAttribute("result", result.get(0)+"");
			List<String> list = map.get("message");
			req.setAttribute("message", list);
			if(result.get(0).equals("1")) {
				req.setAttribute("filepath", map.get("filepath").get(0));
			}
		}else if(xlsx==1){
			req.setAttribute("result", "0");
			req.setAttribute("message", "模板文件不符合要求");
		}else if(xlsx >= 2){
			req.setAttribute("result", "0");
			int x = xlsx - 2;
			req.setAttribute("message", files[x].getOriginalFilename()+"文件不符合要求");
		}
		return "CreateFile/FileDownLoad.jsp";
	}
	/**
	 * 生成文件后，进行文件下载
	 */
	@RequestMapping("download")
	public String fileDownLoad(String file, HttpServletResponse res) throws IOException {
		System.out.println(file);
		//下载excel文件
		res.setCharacterEncoding("utf-8");
		res.setContentType("application/vnd.ms-excel");		//以xls格式下载
		res.addHeader("Content-Type","application/force-download");	//出现下载保存窗口
		res.addHeader("Content-Dispositon", "attachment;filename=new.xlsx");
		ServletOutputStream os = res.getOutputStream();
		File file2 = new File(file);
		byte[] by = FileUtils.readFileToByteArray(file2);
		os.write(by);
		os.flush();
		os.close();
		return "index.jsp";
	}
	/**
	 * 上传文件，获取文件表头信息，检验格式是否一致
	 */
	@RequestMapping("getTitle")
	public String fileUpLoadToPie(@RequestParam("files")MultipartFile[] files, HttpServletRequest req) {
		if(files[0].isEmpty()) {
			req.setAttribute("result", 0);
			req.setAttribute("message","必须选择文件");
			return "PieChart/Title.jsp";
		}
		
		int xlsx = fileCheck.isXLSX(files);
		//格式正确
		if(xlsx==0) {
			multipartFile = files.clone();
			//获取文件表头
			List<String> title = fileData.getTitle(files);
			
			if(title.get(0).equals("1")) {
				//格式一致
				title.remove(0);//移除第一个格式是否符合的标志
				req.setAttribute("list", title);
				req.setAttribute("result", 1);
			}else {
				//不一致
				req.setAttribute("result", 0);
				req.setAttribute("message",title.get(title.size()-1));
			}
		}else {
			int x = xlsx - 1;
			req.setAttribute("result", 0);
			req.setAttribute("message",files[x].getOriginalFilename()+"文件不符合要求");
		}
		return "PieChart/Title.jsp";
	}
	
	/**
	 * 根据title页面传入的标题的位置，提取文件中的内容
	 * 因为经过验证，所有文件的表头格式都等于getTitle中获取的title的list的顺序，所以只需要传回选择了第几列就可以
	 */
	@RequestMapping("getFileDataByTitle")
	public String getFileDataByTitle(@RequestParam("titles")int[] titles, HttpServletRequest req) {
		//存放文件中读取的成绩的数据
		List<List<String>> finalList = fileData.getFileData(multipartFile, titles);
		
		//将成绩数据保存下来
		createChart = finalList;
		
		if(finalList == null) {
			System.out.println("失败");
			return "redirect:error.jsp";
		}else {
			//将结果显示到界面
			req.setAttribute("finalList",finalList);
		}
		return "PieChart/PieChart.jsp";
	}

	/**
	 * 生成图表，根据createChart保存的成绩信息，生成图表
	 */
	@RequestMapping("createPieChart")
	public void createPieChart(HttpServletResponse res, int index) {
		PieChartToJSP pie = new PieChartToJSP();
		pie.pieOut(res, createChart, index);
	}
}
