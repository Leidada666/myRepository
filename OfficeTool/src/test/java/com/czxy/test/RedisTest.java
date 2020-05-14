package com.czxy.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.czxy.App;
import com.google.gson.Gson;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=App.class)
public class RedisTest {
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Test
	public void test() {
		List<String> list = new ArrayList<>();
		list.add("hello");
		list.add("world");
		list.add("!");
		Gson gson = new Gson();
		String json = gson.toJson(list);
		this.redisTemplate.opsForValue().set("zs", json);
	}
	
	@Test
	public void testGet() {
		Boolean hasKey = this.redisTemplate.hasKey("ls");
		System.out.println(hasKey);
//		Gson gson = new Gson();
//		String object = (String) this.redisTemplate.opsForValue().get("zs");
//		List<String> list = gson.fromJson(object, List.class);
//		if(list.isEmpty()) {
//			
//			System.out.println("null");
//		}{
//			for(String l : list) {
//				System.out.println(l);
//			}
//		}
		
	}
}
