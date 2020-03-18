package com.czxy.check;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.czxy.exception.FileOperateException;


/**
 * 根据文件表头判断文件
 */
public class FileHeadCheck {
	
	/**
	 * FileToFile
	 * 判断用于生成的各个文件，是否包含模板文件所要的内容，以及是否有空文件（只含有表头的文件也被认为是空文件）
	 * 成功，返回模板文件表头；
	 * 失败，抛FileOperateException异常。
	 */
	public List<List<String>> isContainsHead(MultipartFile template, MultipartFile[] files) throws FileOperateException{
		
		List<List<String>> heads = new ArrayList<>();
		
		List<String> templateHead = this.readHead(template, 1);
		//将模板文件表头加入到表头数组中
		heads.add(templateHead);
		
		for(MultipartFile file : files) {
			List<String> fileHead = this.readHead(file, 0);
			
			if(!fileHead.containsAll(templateHead)) {
				throw new FileOperateException(file.getOriginalFilename()+"文件不包含模板文件所要的全部内容");
			}else {
				heads.add(fileHead);
			}
			
			
		}
		
		return heads;
	}
	
	
	
	/**
	 * FileToPie
	 * 判断各个文件是否具有统一格式
	 * 成功，返回所有文件的表头信息
	 * 失败，抛FileOperateException异常
	 */
	public List<List<String>> isSameFile(MultipartFile[] files) throws FileOperateException{
		
		List<List<String>> allFileHead = new ArrayList<>();
		
		List<String> firstFileHead = new ArrayList<>();
		
		if(files.length < 1) {
			throw new FileOperateException("请选择文件");
		}else if(files.length == 1) {	//只有一个文件
			firstFileHead = this.readHead(files[0], 0);
			allFileHead.add(firstFileHead);
		}else {		//多个文件
			firstFileHead = this.readHead(files[0], 0);
			allFileHead.add(firstFileHead);
			for(int i = 1; i<files.length; i++) {
				List<String> list = this.readHead(files[i], 0);
				if(!firstFileHead.containsAll(list) || !list.containsAll(firstFileHead)) {
					throw new FileOperateException("所选择的文件格式不一致，无法生成表格，重新选择文件！");
				}
				allFileHead.add(list);
			}
		}
		return allFileHead;
	}
	
	/**
	 * 读取文件表头
	 * file : 读取表头的文件
	 * isTemplate ： 是否是模板文件，1为true，0为false
	 * 是否是模板文件，影响到判断空文件的标准
	 */
	private List<String> readHead(MultipartFile file, int isTemplate) throws FileOperateException{
		List<String> head = new ArrayList<String>();
		XSSFWorkbook xssfWorkbook = null;
		XSSFSheet sheetAt = null;
		XSSFRow row = null;
		XSSFCell cell = null;
		InputStream in = null;
		
		try {
			in = file.getInputStream();
			xssfWorkbook = new XSSFWorkbook(in);
			sheetAt = xssfWorkbook.getSheetAt(0);//假设只有一张表
			int firstRowNum = sheetAt.getFirstRowNum();
			row = sheetAt.getRow(firstRowNum);	//获取第一行
			short lastCellNum = row.getLastCellNum();
			
			//判断用于生成的文件是否为空，空文件或者只有一行的文件被判定为空
			if(isTemplate == 0) {
				int lastRowNum = sheetAt.getLastRowNum();
				//读取文件最后一行，类似于数组，如果只有一行，则lastRowNum=0
				if(lastRowNum == 0) {
					this.close(xssfWorkbook, in);
					throw new FileOperateException(file.getOriginalFilename()+"文件为空");
				}
			}
			
			for(int i = 0; i<lastCellNum; ++i) {
				cell = row.getCell(i);
				cell.setCellType(CellType.STRING);
				String value = cell.getStringCellValue();
				head.add(value);
			}
		}catch(Exception e) {
			System.out.println("FileHeadCheck文件为空");
			if(isTemplate == 1) {
				this.close(xssfWorkbook, in);
				throw new FileOperateException("FileHeadCheck模板文件为空");
			}else if(isTemplate == 0) {
				this.close(xssfWorkbook, in);
				throw new FileOperateException(file.getOriginalFilename()+"文件是空文件或者文件中存在无内容的表格。注意：除模板文件可以只有一行内容外，其余文件若只有一行内容则会被认为是空文件！！！");
			}
		}
		return head;
	}
	
	/**
	 * 关闭资源
	 */
	private void close(XSSFWorkbook xssfWorkbook, InputStream in) {
		try {
			if(xssfWorkbook!=null) {
				xssfWorkbook.close();
			}
			if(in!=null) {
				in.close();
			}
		} catch (IOException e) {
			System.out.println("FileHeadCheck方法关闭资源失败");
			e.printStackTrace();
		}
	}
}
