package com.senyint.wx.common.utils;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.senyint.wx.admin.dao.ArgumentDao;
import com.senyint.wx.admin.entity.Argument;

//@Service
public class ArgumentUtil  {
//	@Autowired
//	private  ArgumentDao argumentDao;
	public String getSysVal(String key){
		WebApplicationContext c =  ContextLoader.getCurrentWebApplicationContext();
		ArgumentDao argumentDao = c.getBean(ArgumentDao.class);
		Conjunction con = Restrictions.and();
		con.add(Restrictions.eq("name", key));
		List<Argument> argumentList= argumentDao.findByCriteria(false, con) ;
		Argument argument = argumentList.get(0);
		return argument.getValue();	
	}
	
	public static Date getSysDate() {
		WebApplicationContext c =  ContextLoader.getCurrentWebApplicationContext();
		ArgumentDao argumentDao = c.getBean(ArgumentDao.class);
		return argumentDao.getSysDate();
	}

}
