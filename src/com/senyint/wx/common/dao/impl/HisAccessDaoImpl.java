/**
* @File: PacsReportDaoImpl.java
* @Package com.senyint.wx.mobile.dao.impl
* @Description: 
*
* @Company: senyint (Dalian) Co., Ltd
* @author   gjp
* @date     2014年12月22日
* @version  1.0
*/

package com.senyint.wx.common.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.senyint.common.util.DateUtils;
import com.senyint.common.util.IdcardInfoExtractor;
import com.senyint.core.dao.exception.AppRuntimeException;
import com.senyint.wx.common.dao.HisAccessDao;
import com.senyint.wx.common.entity.ForegroundUser;
import com.senyint.wx.common.entity.ForegroundUserInHis;
import com.senyint.wx.common.utils.ArgumentUtil;
import com.senyint.wx.common.web.Constants;
import com.senyint.wx.mobile.entity.OutpatientLisSampleInfo;

/**
 * ClassName:PacsReportDaoImpl
 *
 * @author   gjp
 * @version  
 * @since    Ver 1.1
 * @Date	 2014年12月22日
 *
 * @see 	 
 */
@Repository
public class HisAccessDaoImpl implements HisAccessDao{

	@Resource(name="hisEntityManagerFactory")
	private EntityManagerFactory hisEntityManagerFactory;
	
	private EntityManager getEntityManager(){
		return hisEntityManagerFactory.createEntityManager();
	}
	
	

	public HisAccessDaoImpl() {
		super();
	}
	
	public String getNextNo(String type) throws Exception {
		EntityManager em = getEntityManager();
		String result = null;
		try {
			Query q = em.createNativeQuery(" select nextno(?) from dual ");
			q.setParameter(1, type);
			result =  (String) q.getSingleResult();
		} catch (Exception e) {
			throw e;
		}finally{
			em.close();
		}
		return result;
	}
	
	@Override
	public String orderCheck(String patientId, String snumber, Date date) throws Exception {
		EntityManager em = getEntityManager();
		
		Query q = em.createNativeQuery("SELECT DL_三方预约挂号_CHECK(:patientId,:snumber,:date) FROM dual ");
		q.setParameter("patientId", patientId);
		q.setParameter("snumber", snumber);
		q.setParameter("date", date);

		try {
			String result =  (String) q.getSingleResult();
			return result;
		} catch (Exception e) {
			String message = ((java.sql.SQLException)((org.hibernate.exception.GenericJDBCException)((javax.persistence.PersistenceException) e).getCause()).getCause()).getMessage();
			if (message.indexOf("[ZLSOFT]+") != -1) {
				message = message.substring(message.indexOf("[ZLSOFT]+") + 9, message.indexOf("+[ZLSOFT]"));
				
				throw new AppRuntimeException("E001", message, e);
			}
			
			throw e;
		}finally{
			em.close();
		}
	}
	
	
	@Override
	public String getFormNo() throws Exception {
		return this.getNextNo("12");
	}
	
	@Override
	public boolean isHasCardId(String cardId,String userName) throws Exception {
		EntityManager em = getEntityManager();
		boolean flag = false;
		List objList = null;
		try {
			String sql = "select 病人信息.病人ID,病人信息.姓名,病人信息.身份证号 from 病人信息  where 病人信息.身份证号 = :cardId and 病人信息.姓名 = :userName ";
			Query q  =  em.createNativeQuery(sql);
			q.setParameter("cardId", cardId);
			q.setParameter("userName", userName);
			objList = q.getResultList();
			if(objList.size() >0){
				flag = true;
			}
		} catch (Exception e) {
			throw e;
		}finally{
			em.close();
		}
		return flag;
	}

