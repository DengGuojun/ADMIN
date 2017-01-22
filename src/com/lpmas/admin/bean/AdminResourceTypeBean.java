package com.lpmas.admin.bean;

import java.io.Serializable;

public class AdminResourceTypeBean implements Serializable {
	private static final long serialVersionUID = 8935982893831169859L;

	private int typeId = 0;
	private String typeName = "";
	private String memo = "";

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
}
