package com.czxy.filepath;

public enum FilepathEnum {
	EmergeFilePath("D:/DiplomaProject/files/");
	
	private String path;
	
	private FilepathEnum(String path) {
		this.path = path;
	}
	public String getPath() {
		return path;
	}
}
