package cn.czxy.test;

import java.io.File;

public class Test {
	public static void main(String[] args) {
		File file = new File("D://192.168.0.107");
		if(!file.exists()) {
			file.mkdir();
		}
	}
}
