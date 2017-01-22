package com.lpmas.admin.business;

public class AdminUtil {
	public static final String PRIVILEGE_SYMBOL = "@";

	public static String getPrivilegeCode(int resourceId, int operationId) {
		return resourceId + PRIVILEGE_SYMBOL + operationId;
	}

	public static String getPrivilegeCode(String resourceCode, String operationCode) {
		return resourceCode + PRIVILEGE_SYMBOL + operationCode;
	}

	public static int[] parsePrivilegeCode(String code) {
		int[] result = { 0, 0 };
		String[] array = code.split(PRIVILEGE_SYMBOL);
		result[0] = Integer.parseInt(array[0]);
		result[1] = Integer.parseInt(array[1]);
		return result;
	}
}
