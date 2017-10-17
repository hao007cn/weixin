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

package com.senyint.wx.mobile.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.senyint.wx.mobile.dao.PacsReportDao;
import com.senyint.wx.mobile.entity.ReportPacsSj;

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
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class PacsReportDaoImpl implements PacsReportDao{

	private EntityManager entityManager;
	
//	@Autowired
//	private EntityManagerFactory pacsEntityManagerFactory;

	public PacsReportDaoImpl() {
		super();
	}

	@PersistenceContext(unitName = "pacs")
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	protected EntityManager getEntityManager() {
//		if (entityManager == null)
//			entityManager = pacsEntityManagerFactory.createEntityManager();
		return entityManager;
	}

	@Override
	public Integer findReportPacsCount(String patientname,String patientidnumber) {
		 //'210202198606153220'
		Integer rs=0;
		Query q = getEntityManager().createNativeQuery("select count(sj.SEGUID) from pacs_sj sj where sj.PATIENTIDNUMBER =:PATIENTIDNUMBER and sj.PATIENTNAME=:PATIENTNAME");
		q.setParameter("PATIENTIDNUMBER", patientidnumber);
		q.setParameter("PATIENTNAME", patientname);
		Object returnValues = q.getSingleResult();
		if(returnValues != null){
			rs=Integer.parseInt(returnValues.toString());
		}
		return rs;
		
	}
	@Override
	public List<ReportPacsSj> findReportPacsPage(String patientname,String patientidnumber,
			Integer startNum, Integer endNum) {
		String sql="select * from "
				+ "(SELECT  "
				+ "nvl(sj.SEGUID,'') SEGUID,"
				+ "nvl(sj.PATIENTIDNUMBER,'') PATIENTIDNUMBER,"
				+ "nvl(sj.STUDYID,'') STUDYID,"
				+ "nvl(sj.PATIENTOUTPATIENTID,'') PATIENTOUTPATIENTID,"
				+ "nvl(sj.STUDYTIME,'') STUDYTIME,"
				+ "nvl(sj.REPORTTIME,'') REPORTTIME,"
				+ "nvl(sj.APPLYDEPARTMENT,'') APPLYDEPARTMENT,"
				+ "nvl(sj.DEVICENAME,'') DEVICENAME,"
				+ "nvl(sj.STUDYDESCRIBE,'') STUDYDESCRIBE,"
				+ "nvl(sj.PATIENTNAME,'') PATIENTNAME,"
				+ "nvl(sj.PATIENTAGE,'') PATIENTAGE,"
				+ "nvl(sj.PATIENTGENDER,'') PATIENTGENDER,"
				+ "nvl(sj.REPORTDESC,'') REPORTDESC,"
				+ "nvl(sj.REPORTDIAGNOSE,'') REPORTDIAGNOSE,"
				+ "ROWNUM as ROW_NUM  "
				+ "from (select * from pacs_sj ps where ps.PATIENTIDNUMBER=:PATIENTIDNUMBER and ps.PATIENTNAME=:PATIENTNAME ORDER BY ps.STUDYTIME desc) sj "
				+ " WHERE ROWNUM <=:endNum)"
				+ " where ROW_NUM>:startNum";
		Query q = getEntityManager().createNativeQuery(sql);
		q.setParameter("PATIENTIDNUMBER", patientidnumber);
		q.setParameter("PATIENTNAME", patientname);
		q.setParameter("endNum", endNum);
		q.setParameter("startNum", startNum);
		@SuppressWarnings("unchecked")
		List<Object[]> returnValues = q.getResultList();
		List<ReportPacsSj> pacsResult =  new ArrayList<ReportPacsSj>();
		if(returnValues != null && !returnValues.isEmpty()){
			for (Object[] object : returnValues) {
				ReportPacsSj temp = new ReportPacsSj();
				temp.setXeguid(String.valueOf(object[0])=="null"?"":String.valueOf(object[0]));
				temp.setPatientidnumber(String.valueOf(object[1])=="null"?"":String.valueOf(object[1]));
				temp.setStudyid(String.valueOf(object[2])=="null"?"":String.valueOf(object[2]));
				temp.setPatientoutpatientid(String.valueOf(object[3])=="null"?"":String.valueOf(object[3]));
				temp.setStudytime(String.valueOf(object[4])=="null"?"":String.valueOf(object[4]));
				temp.setReporttime(String.valueOf(object[5])=="null"?"":String.valueOf(object[5]));
				temp.setApplydepartment(String.valueOf(object[6])=="null"?"":String.valueOf(object[6]));
				temp.setDevicename(String.valueOf(object[7])=="null"?"":String.valueOf(object[7]));
				temp.setStudydescribe(String.valueOf(object[8])=="null"?"":String.valueOf(object[8]));
				temp.setPatientname(String.valueOf(object[9])=="null"?"":String.valueOf(object[9]));
				temp.setPatientage(String.valueOf(object[10])=="null"?"":String.valueOf(object[10]));
				temp.setPatientgender(String.valueOf(object[11])=="null"?"":String.valueOf(object[11]));
				temp.setReportdesc(String.valueOf(object[12])=="null"?"":String.valueOf(object[12]));
				temp.setReportdiagnose(String.valueOf(object[13])=="null"?"":String.valueOf(object[13]));
				pacsResult.add(temp);
			}
		}
		return pacsResult;
		
	}
	@Override
	public ReportPacsSj getReportPacs(String id) {
		Query q = getEntityManager().createNativeQuery("select "
				+ "nvl(sj.SEGUID,'') SEGUID,"
				+ "nvl(sj.PATIENTIDNUMBER,'') PATIENTIDNUMBER,"
				+ "nvl(sj.STUDYID,'') STUDYID,"
				+ "nvl(sj.PATIENTOUTPATIENTID,'') PATIENTOUTPATIENTID,"
				+ "nvl(sj.STUDYTIME,'') STUDYTIME,"
				+ "nvl(sj.REPORTTIME,'') REPORTTIME,"
				+ "nvl(sj.APPLYDEPARTMENT,'') APPLYDEPARTMENT,"
				+ "nvl(sj.DEVICENAME,'') DEVICENAME,"
				+ "nvl(sj.STUDYDESCRIBE,'') STUDYDESCRIBE,"
				+ "nvl(sj.PATIENTNAME,'') PATIENTNAME,"
				+ "nvl(sj.PATIENTAGE,'') PATIENTAGE,"
				+ "nvl(sj.PATIENTGENDER,'') PATIENTGENDER,"
				+ "nvl(sj.REPORTDESC,'') REPORTDESC,"
				+ "nvl(sj.REPORTDIAGNOSE,'') REPORTDIAGNOSE "
				+ "from pacs_sj sj where sj.SEGUID =:SEGUID");
		q.setParameter("SEGUID", id);
		ReportPacsSj temp = new ReportPacsSj();
		@SuppressWarnings("unchecked")
		List<Object[]> returnValues = q.getResultList();
		if(returnValues != null && !returnValues.isEmpty()){
			for (Object[] object : returnValues) {
				temp.setXeguid(String.valueOf(object[0])=="null"?"":String.valueOf(object[0]));
				temp.setPatientidnumber(String.valueOf(object[1])=="null"?"":String.valueOf(object[1]));
				temp.setStudyid(String.valueOf(object[2])=="null"?"":String.valueOf(object[2]));
				temp.setPatientoutpatientid(String.valueOf(object[3])=="null"?"":String.valueOf(object[3]));
				temp.setStudytime(String.valueOf(object[4])=="null"?"":String.valueOf(object[4]));
				temp.setReporttime(String.valueOf(object[5])=="null"?"":String.valueOf(object[5]));
				temp.setApplydepartment(String.valueOf(object[6])=="null"?"":String.valueOf(object[6]));
				temp.setDevicename(String.valueOf(object[7])=="null"?"":String.valueOf(object[7]));
				temp.setStudydescribe(String.valueOf(object[8])=="null"?"":String.valueOf(object[8]));
				temp.setPatientname(String.valueOf(object[9])=="null"?"":String.valueOf(object[9]));
				temp.setPatientage(String.valueOf(object[10])=="null"?"":String.valueOf(object[10]));
				temp.setPatientgender(String.valueOf(object[11])=="null"?"":String.valueOf(object[11]));
				temp.setReportdesc(String.valueOf(object[12])=="null"?"":String.valueOf(object[12]));
				temp.setReportdiagnose(String.valueOf(object[13])=="null"?"":String.valueOf(object[13]));
			}
		}
		return temp;
		
	}
}
