package com.czxy.exception;

/**
 * 文件处理过程中发生的错误
 */
public class FileToFileException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String msg;

	public FileToFileException(String msg) {
		super();
		this.msg = msg;
	}

	public FileToFileException() {
		super();
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
