package com.senyint.wx.mobile.dao.impl;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.senyint.wx.mobile.dao.LisReportDao;
import com.senyint.wx.mobile.entity.LisReport;
import com.senyint.wx.mobile.entity.ReportLisResult;
import com.senyint.wx.mobile.entity.ReportLisSampleInfo;

/**
 * JPA {@link EntityManager} based EAO
 * <p>
 * this could be used when non one-entity-per-table case. it won't limit the
 * entity type this EAO handles for. you can get entity manager directly and
 * have your own operation freely.
 * </p>
 * 
 * 
 */
/**
 * @author wangchao
 *
 */
@Repository
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ListReportDaoImpl implements LisReportDao{

	private EntityManager entityManager;
//	
//	@Autowired
//	private EntityManagerFactory lisEntityManagerFactory;

	public ListReportDaoImpl() {
		super();
	}

	@PersistenceContext(unitName = "lis")
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	protected EntityManager getEntityManager() {
//		if (entityManager == null)
//			entityManager = lisEntityManagerFactory.createEntityManager();
		return entityManager;
	}
	
	@SuppressWarnings("unchecked")
	public List<LisReport> findByBblsh(String bblsh) {
		Query q = getEntityManager().createNativeQuery("select * from NEWDOC.T_LIS_RESULT_VIEW where bblsh=:bblsh order by bblsh desc");
		q.setParameter("bblsh", bblsh);
		
		List<Object[]> returnValues = q.getResultList();
		List<LisReport> lisResult =  new ArrayList<LisReport>();
		if(returnValues != null && !returnValues.isEmpty()){
			for (Object[] object : returnValues) {
				LisReport temp = new LisReport();
				temp.setBblsh(String.valueOf(object[0])=="null"?"":String.valueOf(object[0]));
				temp.setXmdm(String.valueOf(object[1])=="null"?"":String.valueOf(object[1]));
				temp.setXmmc(String.valueOf(object[2])=="null"?"":String.valueOf(object[2]));
				temp.setXmywmc(String.valueOf(object[3])=="null"?"":String.valueOf(object[3]));
				temp.setXmjg(String.valueOf(object[4])=="null"?"":String.valueOf(object[4]));
				temp.setJgdw(String.valueOf(object[5])=="null"?"":String.valueOf(object[5]));
				temp.setCkz(String.valueOf(object[6])=="null"?"":String.valueOf(object[6]));
				temp.setCssj(String.valueOf(object[7])=="null"?"":String.valueOf(object[7]));
				temp.setJgbz(String.valueOf(object[8])=="null"?"":String.valueOf(object[8]));
				temp.setYzxmdm(String.valueOf(object[9])=="null"?"":String.valueOf(object[9]));
				temp.setYzxmmc(String.valueOf(object[10])=="null"?"":String.valueOf(object[10]));
				lisResult.add(temp);
			}
		}
		return lisResult;
	}
	@Override
	public List<ReportLisSampleInfo> findReportLisPage(String username,String patientidnumber, Integer startNum, Integer endNum) {
		String sql = "SELECT * FROM " 
				+ "(SELECT nvl(lis.BBLSH,'') BBLSH," 
				+ "nvl(lis.MZH,'') MZH, " 
				+ "nvl(lis.SFZH,'') SFZH, " 
				+ "nvl(lis.XM,'') XM, " 
				+ "nvl(lis.XB,'') XB, " 
				+ "nvl(lis.NL,'') NL, " 
				+ "nvl(lis.BBMC,'') BBMC, " 
				+ "nvl(lis.BBXH,'') BBXH, " 
				+ "nvl(lis.SQXM,'') SQXM, " 
				+ "nvl(lis.YSID,'') YSID, " 
				+ "nvl(lis.TYRMC,'') TYRMC, " 
				+ "nvl(lis.SHR,'') SHR, " 
				+ "nvl(lis.BGSJ,'') BGSJ, " 
				+ "nvl(lis.SHSJ,'') SHSJ, " 
				+ "nvl(lis.SQKSDM,'') SQKSDM, " 
				+ "nvl(lis.BBXT,'') BBXT, " 
				+ "nvl(lis.JYBZ,'') JYBZ, " 
				+ " ROWNUM AS ROW_NUM "
				+ " FROM ( select * from v_lis_sampleinfo_mz sj WHERE sj.sfzh =:sfzh and sj.xm =:xm ORDER BY sj.BGSJ DESC ) lis " 
				+ " WHERE ROWNUM <=:endNum ) "
				+ " WHERE ROW_NUM >:startNum ";
		//Query q = getEntityManager().createNativeQuery("select * from (select * from v_lis_sampleinfo_mz lis where lis.sfzh =:sfzh and ROWNUM <=:endNum) where ROWNUM >:startNum");
		Query q = getEntityManager().createNativeQuery(sql);
		q.setParameter("sfzh", patientidnumber);
		q.setParameter("xm", username);
		q.setParameter("endNum", endNum);
		q.setParameter("startNum", startNum);
		@SuppressWarnings("unchecked")
		List<Object[]> returnValues = q.getResultList();
		List<ReportLisSampleInfo> listLisSampleInfos = new ArrayList<ReportLisSampleInfo>();
		if(returnValues != null && !returnValues.isEmpty()){
			for (Object[] object : returnValues){
				ReportLisSampleInfo temp = new ReportLisSampleInfo();
				temp.setBblsh(String.valueOf(object[0])=="null"?"":String.valueOf(object[0]));
				temp.setMzh(String.valueOf(object[1])=="null"?"":String.valueOf(object[1]));
				temp.setSfzh(String.valueOf(object[2])=="null"?"":String.valueOf(object[2]));
				temp.setXm(String.valueOf(object[3])=="null"?"":String.valueOf(object[3]));
				temp.setXb(String.valueOf(object[4])=="null"?"":String.valueOf(object[4]));
				temp.setNl(String.valueOf(object[5])=="null"?"":String.valueOf(object[5]));
				temp.setBbmc(String.valueOf(object[6])=="null"?"":String.valueOf(object[6]));
				temp.setBbxh(String.valueOf(object[7])=="null"?"":String.valueOf(object[7]));
				temp.setSqxm(String.valueOf(object[8])=="null"?"":String.valueOf(object[8]));
				temp.setYsid(String.valueOf(object[9])=="null"?"":String.valueOf(object[9]));
				temp.setJyrmc(String.valueOf(object[10])=="null"?"":String.valueOf(object[10]));
				temp.setShr(String.valueOf(object[11])=="null"?"":String.valueOf(object[11]));
				temp.setBgsj(String.valueOf(object[12])=="null"?"":String.valueOf(object[12]));
				temp.setShsj(String.valueOf(object[13])=="null"?"":String.valueOf(object[13]));
				temp.setSqksdm(String.valueOf(object[14])=="null"?"":String.valueOf(object[14]));
				temp.setBbxt(String.valueOf(object[15])=="null"?"":String.valueOf(object[15]));
				temp.setJybz(String.valueOf(object[16])=="null"?"":String.valueOf(object[16]));
				listLisSampleInfos.add(temp);
			}
		}
		return listLisSampleInfos;
	}
	@Override
	public List<ReportLisResult> findLisByBblsh(String bblsh) {
		String sql = "SELECT nvl(lis.BBLSH,'') BBLSH, " 
				+ "nvl(lis.SQXMDM,'') SQXMDM, " 
				+ "nvl(lis.SQXMMC,'') SQXMMC, " 
				+ "nvl(lis.BGSX,'') BGSX, " 
				+ "nvl(lis.XMDM,'') XMDM, " 
				+ "nvl(lis.XMMC,'') XMMC, " 
				+ "nvl(lis.XMYWMC,'') XMYWMC, " 
				+ "nvl(lis.XMJG,'') XMJG, " 
				+ "nvl(lis.CSSJ,'') CSSJ, " 
				+ "nvl(lis.CKZ,'') CKZ, " 
				+ "nvl(lis.JGDW,'') JGDW, " 
				+ "nvl(lis.JGBZ,'') JGBZ, " 
				+ "nvl(lis.YYXBZ,'') YYXBZ, " 
				+ "nvl(lis.YZXMDM,'') YZXMDM, " 
				+ "nvl(lis.YZXMMC,'') YZXMMC " 
				+ "FROM v_lis_result_mz lis "
				+ "WHERE lis.bblsh=:bblsh ";
		//Query q = getEntityManager().createNativeQuery("select * from v_lis_result_mz where bblsh=:bblsh ");
		Query q = getEntityManager().createNativeQuery(sql);
		q.setParameter("bblsh", bblsh);
		@SuppressWarnings("unchecked")
		List<Object[]> returnValues = q.getResultList();
		List<ReportLisResult> reportLisResultList = new ArrayList<ReportLisResult>();
		if(returnValues != null && !returnValues.isEmpty()){
			for (Object[] object : returnValues){
				ReportLisResult temp = new ReportLisResult();
				temp.setBblsh(String.valueOf(object[0])=="null"?"":String.valueOf(object[0]));
				temp.setSqxmdm(String.valueOf(object[1])=="null"?"":String.valueOf(object[1]));
				temp.setSqxmmc(String.valueOf(object[2])=="null"?"":String.valueOf(object[2]));
				temp.setBgsx(String.valueOf(object[3])=="null"?"":String.valueOf(object[3]));
				temp.setXmdm(String.valueOf(object[4])=="null"?"":String.valueOf(object[4]));
				temp.setXmmc(String.valueOf(object[5])=="null"?"":String.valueOf(object[5]));
				temp.setXmywmc(String.valueOf(object[6])=="null"?"":String.valueOf(object[6]));
				temp.setXmjg(String.valueOf(object[7])=="null"?"":String.valueOf(object[7]));
				temp.setCssj(String.valueOf(object[8])=="null"?"":String.valueOf(object[8]));
				temp.setCkz(String.valueOf(object[9])=="null"?"":String.valueOf(object[9]));
				temp.setJgdw(String.valueOf(object[10])=="null"?"":String.valueOf(object[10]));
				temp.setJgbz(String.valueOf(object[11])=="null"?"":String.valueOf(object[11]));
				temp.setYyxbz(String.valueOf(object[12])=="null"?"":String.valueOf(object[12]));
				temp.setYzxmdm(String.valueOf(object[13])=="null"?"":String.valueOf(object[13]));
				temp.setYzxmmc(String.valueOf(object[14])=="null"?"":String.valueOf(object[14]));
				reportLisResultList.add(temp);
			}
		}
		return reportLisResultList;
	}
	@Override
	public Integer findReportLisCount(String username,String patientidnumber) {
		Integer rs=0;
		Query q = getEntityManager().createNativeQuery("SELECT COUNT(lis.BBLSH) FROM v_lis_sampleinfo_mz lis WHERE lis.sfzh =:sfzh and lis.xm=:xm");
		q.setParameter("sfzh", patientidnumber);
		q.setParameter("xm", username);
		Object returnValues = q.getSingleResult();
		if(returnValues != null){
			rs=Integer.parseInt(returnValues.toString());
		}
		return rs;
	}

	@Override
	public ReportLisSampleInfo findReportLisSampleEntityByBblsh(String bblsh) {
		String sql = "SELECT nvl(lis.BBLSH,'') BBLSH, " 
							+ "nvl(lis.MZH,'') MZH, " 
							+ "nvl(lis.SFZH,'') SFZH, " 
							+ "nvl(lis.XM,'') XM, " 
							+ "nvl(lis.XB,'') XB, " 
							+ "nvl(lis.NL,'') NL, " 
							+ "nvl(lis.BBMC,'') BBMC, " 
							+ "nvl(lis.BBXH,'') BBXH, " 
							+ "nvl(lis.SQXM,'') SQXM, " 
							+ "nvl(lis.YSID,'') YSID, " 
							+ "nvl(lis.TYRMC,'') TYRMC, " 
							+ "nvl(lis.SHR,'') SHR, " 
							+ "nvl(lis.BGSJ,'') BGSJ, " 
							+ "nvl(lis.SHSJ,'') SHSJ, " 
							+ "nvl(lis.SQKSDM,'') SQKSDM, " 
							+ "nvl(lis.BBXT,'') BBXT, " 
							+ "nvl(lis.JYBZ,'') JYBZ " 
							+ "FROM v_lis_sampleinfo_mz lis WHERE lis.bblsh =:bblsh ";
		Query q = getEntityManager().createNativeQuery(sql);
		//Query q = getEntityManager().createNativeQuery("select * from v_lis_sampleinfo_mz where bblsh=:bblsh");
		q.setParameter("bblsh", bblsh);
		@SuppressWarnings("unchecked")
		List<Object[]> returnValues = q.getResultList();
		ReportLisSampleInfo temp = new ReportLisSampleInfo();
		if(returnValues != null && !returnValues.isEmpty()){
			for (Object[] object : returnValues){
				temp.setBblsh(String.valueOf(object[0])=="null"?"":String.valueOf(object[0]));
				temp.setMzh(String.valueOf(object[1])=="null"?"":String.valueOf(object[1]));
				temp.setSfzh(String.valueOf(object[2])=="null"?"":String.valueOf(object[2]));
				temp.setXm(String.valueOf(object[3])=="null"?"":String.valueOf(object[3]));
				temp.setXb(String.valueOf(object[4])=="null"?"":String.valueOf(object[4]));
				temp.setNl(String.valueOf(object[5])=="null"?"":String.valueOf(object[5]));
				temp.setBbmc(String.valueOf(object[6])=="null"?"":String.valueOf(object[6]));
				temp.setBbxh(String.valueOf(object[7])=="null"?"":String.valueOf(object[7]));
				temp.setSqxm(String.valueOf(object[8])=="null"?"":String.valueOf(object[8]));
				temp.setYsid(String.valueOf(object[9])=="null"?"":String.valueOf(object[9]));
				temp.setJyrmc(String.valueOf(object[10])=="null"?"":String.valueOf(object[10]));
				temp.setShr(String.valueOf(object[11])=="null"?"":String.valueOf(object[11]));
				temp.setBgsj(String.valueOf(object[12])=="null"?"":String.valueOf(object[12]));
				temp.setShsj(String.valueOf(object[13])=="null"?"":String.valueOf(object[13]));
				temp.setSqksdm(String.valueOf(object[14])=="null"?"":String.valueOf(object[14]));
				temp.setBbxt(String.valueOf(object[15])=="null"?"":String.valueOf(object[15]));
				temp.setJybz(String.valueOf(object[16])=="null"?"":String.valueOf(object[16]));

			}
		}
		return temp;
	}
}