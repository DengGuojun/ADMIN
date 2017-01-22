package com.lpmas.admin.bean;

import java.io.Serializable;

public class AdminRoleUserBean implements Serializable {
	private static final long serialVersionUID = 5211576034100813141L;
	
	private int roleId = 0;
	private int userId = 0;

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}
