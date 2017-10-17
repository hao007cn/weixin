package com.senyint.wx.admin.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.senyint.wx.admin.service.ArticleTypeService;
import com.senyint.wx.common.dao.ArticleTypeDao;

@Service
public class ArticleTypeServiceImpl implements ArticleTypeService {
	@Autowired
	private ArticleTypeDao articleTypeDao;

	
}
