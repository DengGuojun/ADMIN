package com.lpmas.admin.bean;

import java.io.Serializable;

public class AdminRoleGroupBean implements Serializable {
	private static final long serialVersionUID = 2800253837298435961L;
	
	private int roleId = 0;
	private int groupId = 0;

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
}
