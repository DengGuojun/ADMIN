package com.lpmas.admin.bean;

import java.io.Serializable;
import java.sql.Timestamp;

public class AdminUserInfoBean implements Serializable {
	private static final long serialVersionUID = 6747815849081528424L;

	private int userId = 0;
	private String loginId = "";
	private String loginPassword = "";
	private String adminUserName = "";
	private int adminUserType = 0;
	private String adminUserPose = "";
	private String adminUserDepartment = "";
	private String adminUserCompany = "";
	private String adminUserTelephone = "";
	private String adminUserMobile = "";
	private String adminUserEmail = "";
	private int status = 0;
	private Timestamp createTime = null;
	private int createUser = 0;
	private Timestamp modifyTime = null;
	private int modifyUser = 0;
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

	public int getAdminUserType() {
		return adminUserType;
	}

	public void setAdminUserType(int adminUserType) {
		this.adminUserType = adminUserType;
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

	public String getAdminUserCompany() {
		return adminUserCompany;
	}

	public void setAdminUserCompany(String adminUserCompany) {
		this.adminUserCompany = adminUserCompany;
	}

	public String getAdminUserTelephone() {
		return adminUserTelephone;
	}

	public void setAdminUserTelephone(String adminUserTelephone) {
		this.adminUserTelephone = adminUserTelephone;
	}

	public String getAdminUserMobile() {
		return adminUserMobile;
	}

	public void setAdminUserMobile(String adminUserMobile) {
		this.adminUserMobile = adminUserMobile;
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
