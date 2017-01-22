package com.lpmas.declare.admin.bean;

import com.lpmas.framework.annotation.FieldTag;

public class AdminPrivilegeInfoBean {
	@FieldTag(name = "角色ID")
	private int roleId = 0;
	@FieldTag(name = "资源ID")
	private int resourceId = 0;
	@FieldTag(name = "操作ID")
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