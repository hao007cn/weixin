package com.senyint.wx.admin.service.impl;
import java.util.List;

import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.senyint.wx.admin.service.ArticleService;
import com.senyint.wx.common.dao.ArticleDao;
import com.senyint.wx.common.dao.ArticleTypeDao;
import com.senyint.wx.common.entity.ArticleType;

@Service
public class ArticleServiceImpl implements ArticleService {
	
	@Autowired
	private ArticleDao articleDao;
	
	@Autowired
	private ArticleTypeDao articleTypeDao;

	@Override
	public List<ArticleType> getArticleList(Integer parentFlag) {
		Conjunction con = Restrictions.and();
		con.add(Restrictions.eq("deleteFlg",false));
		con.add(Restrictions.eq("parentflg", parentFlag));
		List<ArticleType> articleTypeList = articleTypeDao.findByCriteria(false, con);
		return articleTypeList;
	}
	
}
