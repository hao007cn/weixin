package com.senyint.wx.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.senyint.wx.admin.dao.UserDao;

@Service
@Transactional
public class UserService {
	
	@Autowired
	private UserDao userDao;
	
	
	
}
