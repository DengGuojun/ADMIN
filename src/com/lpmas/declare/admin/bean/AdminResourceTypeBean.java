package com.lpmas.declare.admin.bean;

import com.lpmas.framework.annotation.FieldTag;

public class AdminResourceTypeBean {
	@FieldTag(name = "资源类型ID")
	private int typeId = 0;
	@FieldTag(name = "资源类型名称")
	private String typeName = "";
	@FieldTag(name = "备注")
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