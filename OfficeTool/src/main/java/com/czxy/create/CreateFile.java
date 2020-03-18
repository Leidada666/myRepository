package com.czxy.create;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.czxy.check.FileHeadCheck;
import com.czxy.check.FileTypeCheck;
import com.czxy.exception.FileOperateException;
import com.czxy.filepath.FilepathEnum;

public class CreateFile {
	
	private FileTypeCheck fileTypeCheck;
	private FileHeadCheck fileHeadCheck;
	
	private String filepath = FilepathEnum.EmergeFilePath.getPath();
	/**
	 * 开始生成文件，做验证文件和生成文件
	 */
	public String startCreateFile(MultipartFile template, MultipartFile[] files, String username) throws FileOperateException {
		
		fileTypeCheck = new FileTypeCheck();
		fileHeadCheck = new FileHeadCheck();
		int xlsx = fileTypeCheck.isXLSX(template, files);
		
		String uuid = null;
		if(xlsx == 1) {
			//模板文件表头
			List<List<String>> heads = fileHeadCheck.isContainsHead(template, files);
			if(!heads.isEmpty()) {
				uuid = this.createFile(heads,template,files, username);
			}
		}
		return uuid;
	}
	
	/**
	 * 生成文件，同时返回文件名
	 */
	private String createFile(List<List<String>> heads ,MultipartFile template, MultipartFile[] files, String username) throws FileOperateException {
		String uuid = UUID.randomUUID().toString();
		String newFilepath = filepath+username+"/"+ uuid +".xlsx";
		
		try {
			FileUtils.copyInputStreamToFile(template.getInputStream(), new File(newFilepath));
		} catch (IOException e) {
			e.printStackTrace();
			throw new FileOperateException("保存模板文件失败");
		}
		
		//合成文件到模板文件
		List<String> templateHead = heads.get(0);
		for(int i=1; i<heads.size(); i++) {
			List<String> fileHead = heads.get(i);
			int[] position = this.getPosition(templateHead, fileHead);
			for(MultipartFile file : files) {
				this.mergeFiles(position, file, newFilepath);
				System.out.println(file.getOriginalFilename()+" ： 合并成功--CreateFile:71");
			}
		}
		
		return uuid;
	}
	
	/**
	 * 合成文件
	 */
	private void mergeFiles(int[] position, MultipartFile file, String newFilepath) throws FileOperateException {
		//读取保存本地的文件
		XSSFWorkbook xssfWorkbook = null;
		XSSFSheet sheetAt = null;
		XSSFRow row = null;
		XSSFCell cell = null;
		InputStream in = null;
		FileOutputStream fos = null;
		
		//读取用于生成文件的文件
		XSSFWorkbook filexssfWorkbook = null;
		XSSFSheet fileSheet = null;
		XSSFRow fileRow = null;
		XSSFCell fileCell = null;
		InputStream fisNew = null;
		
		File newFile = new File(newFilepath);
		
		try {
			//获取保存在本地的文件有多少行
			in = new FileInputStream(newFile);
			xssfWorkbook = new XSSFWorkbook(in);
			sheetAt = xssfWorkbook.getSheetAt(0);
			int lastRowNum = sheetAt.getLastRowNum();
			
			//读取用于生成的文件
			fisNew = file.getInputStream();
			filexssfWorkbook = new XSSFWorkbook(fisNew);
			fileSheet = filexssfWorkbook.getSheetAt(0);
			int rowNum = fileSheet.getLastRowNum();
			
			//写入文件
			for(int i = 1; i<=rowNum; i++) {
				fileRow = fileSheet.getRow(i);						//获取源文件第i行
				row = sheetAt.createRow(lastRowNum+i);				//新生成文件创建一行
				
				for(int j = 0; j<position.length; j++) {
					fileCell = fileRow.getCell(position[j]);		//获取源文件第position[j]列
					fileCell.setCellType(CellType.STRING);
					String cellValue = fileCell.getStringCellValue();
					
					//写入
					cell = row.createCell(j);						//新生成文件创建第j列
					cell.setCellType(CellType.STRING);
					cell.setCellValue(cellValue);
					
					fos = new FileOutputStream(newFile);
					xssfWorkbook.write(fos);
					fos.flush();
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			throw new FileOperateException("合并文件失败");
		}finally {
			this.close(filexssfWorkbook,xssfWorkbook, in, fos);
		}
	}
	
	
	/**
	 * 获取表头位置
	 */
	private int[] getPosition(List<String> templateHead, List<String> fileHead) {
		int[] position = new int[templateHead.size()];
		for(int i = 0; i<templateHead.size(); i++) {
			int j = 0;
			while(j < fileHead.size()) {
				if(templateHead.get(i).equals(fileHead.get(j))) {
					break;
				}else {
					j++;
				}
			}
			position[i] = j;
		}
		return position;
	}
	
	
	/**
	 * 关闭资源
	 */
	private void close(XSSFWorkbook filexssfWorkbook,XSSFWorkbook xssfWorkbook, InputStream in, FileOutputStream fos) {
		try {
			if(xssfWorkbook!=null) {
				xssfWorkbook.close();
			}
			if(in!=null) {
				in.close();
			}
			if(fos!=null) {
				fos.close();
			}
		} catch (IOException e) {
			System.out.println("CreateFile方法关闭资源失败");
			e.printStackTrace();
		}
	}
}