	@Override
	public ForegroundUserInHis queryForegroundUserInHis(String cardId,String userName) throws Exception {
		EntityManager em = getEntityManager();
		//String sql = "select 病人信息.病人ID,病人信息.姓名,病人信息.身份证号 from 病人信息  where 病人信息.身份证号 = :cardId and 病人信息.姓名 = :userName ";
		String sql =  " SELECT his.病人id patientId,         " 
					+ " his.门诊号 clinicNum,                " 
					+ " his.姓名  NAME,                       " 
					+ " his.性别 sex,                        " 
					+ " his.年龄 age,                        " 
					+ " his.出生日期 birthday,               " 
					+ " his.费别 chargeType,                 " 
					+ " his.医疗付款方式 payType,            " 
					+ " his.国籍 nationality,                " 
					+ " his.民族 nation,                     " 
					+ " his.婚姻状况 marriage,               " 
					+ " his.职业 occupation,                 " 
					+ " his.身份证号 cardId,                 " 
					+ " his.工作单位 company,                " 
					+ " his.单位电话 companyTel,             " 
					+ " his.单位邮编 companyPostcode,        " 
					+ " his.家庭地址 houseAddress,           " 
					+ " his.家庭电话 houseTel,               " 
					+ " his.家庭地址邮编 housePostcode,      " 
					+ " his.登记时间 registrationTime,       " 
					+ " his.户口地址 accounts,               " 
					+ " his.户口地址邮编 accountPostcode			" 
					+ " From 病人信息  his             " 
					+ " where his.身份证号 = :cardId    " 
					+ " and his.姓名 = :userName        " ;
		List<ForegroundUserInHis> fuList = new ArrayList<ForegroundUserInHis>();
		try {
			Query q  =  em.createNativeQuery(sql);
			q.setParameter("cardId", cardId);
			q.setParameter("userName", userName);
			List<Object[]> results= q.getResultList();
			for(Object[] rs : results ){
				ForegroundUserInHis fu =  new ForegroundUserInHis();
				fu.setPatientId(Long.valueOf(rs[0].toString()));
				fu.setClinicNum(rs[1]==null?null:Long.valueOf(rs[1].toString()));
				fu.setName((String) rs[2]);
				fu.setSex((String) rs[3]);
				fu.setAge((String) rs[4]);
				fu.setBirthday((Date) rs[5]);
				fu.setChargeType((String) rs[6]);
				fu.setPayType((String) rs[7]);
				fu.setNationality((String) rs[8]);
				fu.setNation((String) rs[9]);
				fu.setMarriage((String) rs[10]);
				fu.setOccupation((String) rs[11]);
				fu.setCardId((String) rs[12]);
				fu.setCompany((String) rs[13]);
				fu.setCompanyTel((String) rs[14]);
				fu.setCompanyPostcode((String) rs[15]);
				fu.setHouseAddress((String) rs[16]);
				fu.setHouseTel((String) rs[17]);
				fu.setHousePostcode((String) rs[18]);
				fu.setRegistrationTime((Date) rs[19]);
				fu.setAccounts((String) rs[20]);
				fu.setAccountPostcode((String) rs[21]);
				fuList.add(fu);
			}
		} catch (Exception e) {
			throw e;
		}finally{
			em.close();
		}
		
		return fuList.get(0);
	}

	@Override
	public Long genericId() throws Exception {
		// TODO Auto-generated method stub
		//String sql = "select Nextno(1) as Tid from dual";
		//ZL_获取病人ID
		EntityManager em = getEntityManager();
		Object returnValues = null;
		try {
			Query q =  em.createNativeQuery("select Nextno(1) as Tid from dual");
			returnValues = q.getSingleResult();
		} catch (Exception e) {
			throw e;
		}finally{
			em.close();
		}
		return Long.valueOf(returnValues.toString());
	}

	@Override
	public Date getUserBirthDay(String cardId) {
		Date birthDayDate = IdcardInfoExtractor.getBirdate(cardId);
		return birthDayDate;
	}

	@Override
	public String getUserAge(String hisId,Date birthDate,Date currentDate) throws Exception {
		EntityManager em = getEntityManager();
		//select zl_age_calc(病人id,出生月日,当前日期) from dual
		String sql = "select zl_age_calc(:hisId,:birthDate,:currentDate) from dual";
		Object returnValues = null;
		try {
			Query q =  em.createNativeQuery(sql);
			q.setParameter("hisId", hisId);
			q.setParameter("birthDate", birthDate);
			q.setParameter("currentDate", currentDate);
			returnValues  = q.getSingleResult();
		} catch (Exception e) {
			throw e;
		}finally{
			em.close();
		}
		return (String)returnValues;
	}

