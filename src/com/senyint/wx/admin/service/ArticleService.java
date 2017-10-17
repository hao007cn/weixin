package com.senyint.wx.admin.service;

import java.util.List;

import com.senyint.wx.common.entity.ArticleType;

public interface ArticleService {
	/**
	 * 返回菜单列表
	 * @param parentFlag
	 * @return
	 */
	List<ArticleType>  getArticleList(Integer parentFlag); 
}
