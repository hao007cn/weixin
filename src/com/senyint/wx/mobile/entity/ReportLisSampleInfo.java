package com.senyint.wx.mobile.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Administrator
 * --list头信息
 *
 */

public class ReportLisSampleInfo {
	
	/**
	 * 标本流水号
	 */
	private String bblsh;

	/**
	 * 门诊号
	 */
	private String mzh;
	
	/**
	 * 身份证号
	 */
	private String sfzh;
	
	/**
	 * 姓名
	 */
	private String xm;
	/**
	 * 性别
	 */
	private String xb;
	/**
	 * 年龄
	 */
	private String nl;
	/**
	 * 标本类型
	 */
	private String bbmc;
	/**
	 * 标本序号
	 */
	private String bbxh;
	/**
	 * 申请项目
	 */
	private String sqxm;
	/**
	 * 申请医生
	 */
	private String ysid;
	/**
	 * 检验者
	 */
	private String jyrmc;
	/**
	 * 审核者
	 */
	private String shr;
	/**
	 * 检验时间
	 */
	private String bgsj;
	/**
	 * 审核时间
	 */
	private String shsj;
	/**
	 * 科室
	 */
	private String sqksdm;
	/**
	 * 标本状态
	 */
	private String bbxt;
	/**
	 * 
	 */
	private String jybz;
	
	public String getBblsh() {
		return bblsh;
	}
	public void setBblsh(String bblsh) {
		this.bblsh = bblsh;
	}
	public String getMzh() {
		return mzh;
	}
	public void setMzh(String mzh) {
		this.mzh = mzh;
	}
	public String getSfzh() {
		return sfzh;
	}
	public void setSfzh(String sfzh) {
		this.sfzh = sfzh;
	}
	public String getXm() {
		return xm;
	}
	public void setXm(String xm) {
		this.xm = xm;
	}
	public String getXb() {
		return xb;
	}
	public void setXb(String xb) {
		this.xb = xb;
	}
	public String getNl() {
		return nl;
	}
	public void setNl(String nl) {
		this.nl = nl;
	}
	public String getBbmc() {
		return bbmc;
	}
	public void setBbmc(String bbmc) {
		this.bbmc = bbmc;
	}
	public String getBbxh() {
		return bbxh;
	}
	public void setBbxh(String bbxh) {
		this.bbxh = bbxh;
	}
	public String getSqxm() {
		return sqxm;
	}
	public void setSqxm(String sqxm) {
		this.sqxm = sqxm;
	}
	public String getYsid() {
		return ysid;
	}
	public void setYsid(String ysid) {
		this.ysid = ysid;
	}
	public String getJyrmc() {
		return jyrmc;
	}
	public void setJyrmc(String jyrmc) {
		this.jyrmc = jyrmc;
	}
	public String getShr() {
		return shr;
	}
	public void setShr(String shr) {
		this.shr = shr;
	}
	public String getBgsj() {
		return bgsj;
	}
	public void setBgsj(String bgsj) {
		this.bgsj = bgsj;
	}
	public String getShsj() {
		return shsj;
	}
	public void setShsj(String shsj) {
		this.shsj = shsj;
	}
	public String getSqksdm() {
		return sqksdm;
	}
	public void setSqksdm(String sqksdm) {
		this.sqksdm = sqksdm;
	}
	public String getBbxt() {
		return bbxt;
	}
	public void setBbxt(String bbxt) {
		this.bbxt = bbxt;
	}
	public String getJybz() {
		return jybz;
	}
	public void setJybz(String jybz) {
		this.jybz = jybz;
	}
}
