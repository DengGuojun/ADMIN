package com.lpmas.admin.bean;

import java.io.Serializable;

public class AdminPrivilegeInfoBean implements Serializable {
	private static final long serialVersionUID = -6258462180482492587L;

	private int roleId = 0;
	private int resourceId = 0;
	private int operationId = 0;

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getResourceId() {
		return resourceId;
	}

	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}

	public int getOperationId() {
		return operationId;
	}

	public void setOperationId(int operationId) {
		this.operationId = operationId;
	}
}
