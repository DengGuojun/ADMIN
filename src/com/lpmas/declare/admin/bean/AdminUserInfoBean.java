package com.lpmas.declare.admin.bean;

import java.sql.Date;
import java.sql.Timestamp;
import com.lpmas.framework.annotation.FieldTag;

public class AdminUserInfoBean {
	@FieldTag(name = "用户ID")
	private int userId = 0;
	@FieldTag(name = "登录ID")
	private String loginId = "";
	@FieldTag(name = "登录密码")
	private String loginPassword = "";
	@FieldTag(name = "用户名")
	private String adminUserName = "";
	@FieldTag(name = "用户类型")
	private String adminUserType = "";
	@FieldTag(name = "用户性别")
	private int adminUserGender = 0;
	@FieldTag(name = "用户生日")
	private Date adminUserBirthday = null;
	@FieldTag(name = "用户职位")
	private String adminUserPose = "";
	@FieldTag(name = "用户部门")
	private String adminUserDepartment = "";
	@FieldTag(name = "用户级别")
	private String adminUserLevel = "";
	@FieldTag(name = "国家")
	private String country = "";
	@FieldTag(name = "省")
	private String province = "";
	@FieldTag(name = "市")
	private String city = "";
	@FieldTag(name = "区")
	private String region = "";
	@FieldTag(name = "用户电话")
	private String adminUserTelephone = "";
	@FieldTag(name = "用户手机")
	private String adminUserPhone = "";
	@FieldTag(name = "用户传真")
	private String adminUserFax = "";
	@FieldTag(name = "用户邮箱")
	private String adminUserEmail = "";
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

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public String getAdminUserName() {
		return adminUserName;
	}

	public void setAdminUserName(String adminUserName) {
		this.adminUserName = adminUserName;
	}

	public String getAdminUserType() {
		return adminUserType;
	}

	public void setAdminUserType(String adminUserType) {
		this.adminUserType = adminUserType;
	}

	public int getAdminUserGender() {
		return adminUserGender;
	}

	public void setAdminUserGender(int adminUserGender) {
		this.adminUserGender = adminUserGender;
	}

	public Date getAdminUserBirthday() {
		return adminUserBirthday;
	}

	public void setAdminUserBirthday(Date adminUserBirthday) {
		this.adminUserBirthday = adminUserBirthday;
	}

	public String getAdminUserPose() {
		return adminUserPose;
	}

	public void setAdminUserPose(String adminUserPose) {
		this.adminUserPose = adminUserPose;
	}

	public String getAdminUserDepartment() {
		return adminUserDepartment;
	}

	public void setAdminUserDepartment(String adminUserDepartment) {
		this.adminUserDepartment = adminUserDepartment;
	}

	public String getAdminUserLevel() {
		return adminUserLevel;
	}

	public void setAdminUserLevel(String adminUserLevel) {
		this.adminUserLevel = adminUserLevel;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getAdminUserTelephone() {
		return adminUserTelephone;
	}

	public void setAdminUserTelephone(String adminUserTelephone) {
		this.adminUserTelephone = adminUserTelephone;
	}

	public String getAdminUserPhone() {
		return adminUserPhone;
	}

	public void setAdminUserPhone(String adminUserPhone) {
		this.adminUserPhone = adminUserPhone;
	}

	public String getAdminUserFax() {
		return adminUserFax;
	}

	public void setAdminUserFax(String adminUserFax) {
		this.adminUserFax = adminUserFax;
	}

	public String getAdminUserEmail() {
		return adminUserEmail;
	}

	public void setAdminUserEmail(String adminUserEmail) {
		this.adminUserEmail = adminUserEmail;
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