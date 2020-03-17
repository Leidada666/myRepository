package com.czxy.check;

import org.springframework.web.multipart.MultipartFile;

import com.czxy.exception.FileToFileException;

/**
 * 判断文件类型是否为xlsx
 * 返回值为1时表示格式符合要求
 */
public class FileTypeCheck {
	public int isXLSX(MultipartFile template, MultipartFile[] files) throws FileToFileException {
		if(!".xlsx".equals(this.getType(template))){
			throw new FileToFileException("模板文件格式不符合要求");
		}else {
			for(MultipartFile file : files) {
				if(!".xlsx".equals(this.getType(file))){
					throw new FileToFileException(file.getOriginalFilename()+" : 格式不符合要求");
				}
			}
		}
		return 1;
	}
	/**
	 * 获取文件类型
	 */
	private String getType(MultipartFile file) {
		String filename = file.getOriginalFilename();
		String suffix = filename.substring(filename.lastIndexOf("."));
		return suffix;
	}
}
