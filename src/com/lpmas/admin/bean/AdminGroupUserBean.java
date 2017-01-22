package com.lpmas.admin.bean;

import java.io.Serializable;

public class AdminGroupUserBean implements Serializable {
	private static final long serialVersionUID = 8810140589115938761L;

	private int groupId = 0;
	private int userId = 0;

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}