	@Override
	public void insertNewUser2His(ForegroundUserInHis inHis) throws Exception {
		EntityManager em = getEntityManager();
		em.getTransaction().begin();
			Query q = em.createNativeQuery("{call ZL_病人档案_HIS_INSERT(1,:patientId,null,:patientName,:sex,:age,:birthday,null,:payType,null,null,null,null,:cardId,null,null,null,null,:houseTel,null,:createDate,null,null)}");
			q.setParameter("patientId", inHis.getPatientId());
			q.setParameter("patientName", inHis.getName());
			q.setParameter("sex", inHis.getSex());
			q.setParameter("cardId", inHis.getCardId());
			q.setParameter("age", inHis.getAge());
			q.setParameter("birthday", inHis.getBirthday());
			q.setParameter("payType", inHis.getPayType());
			q.setParameter("houseTel",inHis.getHouseTel());
			q.setParameter("createDate", ArgumentUtil.getSysDate());
			try {
				q.executeUpdate();
				em.getTransaction().commit();
			} catch (Exception e) {
				e.printStackTrace();
				String message = ((java.sql.SQLException)((org.hibernate.exception.GenericJDBCException)((javax.persistence.PersistenceException) e).getCause()).getCause()).getMessage();
				if (message.indexOf("[ZLSOFT]+") != -1) {
					message = message.substring(message.indexOf("[ZLSOFT]+") + 9, message.indexOf("+[ZLSOFT]"));
					
					throw new AppRuntimeException("E001", message, e);
				}
				
				throw e;
			}finally{
				em.close();
			}
	}
	
	@Override
	public void update2His(ForegroundUserInHis inhis) throws Exception {
		EntityManager em = getEntityManager();
		em.getTransaction().begin();
		Query q = null;
		q=em.createNativeQuery("{call dl_保存电话号码(:patientId,:houseTel,:validateCode,:channel)}");
		q.setParameter("patientId", inhis.getPatientId());
		q.setParameter("houseTel", inhis.getHouseTel());
		q.setParameter("validateCode", inhis.getValidateCode());
		q.setParameter("channel", Constants.DEFAULT_CHANNEL);
		try {
			q.executeUpdate();
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			String message = ((java.sql.SQLException)((org.hibernate.exception.GenericJDBCException)((javax.persistence.PersistenceException) e).getCause()).getCause()).getMessage();
			if (message.indexOf("[ZLSOFT]+") != -1) {
				message = message.substring(message.indexOf("[ZLSOFT]+") + 9, message.indexOf("+[ZLSOFT]"));
				
				throw new AppRuntimeException("E001", message, e);
			}
			throw e;
		}finally{
			em.close();
		}
	}
	
	@Override
	public ForegroundUserInHis queryForegroundUserInHisById(String patiendId) throws Exception {
		EntityManager em = getEntityManager();
		//String sql = "select 病人信息.病人ID,病人信息.姓名,病人信息.身份证号 from 病人信息  where 病人信息.身份证号 = :cardId and 病人信息.姓名 = :userName ";
		String sql =  " SELECT his.病人id patientId,         " 
					+ " his.门诊号 clinicNum,                " 
					+ " his.姓名  NAME,                       " 
					+ " his.性别 sex,                        " 
					+ " his.年龄 age,                        " 
					+ " his.出生日期 birthday,               " 
					+ " his.费别 chargeType,                 " 
					+ " his.医疗付款方式 payType,            " 
					+ " his.国籍 nationality,                " 
					+ " his.民族 nation,                     " 
					+ " his.婚姻状况 marriage,               " 
					+ " his.职业 occupation,                 " 
					+ " his.身份证号 cardId,                 " 
					+ " his.工作单位 company,                " 
					+ " his.单位电话 companyTel,             " 
					+ " his.单位邮编 companyPostcode,        " 
					+ " his.家庭地址 houseAddress,           " 
					+ " his.家庭电话 houseTel,               " 
					+ " his.家庭地址邮编 housePostcode,      " 
					+ " his.登记时间 registrationTime,       " 
					+ " his.户口地址 accounts,               " 
					+ " his.户口地址邮编 accountPostcode			" 
					+ " From 病人信息  his             " 
					+ " where his.病人id = :patiendId    " ;
		List<ForegroundUserInHis> fuList = new ArrayList<ForegroundUserInHis>();
		try {
			Query q  =  em.createNativeQuery(sql);
			q.setParameter("patiendId", Long.valueOf(patiendId));
			List<Object[]> results= q.getResultList();
			for(Object[] rs : results ){
				ForegroundUserInHis fu =  new ForegroundUserInHis();
				fu.setPatientId(Long.valueOf(rs[0].toString()));
				fu.setClinicNum(rs[1]==null?0L:Long.valueOf(rs[1].toString()));
				fu.setName((String) rs[2]);
				fu.setSex((String) rs[3]);
				fu.setAge((String) rs[4]);
				fu.setBirthday((Date) rs[5]);
				fu.setChargeType((String) rs[6]);
				fu.setPayType((String) rs[7]);
				fu.setNationality((String) rs[8]);
				fu.setNation((String) rs[9]);
				fu.setMarriage((String) rs[10]);
				fu.setOccupation((String) rs[11]);
				fu.setCardId((String) rs[12]);
				fu.setCompany((String) rs[13]);
				fu.setCompanyTel((String) rs[14]);
				fu.setCompanyPostcode((String) rs[15]);
				fu.setHouseAddress((String) rs[16]);
				fu.setHouseTel((String) rs[17]);
				fu.setHousePostcode((String) rs[18]);
				fu.setRegistrationTime((Date) rs[19]);
				fu.setAccounts((String) rs[20]);
				fu.setAccountPostcode((String) rs[21]);
				fuList.add(fu);
			}
		} catch (Exception e) {
			throw e;
		}finally{
			em.close();
		}
		return fuList.get(0);
	}

