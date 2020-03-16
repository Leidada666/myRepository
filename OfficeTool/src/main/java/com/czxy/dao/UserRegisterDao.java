package com.czxy.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.czxy.pojo.User;

/**
 * 用户注册
 */
public interface UserRegisterDao extends JpaRepository<User, Integer>{

}
