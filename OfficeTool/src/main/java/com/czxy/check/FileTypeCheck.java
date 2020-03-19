package com.czxy.check;

import org.springframework.web.multipart.MultipartFile;

import com.czxy.exception.FileOperateException;

/**
 * 判断文件类型是否为xlsx
 * 返回值为1时表示格式符合要求
 */
public class FileTypeCheck {
	public int isXLSX(MultipartFile template, MultipartFile[] files) throws FileOperateException {
		if(!".xlsx".equals(this.getType(template))){
			System.out.println("FileTypeCheck模板文件格式不符合要求");
			throw new FileOperateException("模板文件格式不符合要求");
		}else {
			for(MultipartFile file : files) {
				if(!".xlsx".equals(this.getType(file))){
					throw new FileOperateException(file.getOriginalFilename()+" : 格式不符合要求");
				}
			}
		}
		return 1;
	}
	/**
	 * 获取文件类型
	 */
	private String getType(MultipartFile file) throws FileOperateException {
		String suffix = null;
		try {
			String filename = file.getOriginalFilename();
			suffix = filename.substring(filename.lastIndexOf("."));
		}catch(StringIndexOutOfBoundsException sioobe) {
			throw new FileOperateException("请选择文件");
		}
		
		return suffix;
	}
}
