package com.lpmas.declare.admin.bean;

import java.sql.Timestamp;

import com.lpmas.framework.annotation.FieldTag;

public class GovernmentOrganizationInfoBean {
	@FieldTag(name = "主管部门ID")
	private int organizationId = 0;
	@FieldTag(name = "主管部门名称")
	private String organizationName = "";
	@FieldTag(name = "主管科室")
	private String department = "";
	@FieldTag(name = "负责人姓名")
	private String responsiblePersonName = "";
	@FieldTag(name = "负责人电话")
	private String responsiblePersonMobile = "";
	@FieldTag(name = "经办人姓名")
	private String operatorName = "";
	@FieldTag(name = "经办人电话")
	private String operatorMobile = "";
	@FieldTag(name = "传真")
	private String fax = "";
	@FieldTag(name = "地址")
	private String address = "";
	@FieldTag(name = "邮政编码")
	private String zipCode = "";
	@FieldTag(name = "是否示范区")
	private int isDemonstrationArea = 0;
	@FieldTag(name = "状态")
	private int status = 0;
	@FieldTag(name = "创建时间")
	private Timestamp createTime = null;
	@FieldTag(name = "创建用户")
	private int createUser = 0;
	@FieldTag(name = "修改时间")
	private Timestamp modifyTime = null;
	@FieldTag(name = "修改用户")
	private int modifyUser = 0;
	@FieldTag(name = "备注")
	private String memo = "";

	public int getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getResponsiblePersonName() {
		return responsiblePersonName;
	}

	public void setResponsiblePersonName(String responsiblePersonName) {
		this.responsiblePersonName = responsiblePersonName;
	}

	public String getResponsiblePersonMobile() {
		return responsiblePersonMobile;
	}

	public void setResponsiblePersonMobile(String responsiblePersonMobile) {
		this.responsiblePersonMobile = responsiblePersonMobile;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getOperatorMobile() {
		return operatorMobile;
	}

	public void setOperatorMobile(String operatorMobile) {
		this.operatorMobile = operatorMobile;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public int getIsDemonstrationArea() {
		return isDemonstrationArea;
	}

	public void setIsDemonstrationArea(int isDemonstrationArea) {
		this.isDemonstrationArea = isDemonstrationArea;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public int getCreateUser() {
		return createUser;
	}

	public void setCreateUser(int createUser) {
		this.createUser = createUser;
	}

	public Timestamp getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

	public int getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(int modifyUser) {
		this.modifyUser = modifyUser;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
}