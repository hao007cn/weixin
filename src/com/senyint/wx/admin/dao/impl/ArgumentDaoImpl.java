package com.senyint.wx.admin.dao.impl;
import java.util.Date;

import org.springframework.stereotype.Repository;

import com.senyint.common.util.DateUtils;
import com.senyint.core.dao.impl.GenericDaoImpl;
import com.senyint.wx.admin.dao.ArgumentDao;
import com.senyint.wx.admin.entity.Argument;

@Repository
public class ArgumentDaoImpl extends GenericDaoImpl<Argument, Integer> implements ArgumentDao {

	@Override
	public Date getSysDate() {
		String dateStr = (String) this.getEntityManager().createNativeQuery("SELECT to_CHAR(SYSDATE,'yyyy-MM-dd HH24:mi:ss') FROM DUAL").getSingleResult();
		return DateUtils.strToDate(dateStr, "yyyy-MM-dd HH:mm:ss");
	}

}