	@Override
	public Map<String, String> createOrder(Map<String, Object> params) throws Exception {
		EntityManager em = getEntityManager();
		em.getTransaction().begin();
		Map<String, String> resMap = new HashMap<String, String>();
		resMap.put("resKey", "1");
		resMap.put("message", "操作成功!");
		
		Integer orderId = (Integer)params.get("orderId");
		long patientId = Long.valueOf((String)params.get("patientId"));
		String snumber = (String)params.get("snumber");
		Integer serialNo = Integer.valueOf((String)params.get("serialNo"));
		Integer fee = Integer.valueOf((String)params.get("fee"));
		Date date = DateUtils.strToDate((String)params.get("date") + " " + params.get("startTime"), "yyyy-MM-dd HH:mm");
		
		String formNo = (String) params.get("formNo");
		
		StringBuffer sbu = new StringBuffer();
		sbu.append("{call ZL_三方机构挂号_INSERT(2");// 操作方式
		sbu.append(",?");// 病人id
		sbu.append(",?");// 号码
		sbu.append(",?");// 号序
		sbu.append(",?");// 单据号
		sbu.append(",NULL");// 票据号
		sbu.append(",'现金'");// 结算方式
		sbu.append(",NULL");
		sbu.append(",?");// 发生时间
		sbu.append(",NULL");// 登记时间
		sbu.append(",NULL");// 合作单位
		sbu.append(",?");// 挂号金额
		sbu.append(",0");
		sbu.append(",0");
		sbu.append(",?");// 交易流水号
		sbu.append(",'微信'");// 交易说明
		sbu.append(",'微信'");// 预约方式
		sbu.append(")}");
		
		Query query = em.createNativeQuery(sbu.toString());
		query.setParameter(1, patientId);
		query.setParameter(2, snumber);
		query.setParameter(3, serialNo);
		query.setParameter(4, formNo);
		query.setParameter(5, date);
		query.setParameter(6, fee);
		query.setParameter(7, String.valueOf(orderId));
		try {
			query.executeUpdate();
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			String message = ((java.sql.SQLException)((org.hibernate.exception.GenericJDBCException)((javax.persistence.PersistenceException) e).getCause()).getCause()).getMessage();
			if (message.indexOf("[ZLSOFT]+") != -1) {
				message = message.substring(message.indexOf("[ZLSOFT]+") + 9, message.indexOf("+[ZLSOFT]"));
				
				throw new AppRuntimeException("E001", message, e);
			}
			
			throw e;
		}finally{
			em.close();
		}
		
		return resMap;
	}

