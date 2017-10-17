package com.senyint.wx.mobile.entity;

import java.io.Serializable;

/**
 * @Title: LisReport.java
 * @Description: Lis 报告
 * @version V1.0
 */
/**
 * This is an object that contains data related to the t_lis_result_view table.
 * Do not modify this class because it will be overwritten if the configuration
 * file related to this class is modified.
 * 
 * @hibernate.class table="t_lis_report_view"
 */
public class LisReport implements Serializable {

	private static final long serialVersionUID = 15545345355345007L;

	// constructors
	public LisReport() {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public LisReport(java.lang.String bblsh) {
		this.setBblsh(bblsh);
		initialize();
	}

	protected void initialize() {
	}

	/**
	 * @Fields bblsh : 标本流水号
	 */
	private String bblsh;
	/**
	 * @Fields xmdm : 项目代码
	 */
	private String xmdm;
	/**
	 * @Fields xmmc : 项目名称
	 */
	private String xmmc;
	/**
	 * @Fields xmywmc : 项目医务名称
	 */
	private String xmywmc;
	/**
	 * @Fields xmjg : 项目结果
	 */
	private String xmjg;
	/**
	 * @Fields jgdw : 结果单位
	 */
	private String jgdw;
	/**
	 * @Fields ckz : 查看值
	 */
	private String ckz;
	/**
	 * @Fields cssj : 测试时间
	 */
	private String cssj;
	/**
	 * @Fields jgbz : 结果病种
	 */
	private String jgbz;
	/**
	 * @Fields yzxmdm : 医嘱项目代码
	 */
	private String yzxmdm;
	/**
	 * @Fields yzxmmc : 医嘱项目名称
	 */
	private String yzxmmc;

	/**
	 * Return the unique identifier of this class
	 * 
	 * @hibernate.id generator-class="identity" column="bblsh"
	 */
	public String getBblsh() {
		return bblsh;
	}

	/**
	 * Set the value related to the column: bblsh
	 * 
	 * @param bblsh
	 *            the bblsh value
	 */
	public void setBblsh(String bblsh) {
		this.bblsh = bblsh;
	}

	public String getXmdm() {
		return xmdm;
	}

	/**
	 * Set the value related to the column: xmdm
	 * 
	 * @param xmdm
	 *            the xmdm value
	 */
	public void setXmdm(String xmdm) {
		this.xmdm = xmdm;
	}

	public String getXmmc() {
		return xmmc;
	}

	/**
	 * Set the value related to the column: xmmc
	 * 
	 * @param xmmc
	 *            the xmmc value
	 */
	public void setXmmc(String xmmc) {
		this.xmmc = xmmc;
	}

	public String getXmywmc() {
		return xmywmc;
	}

	/**
	 * Set the value related to the column: xmywmc
	 * 
	 * @param xmywmc
	 *            the xmywmc value
	 */
	public void setXmywmc(String xmywmc) {
		this.xmywmc = xmywmc;
	}

	public String getXmjg() {
		return xmjg;
	}

	/**
	 * Set the value related to the column: xmjg
	 * 
	 * @param xmjg
	 *            the xmjg value
	 */
	public void setXmjg(String xmjg) {
		this.xmjg = xmjg;
	}

	public String getJgdw() {
		return jgdw;
	}

	/**
	 * Set the value related to the column: jgdw
	 * 
	 * @param jgdw
	 *            the jgdw value
	 */
	public void setJgdw(String jgdw) {
		this.jgdw = jgdw;
	}

	public String getCkz() {
		return ckz;
	}

	/**
	 * Set the value related to the column: ckz
	 * 
	 * @param ckz
	 *            the ckz value
	 */
	public void setCkz(String ckz) {
		this.ckz = ckz;
	}

	public String getCssj() {
		return cssj;
	}

	/**
	 * Set the value related to the column: cssj
	 * 
	 * @param cssj
	 *            the cssj value
	 */
	public void setCssj(String cssj) {
		this.cssj = cssj;
	}

	public String getJgbz() {
		return jgbz;
	}

	/**
	 * Set the value related to the column: jgbz
	 * 
	 * @param jgbz
	 *            the jgbz value
	 */
	public void setJgbz(String jgbz) {
		this.jgbz = jgbz;
	}

	public String getYzxmdm() {
		return yzxmdm;
	}

	/**
	 * Set the value related to the column: yzxmdm
	 * 
	 * @param yzxmdm
	 *            the yzxmdm value
	 */
	public void setYzxmdm(String yzxmdm) {
		this.yzxmdm = yzxmdm;
	}

	public String getYzxmmc() {
		return yzxmmc;
	}

	/**
	 * Set the value related to the column: yzxmmc
	 * 
	 * @param yzxmmc
	 *            the yzxmmc value
	 */
	public void setYzxmmc(String yzxmmc) {
		this.yzxmmc = yzxmmc;
	}

}
