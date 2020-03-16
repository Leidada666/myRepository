package cn.czxy.check;

import org.springframework.web.multipart.MultipartFile;

public class FileCheck {
	/**
	 * 结果：
	 * 0 : 成功
	 * 1 : 模板文件失败
	 * 2 : files的第x-2个
	 */
	public int isXLSX(MultipartFile file, MultipartFile[] files) {
		String filename = file.getOriginalFilename();
		String suffix = filename.substring(filename.lastIndexOf("."));
		if(!suffix.equals(".xlsx")) {
			//模板文件失败
			return 1;
		}else {
			for(int i = 0; i<files.length; i++) {
				filename = files[i].getOriginalFilename();
				suffix = filename.substring(filename.lastIndexOf("."));
				if(suffix.equals(".xlsx")) {
					continue;
				}else {
					return i+2;
				}
			}
		}
		return 0;
	}
	
	/**
	 * 0 : 成功
	 * 1 - .. : 第x-1个失败
	 */
	public int isXLSX(MultipartFile[] files){
		String filename = null;
		String suffix = null;
		for(int i = 0; i<files.length; i++) {
			filename = files[i].getOriginalFilename();
			suffix = filename.substring(filename.lastIndexOf("."));
			if(suffix.equals(".xlsx")) {
				continue;
			}else {
				return i+1;
			}
		}
		return 0;
	}
}
