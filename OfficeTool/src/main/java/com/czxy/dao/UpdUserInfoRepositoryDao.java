package com.czxy.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.czxy.pojo.User;
/**
 * 修改用户信息
 */
public interface UpdUserInfoRepositoryDao extends Repository<User, Integer> {
	//修改用户信息
	@Query("update User set username= ?1, telephone= ?2 where id = ?3")
	@Modifying
	void updateUserInfo(String username, String telephone, Integer id);
}
