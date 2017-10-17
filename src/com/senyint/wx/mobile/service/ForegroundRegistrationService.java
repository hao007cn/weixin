package com.senyint.wx.mobile.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.senyint.wx.common.entity.ForegroundUser;
import com.senyint.wx.common.entity.ForegroundUserInHis;
import com.senyint.wx.common.entity.UserRelation;
import com.senyint.wx.common.entity.UserRelevance;


public interface ForegroundRegistrationService {
	
	/**
	 * setUserInfo2Session
	 * @param openid
	 * @param session
	 */
	void setUserInfo2Session(String openid,HttpSession session)throws Exception;
	
	/**
	 * 向用户和his关系表添加一条数据
	 * @param userRelevance
	 * @return
	 */
	UserRelevance addNewUserRelevan(String openid, String patiendId);
	
	/**
	 * his中有身份证：向前台数据表插入数据
	 * @param cardId
	 * @param userName
	 * @return
	 */
	void addNewUser2Foreground(String cardId,String userName,String openid)throws Exception;
	
	/**向his添加新信息
	 * @param cardId
	 * @param tel
	 * @param openid
	 * @param userName
	 * @param patientId
	 */
	void addNewUser2His(String cardId , String tel,String openid,String userName,Long patientId) throws Exception;
	
	
	/**
	 * 根据openid查询patientId
	 * @param openid
	 * @return
	 */
	Long getPatientIdByOpenid(String openid);
	
	/**
	 * 根据openid获取UserRelevance
	 * @param openid
	 * @return
	 */
	UserRelevance getuserUserRelationByOpenid(String openid);
	
	/**
	 * 验证关系中是否有opendid
	 * @param openid
	 * @return
	 */
	boolean isHasOpenIdInUserRelevance(String openid);
	
	/**获取 inhis中的用户信息
	 * @param cardId
	 * @param userName
	 * @return
	 */
	ForegroundUserInHis getForegroundUserInHis(String cardId,String userName)throws Exception;
	
	
	/**
	 * foregroundUserInHis2ForegroundUser
	 * @param inHis
	 * @return
	 */
	ForegroundUser foregroundUserInHis2ForegroundUser(ForegroundUserInHis inHis);
	
	/**
	 * ForegroundUser2ForegroundUserInHis
	 * @param fu
	 * @return
	 */
	ForegroundUserInHis ForegroundUser2ForegroundUserInHis(ForegroundUser fu);
	/**
	 * poid批量查询his用户
	 * @param list
	 * @return
	 */
    List<ForegroundUser> batchFindForegroundUserById(List<UserRelation> list)throws Exception;
    /**
     * 子用户保存HIS
     * @param patientId
     * @param cardId
     * @param tel
     * @param userName
     * @return
     */
    void addNewSubUser2His(Long PatientId,Long subPatientId,String cardId,String tel,String userName)throws Exception;
    
}