	@Override
	public void cancelOrder(String formNo, Integer poid) throws Exception {
		EntityManager em = getEntityManager();
		em.getTransaction().begin();
		StringBuffer sbu = new StringBuffer();
		sbu.append("{call ZL_三方机构挂号_DELETE(");
		sbu.append("?");// 单据号
		sbu.append(",?");// 交易流水号
		sbu.append(",'微信'");// 交易说明
		sbu.append(")}");
		
		Query query = em.createNativeQuery(sbu.toString());
		query.setParameter(1, formNo);
		query.setParameter(2, poid);

		try {
			query.executeUpdate();
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			String message = ((java.sql.SQLException)((org.hibernate.exception.GenericJDBCException)((javax.persistence.PersistenceException) e).getCause()).getCause()).getMessage();
			if (message.indexOf("[ZLSOFT]+") != -1) {
				message = message.substring(message.indexOf("[ZLSOFT]+") + 9, message.indexOf("+[ZLSOFT]"));
				
				throw new AppRuntimeException("E001", message, e);
			}
			
			throw e;
		}finally{
			em.close();
		}
	}
	
	@Override
	public List<OutpatientLisSampleInfo> findOutpatientPage(String patientidnumber, String fuName , Integer startNum, Integer endNum, Integer type) throws Exception {
		EntityManager em = getEntityManager();
		String sql = "SELECT * FROM " 
				+ "(SELECT lis.挂号单号," 
				+ "lis.病人ID, " 
				+ "lis.姓名, " 
				+ "lis.身份证号, " 
				+ "lis.看诊科室ID, " 
				+ "lis.看诊科室名称, " 
				+ "lis.最终看诊医生, " 
				+ "lis.执行状态, " 
				+ "lis.看诊日期, " 
				+ "lis.预约, " 
				+ "lis.预约方式, " 
				+ "lis.登记时间 ,  " 
				+ " ROWNUM AS ROW_NUM "
				+ " FROM ( select * from outpatient_records op where op.身份证号 =:sfzh  and op.姓名 = :fuName order by op.登记时间  DESC ) lis " 
				+ " WHERE ROWNUM <=:endNum ) "
				+ " WHERE ROW_NUM >:startNum ";
		List<OutpatientLisSampleInfo> listLisSampleInfos = new ArrayList<OutpatientLisSampleInfo>();
		try {
			Query q = em.createNativeQuery(sql);
			q.setParameter("sfzh", patientidnumber);
			q.setParameter("fuName", fuName);
			q.setParameter("endNum", endNum);
			q.setParameter("startNum", startNum);
			@SuppressWarnings("unchecked")
			List<Object[]> returnValues = q.getResultList();
			if(returnValues != null && !returnValues.isEmpty()){
				for (Object[] object : returnValues){
					OutpatientLisSampleInfo temp = new OutpatientLisSampleInfo();
					temp.setGhdh(String.valueOf(object[0]));
					temp.setPatientID(Long.valueOf((object[1].toString())));
					temp.setPatientName(String.valueOf(object[2]));
					temp.setSfzh(String.valueOf(object[3]));
					temp.setKzks(Long.valueOf(object[4].toString()));
					//temp.setKsname(String.valueOf(object[5]));
					temp.setKsname(String.valueOf(object[5])=="null"?"":String.valueOf(object[5]));
					temp.setZzkzys(String.valueOf(object[6])=="null"?"":String.valueOf(object[6]));
					temp.setExecuteState(Integer.parseInt((object[7].toString())));
					temp.setKzdate((Date)object[8]);
					temp.setYuyue(Integer.parseInt(object[9].toString()));
					temp.setYuyuefs(String.valueOf(object[10]));
					temp.setDjdate((Date)object[11]);
					listLisSampleInfos.add(temp);
				}
			}
		} catch (Exception e) {
			throw e;
		}finally{
			em.close();
		}
		return listLisSampleInfos;
	}
	
	@Override
	public Integer findOutpatientCount(String patientidnumber,String fuName, Integer type) throws Exception {
		EntityManager em = getEntityManager();
		Integer rs=0;
		Object returnValues = null;
		try {
			Query q = em.createNativeQuery("SELECT COUNT(*) FROM OUTPATIENT_RECORDS lis WHERE lis.身份证号 =:sfzh AND 姓名 =:xm ");
			q.setParameter("sfzh", patientidnumber);
			q.setParameter("xm", fuName);
			returnValues = q.getSingleResult();
			if(returnValues != null){
				rs=Integer.parseInt(returnValues.toString());
			}
		} catch (Exception e) {
			throw e;
		}finally{
			em.close();
		}
		return rs;
	}

