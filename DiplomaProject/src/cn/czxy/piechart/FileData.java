package cn.czxy.piechart;

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

public class FileData {
	private XSSFWorkbook xssfWorkbook = null;
	private XSSFSheet sheetAt = null;
	private XSSFRow row = null;
	private XSSFCell cell = null;
	private InputStream in = null;
	

	
	/**
	 * 1、获取文件表头，以第一个文件为模板；
	 * 2、返回的list的第一个数据，用来存储这些文件的格式是否一致（1：一致；2：不一致）；
	 * 3、最后一个数据用来存储哪个文件格式不符合第一个文件,若都符合，则没有这一项；
	 * @return
	 */
	public List<String> getTitle(MultipartFile[] files) {
		//title的第一个数据用来存储这些文件的格式是否一致；（1：一致；2：不一致）
		List<String> title = new ArrayList<String>();
		if(!title.isEmpty()) {
			title.clear();
		}
		title.add("1");//假设文件格式一致；
		
		try {
			in = files[0].getInputStream();
			xssfWorkbook = new XSSFWorkbook(in);
			sheetAt = xssfWorkbook.getSheetAt(0);
			row = sheetAt.getRow(0);
			short lastCellNum = row.getLastCellNum();
			for(int i = 0; i<lastCellNum; i++) {
				cell = row.getCell(i);
				if(cell==null) {
					continue;
				}
				cell.setCellType(CellType.STRING);
				String value = cell.getStringCellValue();
//				System.out.println("value:"+value+":"+value.isEmpty());
				//判断字符串是否为空
				if(value.isEmpty()) {
					continue;
				}else {
					title.add(value);
				}
			}
			
			int length = files.length;
			if(length<=1) {
//				System.out.println("文件只有一个");
				return title;
			}else {
				//判断格式是否相同
//				System.out.println("文件有多个");
				for(int i = 1; i<files.length; i++) {
					int same = isSame(title, files[i]);
					if(same == 1) {
						continue;
					}else {
						title.set(0, "0");
						title.add(files[i].getOriginalFilename()+"格式不一致");
						return title;
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(xssfWorkbook!=null) {
					xssfWorkbook.close();
				}
				if(in!=null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return title;
	}
	/**
	 * 1:相同；0:不同；
	 */
	private int isSame(List<String> title,MultipartFile file) {
		try {
			in = file.getInputStream();
			xssfWorkbook = new XSSFWorkbook(in);
			sheetAt = xssfWorkbook.getSheetAt(0);
			
			row = sheetAt.getRow(0);
			short lastCellNum = row.getLastCellNum();
			
			int size = title.size()-1;
			
			if(lastCellNum != size) {
				return 0;
			}
			
			for(int i = 0; i<lastCellNum; i++) {
				cell = row.getCell(i);
				cell.setCellType(CellType.STRING);
				String cellValue = cell.getStringCellValue();
//				System.out.println(cellValue + " : " + title.get(i+1));
				if(title.get(i+1).equals(cellValue)) {
					continue;
				}else {
					return 0;//格式不一致
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(xssfWorkbook!=null) {
					xssfWorkbook.close();
				}
				if(in!=null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//检查完成，格式一致
		return 1;
	}
	
	private static int FIRST_ROW = 0;	//从第一行开始
	private static int SECOND_ROW = 1;	//从第二行开始
	/**
	 * 根据标题下标值读取文件内容
	 */
	public List<List<String>> getFileData(MultipartFile[] files, int[] titles){
		//存放结果
		List<List<String>> finalList = new ArrayList<>();
		
		//存放结果的list是否为空
		if(!finalList.isEmpty()) {
			finalList.clear();
		}
		int firstRow = SECOND_ROW;	//从第几行开始读取
		
		try {
			in = null;
			xssfWorkbook = null;
			//循环读取文件
			for(int x = 0; x < files.length; x++) {
				in = files[x].getInputStream();
				xssfWorkbook = new XSSFWorkbook(in);
				sheetAt = xssfWorkbook.getSheetAt(0);
				int lastRowNum = sheetAt.getLastRowNum();
				
				if(x==0) {
					//第一个文件从第0行读取，将表头读取出来
					firstRow = FIRST_ROW;
				}else {
					//其余文件从第1行读取，不读取表头
					firstRow = SECOND_ROW;
				}
				
				//从第二行开始读取
				for(int i=firstRow; i<=lastRowNum; i++) {
					//一行数据(注意，如果使用同一个list进行存储，数据会被覆盖，因为指向的是同一个内存空间)
					List<String> list = new ArrayList<>();
					
					row = sheetAt.getRow(i);
					//根据titles的下标，读取相应的列
					for(int j : titles) {
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
					//最终结果
					finalList.add(list);
					
					//将list清空，方便垃圾回收机制回收
					list=null;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}finally {
			try {
				if(xssfWorkbook != null) {
					xssfWorkbook.close();
				}
				if(in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//返回最终结果
		return finalList;
	}
}
