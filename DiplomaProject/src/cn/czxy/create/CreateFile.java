package cn.czxy.create;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

public class CreateFile {
	//filepath + filename + ".xlsx"生成文件的路径
	private String filepath = "";
	
	private XSSFWorkbook xssfWorkbook = null;
	private XSSFSheet sheetAt = null;
	private XSSFRow row = null;
	private XSSFCell cell = null;
	private InputStream in = null;
	private FileOutputStream fos = null;
	
	
	/**
	 *	根据ip地址生成文件夹 
	 */
	private void createFolder(String addr) {
		String folder = "D://DiplomaProject/";
		
		if(addr.equals("0:0:0:0:0:0:0:1")) {
			folder = folder + "local";
		}else {
			folder = folder + addr;
		}
		File file = new File(folder);
		if(!file.exists()) {
			file.mkdir();
		}

		filepath = folder + "/";
//		System.out.println(filepath);
	}
	
	/**
	 * 生成是否成功
	 * 1:成功
	 * 0:失败
	 */
	public Map<String, List<String>> isSuccess(MultipartFile template, MultipartFile[] files, String filename, String addr) {
		//创建ip文件夹
		createFolder(addr);
		//返回
		Map<String, List<String>> map = new HashMap<>();
		if(!map.isEmpty()) {
			map.clear();
		}
		//信息
		List<String> message = new ArrayList<String>();
		if(!message.isEmpty()) {
			message.clear();
		}
		//结果(1:成功；0:失败)
		List<String> result = new ArrayList<String>();
		if(!result.isEmpty()) {
			result.clear();
		}
		//获取模板文件表头
		List<String> templateHead = null;
		try {
			templateHead = readHead(template);
		}catch(NullPointerException e) {
			message.add("模板文件不符合规定");
			if(result.isEmpty()) {
				result.add("0");
			}else {
				result.clear();
				result.set(0, "0");
			}
			map.put("result", result);
			map.put("message", message);
			return map;
		}
		if(templateHead == null) {
			message.add("模板文件不符合规定");
			if(result.isEmpty()) {
				result.add("0");
			}else {
				result.clear();
				result.set(0, "0");
			}
			map.put("result", result);
			map.put("message", message);
			return map; 
		}
		//生成新文件,只包含模板文件头
		int createNewHead = createNewHead(templateHead, filename);
		
		if(createNewHead==1) {
			//判断源文件是否包含模板文件表头
			for(MultipartFile file : files) {
				//读取文件表头
				List<String> fileHead = null;
				try {
					fileHead = readHead(file);
				}catch(NullPointerException e) {
					message.add(file.getOriginalFilename()+"文件为空");
					continue;
				}
				if(fileHead == null) {
					message.add(file.getOriginalFilename()+"文件为空");
					continue;
				}
				//判断file是否包含模板内容
				boolean containsAll = fileHead.containsAll(templateHead);
				//生成文件
				if(containsAll) {
					//获取模板表头在文件中的位置
					int[] position = getPosition(templateHead, fileHead);
					int createNewFile = createNewFile(position, file, filename);
					if(createNewFile==1) {
						String mes = file.getOriginalFilename()+"生成成功";
						message.add(mes);
					}else {
						String mes = file.getOriginalFilename()+"生成失败";
						message.add(mes);
					}
				}else {
					String mes = file.getOriginalFilename()+"不包含模板信息";
					message.add(mes);
				}
			}
		}else {
			close(xssfWorkbook, in);
			message.add("创建文件失败");
			if(result.isEmpty()) {
				result.add("0");
			}else {
				result.clear();
				result.set(0, "0");
			}
			map.put("result", result);
			map.put("message", message);
			return map;
		}
		close(xssfWorkbook, in);
		message.add("创建文件成功");
		if(result.isEmpty()) {
			result.add("1");
		}else {
			result.set(0, "1");
		}
		List<String> filepath1 = new ArrayList<String>();
		if(filepath1.isEmpty()) {
			filepath1.add(filepath+filename+".xlsx");
		}else {
			filepath1.clear();
			filepath1.add(filepath+filename+".xlsx");
		}
		map.put("result", result);
		map.put("message", message);
		map.put("filepath", filepath1);
		return map;
	}
	
	/**
	 * 读取文件表头
	 * @param template
	 * @return
	 */
	private List<String> readHead(MultipartFile file){
		List<String> headOfTemplate = new ArrayList<String>();
		try {
			in = file.getInputStream();
			xssfWorkbook = new XSSFWorkbook(in);
			sheetAt = xssfWorkbook.getSheetAt(0);//假设只有一张表
			int firstRowNum = sheetAt.getFirstRowNum();
			row = sheetAt.getRow(firstRowNum);
			short lastCellNum = row.getLastCellNum();
			for(int i = 0; i<lastCellNum; ++i) {
				cell = row.getCell(i);
				cell.setCellType(CellType.STRING);
				String value = cell.getStringCellValue();
				headOfTemplate.add(value);
			}
		}catch(Exception e) {
			System.out.println("文件为空");
			return null;
		}
		return headOfTemplate;
	}
	
	/**
	 * 生成新文件头
	 * 1:成功;0:失败;
	 */
	private int createNewHead(List<String> templateHead, String filename) {
		File file = new File(filepath+filename+".xlsx");
		try {
			xssfWorkbook = new XSSFWorkbook();
			sheetAt = xssfWorkbook.createSheet();
			row = sheetAt.createRow(0);
			
			for(int i = 0; i < templateHead.size(); ++i) {
				cell = row.createCell(i);
				cell.setCellType(CellType.STRING);
				cell.setCellValue(templateHead.get(i));
			}
			
			fos = new FileOutputStream(file);
			xssfWorkbook.write(fos);
		}catch(Exception e) {
			e.printStackTrace();
			return 0;
		}finally {
			if(fos!=null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return 1;
	}
	
	/**
	 * 获取模板文件表头在文件中的位置
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
	 * 生成文件
	 * 1:成功; 0:失败;
	 */
	private int createNewFile(int[] position, MultipartFile file, String filename) {
		
		//读取源文件
		XSSFWorkbook filexssfWorkbook = null;
		XSSFSheet fileSheet = null;
		XSSFRow fileRow = null;
		XSSFCell fileCell = null;
		InputStream fisNew = null;
		
		File newFile = new File(filepath+filename+".xlsx");
		try {
			//获取新生成的文件有多少行
			in = new FileInputStream(newFile);
			xssfWorkbook = new XSSFWorkbook(in);
			sheetAt = xssfWorkbook.getSheetAt(0);
			int lastRowNum = sheetAt.getLastRowNum();
			
			//读取源文件
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
			return 0;
		}finally {
			if(filexssfWorkbook!=null) {
				try {
					filexssfWorkbook.close();
					fisNew.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return 1;
	}
	
	/**
	 * 关闭资源
	 * @param xssfWorkboos
	 * @param in
	 * @return
	 */
	private int close(XSSFWorkbook xssfWorkboos, InputStream in) {
		try {
			if(xssfWorkbook!=null) {
				xssfWorkbook.close();
			}
			if(in!=null) {
				in.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
}
