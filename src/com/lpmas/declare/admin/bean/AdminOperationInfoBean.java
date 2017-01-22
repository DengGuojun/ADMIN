package com.lpmas.declare.admin.bean;

import java.io.Serializable;

public class AdminOperationInfoBean implements Serializable {
	private static final long serialVersionUID = -4000246530748466920L;

	private int operationId = 0;
	private String operationName = "";
	private String operationCode = "";
	private String memo = "";

	public int getOperationId() {
		return operationId;
	}

	public void setOperationId(int operationId) {
		this.operationId = operationId;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public String getOperationCode() {
		return operationCode;
	}

	public void setOperationCode(String operationCode) {
		this.operationCode = operationCode;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
}
