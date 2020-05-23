package com.czxy.util;

import java.io.File;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.czxy.filepath.FilepathEnum;

@Component
public class ScheduledUtil {
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	private final static String filepath = FilepathEnum.EmergeFilePath.getPath();
	
	private File dir;
	
	private Set<String> keys;
	
	@Scheduled(cron="59 59 23 * * ?")
	public void scheduledMethod() {
		System.out.println("触发器被执行");
		keys = this.redisTemplate.keys("*");
		if(keys.isEmpty() || keys.size() == 0) {
			System.out.println("缓存为空");
		}else {
			for(String s : keys) {
				System.out.println(keys);
				this.redisTemplate.delete(s);
			}
		}
		
		dir = new File(filepath);
		if(!dir.exists()) {
			System.out.println("文件夹不存在");
		}else {
			if(dir.isDirectory()) {
				for(File file : dir.listFiles()) {
					if(!file.getName().equals("ls521")) {
						file.delete();
					}
				}
			}
		}
	}
}
