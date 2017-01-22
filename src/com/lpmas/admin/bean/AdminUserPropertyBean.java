package com.lpmas.admin.bean;

import java.io.Serializable;

public class AdminUserPropertyBean implements Serializable {
	private static final long serialVersionUID = -3863644986216071287L;

	private int userId = 0;
	private String propertyCode = "";
	private String propertyValue = "";

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getPropertyCode() {
		return propertyCode;
	}

	public void setPropertyCode(String propertyCode) {
		this.propertyCode = propertyCode;
	}

	public String getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}
}
