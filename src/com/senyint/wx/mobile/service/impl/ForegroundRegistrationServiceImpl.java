package com.senyint.wx.mobile.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.senyint.common.util.IdcardInfoExtractor;
import com.senyint.wx.common.dao.HisAccessDao;
import com.senyint.wx.common.dao.UserRelationDao;
import com.senyint.wx.common.dao.UserRelevanceDao;
import com.senyint.wx.common.entity.ForegroundUser;
import com.senyint.wx.common.entity.ForegroundUserInHis;
import com.senyint.wx.common.entity.UserRelation;
import com.senyint.wx.common.entity.UserRelevance;
import com.senyint.wx.common.utils.ArgumentUtil;
import com.senyint.wx.common.web.Constants;
import com.senyint.wx.mobile.service.ForegroundRegistrationService;

@Service
@Transactional
public class ForegroundRegistrationServiceImpl implements
		ForegroundRegistrationService {
	@Autowired
	private HisAccessDao hisAccessDao;

	@Autowired
	private UserRelevanceDao userRelevanceDao;

	@Autowired
	private UserRelationDao userRelationDao;

	/**
	 * setUserInfo2Session
	 * 
	 * @param openid
	 * @param session
	 * @throws Exception 
	 */
	public void setUserInfo2Session(String openid, HttpSession session) throws Exception {
		UserRelevance ur = getuserUserRelationByOpenid(openid);
		ForegroundUserInHis fh = hisAccessDao
				.queryForegroundUserInHisById(String.valueOf(ur.getPatientId()));
		fh.setOpenId(ur.getOpenId());
		fh.setPoid(ur.getPoid());
		ForegroundUser fu = foregroundUserInHis2ForegroundUser(fh);
		session.setAttribute(Constants.SESSION_TOP_USER_INFO_KEY, fu);
	}

	/**
	 * 向用户和his关系表添加一条数据
	 * 
	 * @param userRelevance
	 * @return
	 */
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public UserRelevance addNewUserRelevan(String openid, String patientId) {
		UserRelevance userRelevance = new UserRelevance();
		userRelevance.setPatientId(Long.valueOf(patientId));
		userRelevance.setOpenId(openid);
		   return userRelevanceDao.makePersistent(userRelevance);
	}

	/**
	 * his中有身份证：向前台数据表插入数据
	 * 
	 * @param cardId
	 * @param userName
	 * @return
	 * @throws Exception 
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addNewUser2Foreground(String cardId, String userName,
			String openid) throws Exception {
		boolean flag = false;
		ForegroundUserInHis foregroundUserInHis = getForegroundUserInHis(
				cardId, userName);
		ForegroundUser fu = foregroundUserInHis2ForegroundUser(foregroundUserInHis);
		fu.setOpenid(openid);
		// 是否锁住
		fu.setLocked(false);
		// 注册时间
		fu.setRegdate(ArgumentUtil.getSysDate());
		// 向前台用户插入数据
		//foregroundUserDao.makePersistent(fu);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addNewUser2His(String cardId , String tel,String openid,String userName,Long patientId) throws Exception{
			Date birthday = hisAccessDao.getUserBirthDay(cardId);
			String age = hisAccessDao.getUserAge(String.valueOf(patientId), birthday, ArgumentUtil.getSysDate());
			//String payType = "05";
			ForegroundUserInHis fh = new ForegroundUserInHis();
			fh.setPatientId(patientId);
			fh.setBirthday(birthday);
			fh.setCardId(cardId);
			fh.setAge(String.valueOf(age));
			fh.setSex(IdcardInfoExtractor.getGenger(cardId,0));
			fh.setName(userName);
			String payTypeName = hisAccessDao.genericPayTypeName(Constants.PAY_TYPE);
			fh.setPayType(payTypeName);
			fh.setHouseTel(tel);
			hisAccessDao.insertNewUser2His(fh);
	}
	/**
	 * 向his添加子用户信息
	 * 
	 * @return
	 * @throws Exception 
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addNewSubUser2His(Long PatientId, Long subPatientId,
			String cardId, String tel, String userName) throws Exception {
		UserRelation ur = new UserRelation();
		ur.setPatiendChildId(subPatientId);
		ur.setPatientId(PatientId);
		userRelationDao.makePersistent(ur);
		Date birthday = hisAccessDao.getUserBirthDay(cardId);
		String age = hisAccessDao.getUserAge(String.valueOf(subPatientId),
				birthday, ArgumentUtil.getSysDate());
		ForegroundUserInHis fh = new ForegroundUserInHis();
		fh.setPatientId(subPatientId);
		fh.setBirthday(birthday);
		fh.setCardId(cardId);
		fh.setAge(String.valueOf(age));
		fh.setSex(IdcardInfoExtractor.getGenger(cardId, 0));
		fh.setName(userName);
		fh.setPayType(Constants.PAY_TYPE);
		fh.setHouseTel(tel);
		hisAccessDao.insertNewUser2His(fh);
	}

	/**
	 * 根据openid查询patientId
	 * 
	 * @param openid
	 * @return
	 */
	public Long getPatientIdByOpenid(String openid) {
		Conjunction con = Restrictions.and();
		con.add(Restrictions.eqOrIsNull("openId", openid));
		UserRelevance relevance = userRelevanceDao.findByCriteria(false, con)
				.get(0);
		return relevance.getPatientId();
	}


	@Override
	public UserRelevance getuserUserRelationByOpenid(String openid) {
		Conjunction con = Restrictions.and();
		con.add(Restrictions.eq("openId", openid));
		 List<UserRelevance> relevanceList = userRelevanceDao.findByCriteria(false, con);
				if(relevanceList != null && relevanceList.size() > 0 ){
					return relevanceList.get(0);
				}else{
					return null;
				}
	}

	/**
	 * 获取 inhis中的用户信息
	 * 
	 * @param cardId
	 * @param userName
	 * @return
	 * @throws Exception 
	 */
	public ForegroundUserInHis getForegroundUserInHis(String cardId,
			String userName) throws Exception {
		ForegroundUserInHis foregroundUserInHis = hisAccessDao
				.queryForegroundUserInHis(cardId, userName);
		return foregroundUserInHis;
	}

	/**
	 * foregroundUserInHis2ForegroundUser
	 * 
	 * @param inHis
	 * @return
	 */
	public ForegroundUser foregroundUserInHis2ForegroundUser(
			ForegroundUserInHis inHis) {
		ForegroundUser fr = new ForegroundUser();
		// 身份证
		fr.setCardid(inHis.getCardId());

		// 删除标志
		fr.setDeleteFlag(false);
		// 移动电话
		fr.setMobile(inHis.getHouseTel());
		// 名字
		fr.setName(inHis.getName());
		// 性别
		fr.setSex(inHis.getSex());
		fr.setPatietId(inHis.getPatientId());
		// openId
		fr.setOpenid(inHis.getOpenId());
		// poid
		fr.setPoid(inHis.getPoid());
		return fr;
	}

	/**
	 * ForegroundUser2ForegroundUserInHis
	 * 
	 * @param fu
	 * @return
	 */
	public ForegroundUserInHis ForegroundUser2ForegroundUserInHis(
			ForegroundUser fu) {
		ForegroundUserInHis fh = new ForegroundUserInHis();
		// hisid
		Long patientId = getPatientIdByOpenid(fu.getOpenid());
		fh.setPatientId(patientId);
		// 身份证
		fh.setCardId(fu.getCardid());
		// 名字
		fh.setName(fu.getName());
		// 手机
		fh.setHouseTel(fu.getMobile());
		// 支付方式
		fh.setPayType("05");
		// 性别
		fh.setSex(fu.getSex());
		return fh;
	}


	public List<ForegroundUser> batchFindForegroundUserById(
			List<UserRelation> list) throws Exception {

		List<ForegroundUser> foregroundUserList = new ArrayList<ForegroundUser>();

		for (UserRelation userRelation : list) {
			Long patiendid = userRelation.getPatiendChildId();
			ForegroundUserInHis fh = hisAccessDao
					.queryForegroundUserInHisById(patiendid.toString());
			foregroundUserList.add(foregroundUserInHis2ForegroundUser(fh));
		}

		return foregroundUserList;
	}

	@Override
	public boolean isHasOpenIdInUserRelevance(String openid) {
//		boolean flag = false;
//		try {
//			foreg
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			// TODO: handle exception
//		}
		return false;
	}
	
}
