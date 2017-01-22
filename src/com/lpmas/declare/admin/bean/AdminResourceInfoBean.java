package com.lpmas.declare.admin.bean;

import com.lpmas.framework.annotation.FieldTag;

public class AdminResourceInfoBean {
	@FieldTag(name = "资源ID")
	private int resourceId = 0;
	@FieldTag(name = "资源名称")
	private String resourceName = "";
	@FieldTag(name = "资源代码")
	private String resourceCode = "";
	@FieldTag(name = "资源类型ID")
	private int typeId = 0;
	@FieldTag(name = "状态")
	private int status = 0;
	@FieldTag(name = "备注")
	private String memo = "";

	public int getResourceId() {
		return resourceId;
	}

	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getResourceCode() {
		return resourceCode;
	}

	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
}