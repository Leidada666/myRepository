package com.czxy.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czxy.dao.UpdUserInfoRepositoryDao;
import com.czxy.service.UpdUserInfoService;

@Service
@Transactional
public class UpdUserInfoServiceImpl implements UpdUserInfoService {
	@Autowired
	private UpdUserInfoRepositoryDao updUserInfoRepositoryDao;
	
	@Override
	public void updateUserInfo(String username, String telephone, Integer id) {
		this.updUserInfoRepositoryDao.updateUserInfo(username, telephone, id);
	}
}
