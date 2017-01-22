package com.lpmas.admin.bean;

import java.io.Serializable;

public class AdminPrivilegeDefineBean implements Serializable {
	private static final long serialVersionUID = 233485472909018899L;

	private int resourceId = 0;
	private int operationId = 0;
	private String privilegeDesc = "";

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

	public String getPrivilegeDesc() {
		return privilegeDesc;
	}

	public void setPrivilegeDesc(String privilegeDesc) {
		this.privilegeDesc = privilegeDesc;
	}
}
