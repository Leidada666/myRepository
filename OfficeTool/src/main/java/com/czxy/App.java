package com.czxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.czxy.interceptor.IsLoginInterceptor;

@SpringBootApplication
@EnableCaching
@EnableScheduling
public class App implements WebMvcConfigurer {
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
	
	//注册拦截器
	public void addInterceptors(InterceptorRegistry registry) {
		InterceptorRegistration isLogin = registry.addInterceptor(new IsLoginInterceptor());
		isLogin.addPathPatterns("/fileToFile/**","/fileToPie/**","/if/**");
//		isLogin.excludePathPatterns("/OfficeTool","/loginValidate/login","/index.html","/userinfo/**","/pieChart/**","/main/**","/error/**","/createFile/**");
	}
}
