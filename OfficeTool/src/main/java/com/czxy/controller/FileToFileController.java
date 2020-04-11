package com.czxy.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.czxy.create.CreateFile;
import com.czxy.exception.FileOperateException;
import com.czxy.filepath.FilepathEnum;
import com.czxy.pojo.User;
import com.google.gson.Gson;

@Controller
@RequestMapping("/fileToFile")
public class FileToFileController {
	
	private CreateFile createFile;
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@RequestMapping("/fileUpload")
	public String fileUpload(HttpServletRequest req, MultipartFile template, @RequestParam("files")MultipartFile[] files) throws FileOperateException {
		User user = (User) req.getSession().getAttribute("user");
		String username = user.getUsername();
		
		createFile = new CreateFile();
		String uuid = createFile.startCreateFile(template, files, username);
		System.out.println("uuid ： "+uuid+".xlsx");
		//当前所生成的文件
		req.getSession().setAttribute("uuid", uuid+".xlsx");
		
		//更新redis缓存
		List<String> finalList = redisMessage(username, uuid);
		req.getSession().setAttribute("uuids", finalList);
		return "createFile/fileDownLoad";
	}

	//更新redis缓存
	private List<String> redisMessage(String username, String uuid) {
		Boolean hasKey = this.redisTemplate.hasKey(username);
		List<String> finalList  = new ArrayList<String>();
		if(hasKey) {
			//已经生成过文件
			String object = (String) this.redisTemplate.opsForValue().get(username);
			Gson gson = new Gson();
			finalList = gson.fromJson(object, List.class);
			finalList.add(uuid+".xlsx");
			String json = gson.toJson(finalList);
			this.redisTemplate.opsForValue().set(username, json);
		}else {
			//还未生成过文件
			Gson gson = new Gson();
			List<String> newList  = new ArrayList<String>();
			newList.add(uuid+".xlsx");
			String json = gson.toJson(newList);
			this.redisTemplate.opsForValue().set(username, json);
		}
		return finalList;
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
