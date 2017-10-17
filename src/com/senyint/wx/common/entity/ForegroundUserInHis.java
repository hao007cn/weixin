package com.senyint.wx.common.entity;

import java.util.Date;

import com.senyint.core.entity.persistence.SurrogateKeyObject;

public class ForegroundUserInHis extends SurrogateKeyObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 病人id
	 */
	private Long patientId;
	
	/**
	 * 门诊号
	 */
	private  Long clinicNum;
	
	/**
	 * 姓名
	 */
	private String name;
	
	/**
	 * 性别
	 */
	private String sex;
	
	/**
	 * 年纪
	 */
	private String age;
	/**
	 * 生日
	 */
	private Date birthday;
	
	/**
	 * 费别_In 
	 */
	private String chargeType = "普通";
	
	/**
	 * 医疗付款方式
	 */
	private String payType = "01";
	
	/**
	 * 国籍
	 */
	private String nationality;
	
	/**
	 * 民族
	 */
	private String nation;
	
	/**
	 * 婚姻
	 */
	private String marriage;
	
	/**
	 * 职业
	 */
	private String occupation;
	
	/**
	 * 身份证号
	 */
	private String cardId;
	
	/**
	 * 工作单位
	 */
	private String company;
	
	/**
	 * 单位电话
	 */
	private String companyTel;
	
	/**
	 * 单位 邮编
	 */
	private String companyPostcode;
	
	/**
	 * 家庭地址
	 */
	private String houseAddress;
	
	/**
	 * 家庭电话
	 */
	private String houseTel;
	
	/**
	 * 家庭地址邮编
	 */
	private String housePostcode;
	
	/**
	 * sysdate 登记时间
	 */
	private Date  registrationTime;
	
	/**
	 * 户口地址
	 */
	private String accounts;
	
	/**
	 * 户口地址邮编
	 */
	private String accountPostcode;
	
	private String openId;

	/**
	 * 验证码 ，默认为关系表的poid
	 */
	private String validateCode;
	
	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public Long getClinicNum() {
		return clinicNum;
	}

	public void setClinicNum(Long clinicNum) {
		this.clinicNum = clinicNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getChargeType() {
		return chargeType;
	}

	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getMarriage() {
		return marriage;
	}

	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		if(!cardId.isEmpty()){
			this.cardId = cardId.trim().toUpperCase();
		}else {
			this.cardId = cardId;
		}		
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCompanyTel() {
		return companyTel;
	}

	public void setCompanyTel(String companyTel) {
		this.companyTel = companyTel;
	}

	public String getCompanyPostcode() {
		return companyPostcode;
	}

	public void setCompanyPostcode(String companyPostcode) {
		this.companyPostcode = companyPostcode;
	}

	public String getHouseAddress() {
		return houseAddress;
	}

	public void setHouseAddress(String houseAddress) {
		this.houseAddress = houseAddress;
	}

	public String getHouseTel() {
		return houseTel;
	}

	public void setHouseTel(String houseTel) {
		this.houseTel = houseTel;
	}

	public String getHousePostcode() {
		return housePostcode;
	}

	public void setHousePostcode(String housePostcode) {
		this.housePostcode = housePostcode;
	}

	public Date getRegistrationTime() {
		return registrationTime;
	}

	public void setRegistrationTime(Date registrationTime) {
		this.registrationTime = registrationTime;
	}

	public String getAccounts() {
		return accounts;
	}

	public void setAccounts(String accounts) {
		this.accounts = accounts;
	}

	public String getAccountPostcode() {
		return accountPostcode;
	}

	public void setAccountPostcode(String accountPostcode) {
		this.accountPostcode = accountPostcode;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}
	
}
