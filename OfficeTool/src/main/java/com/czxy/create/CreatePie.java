package com.czxy.create;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.assertj.core.util.Arrays;
import org.springframework.web.multipart.MultipartFile;

import com.czxy.check.FileHeadCheck;
import com.czxy.exception.FileOperateException;

public class CreatePie {
	
	private FileHeadCheck fileHeadCheck = new FileHeadCheck();
	
	//返回所有文件的表头
	public List<List<String>> fileTitle(MultipartFile[] files) throws FileOperateException {
		List<List<String>> sameFile = this.fileHeadCheck.isSameFile(files);
		return sameFile;
	}
	
	//提取所要的信息
	public List<List<String>> fileDataByTitle(MultipartFile[] files, List<List<String>> filesHead, String[] titles) throws Exception,FileOperateException{
		
		XSSFWorkbook xssfWorkbook = null;
		XSSFSheet sheetAt = null;
		XSSFRow row = null;
		XSSFCell cell = null;
		InputStream in = null;
		
		List<List<String>> fileDataByTitle = new ArrayList<List<String>>();
		
		int first_row = 1;
		List<String> tList = new ArrayList<>();
		//将titile加入到结果集中
		for(String s : titles) {
			tList.add(s);
		}
		fileDataByTitle.add(tList);
		for(int fileIndex = 0; fileIndex < files.length; fileIndex++) {
			try {
				in = files[fileIndex].getInputStream();
				xssfWorkbook = new XSSFWorkbook(in);
				sheetAt = xssfWorkbook.getSheetAt(0);
				int lastRowNum = sheetAt.getLastRowNum();
				
				for(int i = first_row; i<=lastRowNum; i++) {
					//一行数据
					List<String> list = new ArrayList<>();
					
					row = sheetAt.getRow(i);
					for(String title : titles) {
						//根据title的位置，读取相应的列
						int j = this.getTitleIndex(title, filesHead.get(fileIndex));
						
						cell = row.getCell(j);
						cell.setCellType(CellType.STRING);
						String value = cell.getStringCellValue();
						if(value.isEmpty()) {
							continue;
						}else {
							//行数据
							list.add(value);
						}
					}
					
					//读取完一行数据，存入结果集
					fileDataByTitle.add(list);
				}
			} catch (IOException e) {
				e.printStackTrace();
				this.close(xssfWorkbook, in);
				throw new FileOperateException("在生成图表的过程中，根据表头获取文件内容时失败");
			}
		}
		this.close(xssfWorkbook, in);
		return fileDataByTitle;
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
			System.out.println("CreatePie类中的方法关闭资源失败");
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取title的位置
	 */
	private int getTitleIndex(String title, List<String> oneFileHead) {
		return oneFileHead.indexOf(title);
	}
}