	@Override
	public OutpatientLisSampleInfo findByFormNo(String formNo) throws Exception {
		EntityManager em = getEntityManager();
		String sql = "SELECT op.挂号单号," 
				+ "op.病人ID, " 
				+ "op.姓名, " 
				+ "op.身份证号, " 
				+ "op.看诊科室ID, " 
				+ "op.看诊科室名称, " 
				+ "op.最终看诊医生, " 
				+ "op.执行状态, " 
				+ "op.看诊日期, " 
				+ "op.预约, " 
				+ "op.预约方式, " 
				+ "op.登记时间 "
				+ " from outpatient_records op where 挂号单号 = :formNo ";
		OutpatientLisSampleInfo entity = null;
		try {
			Query q = em.createNativeQuery(sql);
			q.setParameter("formNo", formNo);
			List<Object[]> returnValues = q.getResultList();
			if(returnValues != null && !returnValues.isEmpty()){
				entity = new OutpatientLisSampleInfo();
				Object[] object = returnValues.get(0);
				entity.setGhdh(String.valueOf(object[0]));
				entity.setPatientID(Long.valueOf((object[1].toString())));
				entity.setPatientName(String.valueOf(object[2]));
				entity.setSfzh(String.valueOf(object[3]));
				entity.setKzks(Long.valueOf(object[4].toString()));
				entity.setKsname(String.valueOf(object[5])=="null"?"":String.valueOf(object[5]));
				entity.setZzkzys(String.valueOf(object[6])=="null"?"":String.valueOf(object[6]));
				entity.setExecuteState(Integer.parseInt((object[7].toString())));
				entity.setKzdate((Date)object[8]);
				entity.setYuyue(((BigDecimal) object[9]).intValue());
				entity.setYuyuefs(String.valueOf(object[10]));
				entity.setDjdate((Date)object[11]);
			}
		} catch (Exception e) {
			throw e;
		}finally{
			em.close();
		}
		return entity;
	}



	@Override
	public List<OutpatientLisSampleInfo> findByFormNo(List<String> formNoList) throws Exception {
		EntityManager em = getEntityManager();
		String sql = "SELECT op.挂号单号," 
				+ "op.病人ID, " 
				+ "op.姓名, " 
				+ "op.身份证号, " 
				+ "op.看诊科室ID, " 
				+ "op.看诊科室名称, "
				+ "op.最终看诊医生, " 
				+ "op.执行状态, " 
				+ "op.看诊日期, " 
				+ "op.预约, " 
				+ "op.预约方式, " 
				+ "op.登记时间 "
				+ " from outpatient_records op where 挂号单号 in ( :formNoList )";
		List<OutpatientLisSampleInfo> entityList = new ArrayList<OutpatientLisSampleInfo>();
		try {
			Query q = em.createNativeQuery(sql);
			q.setParameter("formNoList", formNoList);
			@SuppressWarnings("unchecked")
			List<Object[]> returnValues = q.getResultList();
			if(returnValues != null && !returnValues.isEmpty()){
				OutpatientLisSampleInfo entity = new OutpatientLisSampleInfo();
				for (Object[] object : returnValues) {
					entity.setGhdh(String.valueOf(object[0]));
					entity.setPatientID(Long.valueOf((object[1].toString())));
					entity.setPatientName(String.valueOf(object[2]));
					entity.setSfzh(String.valueOf(object[3]));
					entity.setKzks(Long.valueOf(object[4].toString()));
					entity.setKsname(String.valueOf(object[5])=="null"?"":String.valueOf(object[5]));
					entity.setZzkzys(String.valueOf(object[6])=="null"?"":String.valueOf(object[6]));
					entity.setExecuteState(Integer.parseInt((object[7].toString())));
					entity.setKzdate((Date)object[8]);
					entity.setYuyue(((BigDecimal) object[9]).intValue());
					entity.setYuyuefs(String.valueOf(object[10]));
					entity.setDjdate((Date)object[11]);
					entityList.add(entity);
				}
			}
		} catch (Exception e) {
			throw e;
		}finally{
			em.close();
		}
		return entityList;
	}

	@Override
	public String genericPayTypeName(String payType) throws Exception {
		EntityManager em = getEntityManager();
		String sql ="Select 名称 payTypeName From 医疗付款方式 where 编码 = :payType";
		String payTypeName = null;
		try {
			Query q = em.createNativeQuery(sql);
			q.setParameter("payType", payType);
			payTypeName = q.getSingleResult().toString();
		} catch (Exception e) {
			throw e;
		}finally{
			em.close();
		}
		return payTypeName;
	}
}
