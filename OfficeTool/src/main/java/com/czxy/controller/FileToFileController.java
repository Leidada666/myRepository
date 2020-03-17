package com.czxy.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.czxy.create.CreateFile;
import com.czxy.exception.FileToFileException;
import com.czxy.filepath.FilepathEnum;
import com.czxy.pojo.User;

@Controller
@RequestMapping("/fileToFile")
public class FileToFileController {
	
	private CreateFile createFile;

	
	@RequestMapping("/fileUpload")
	public String fileUpload(HttpServletRequest req, MultipartFile template, @RequestParam("files")MultipartFile[] files) throws FileToFileException {
		User user = (User) req.getSession().getAttribute("user");
		String username = user.getUsername();
		
		createFile = new CreateFile();
		String uuid = createFile.startCreateFile(template, files, username);
		System.out.println("uuid ： "+uuid+".xlsx");
		
		req.getSession().setAttribute("uuid", uuid+".xlsx");
		
		return "createFile/fileDownLoad";
	}
	
	
	@RequestMapping("/fileDownload")
	public void fileUpload(HttpServletResponse res,HttpServletRequest req, String uuid) throws IOException {
		User user = (User) req.getSession().getAttribute("user");
		String username = user.getUsername();
		
		//下载excel文件
		res.reset();
		res.setCharacterEncoding("utf-8");
		res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");//以xlsx格式下载
		res.addHeader("Content-Type","application/force-download");	//出现下载保存窗口
		res.addHeader("Content-disposition", "attachment;filename="+uuid);
		ServletOutputStream os = null;
		os = res.getOutputStream();
		String filepath = FilepathEnum.EmergeFilePath.getPath()+username+"/"+uuid;
		File file2 = new File(filepath);
		byte[] by = FileUtils.readFileToByteArray(file2);
		os.write(by);
		try {
			os.flush();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}