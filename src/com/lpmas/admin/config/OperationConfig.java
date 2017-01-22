package com.lpmas.admin.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.lpmas.framework.bean.StatusBean;
import com.lpmas.framework.util.StatusKit;

public class OperationConfig {
	public static final String SEARCH = "SEARCH";// 查询
	public static final String CREATE = "CREATE";// 创建
	public static final String UPDATE = "UPDATE";// 修改
	public static final String REMOVE = "REMOVE";// 删除
	public static final String EXPORT = "EXPORT";// 导出
	public static final String APPROVE = "APPROVE";// 审批

	public static List<StatusBean<String, String>> OPERATION_LIST = null;
	public static HashMap<String, String> OPERATION_MAP = null;

	static {
		new OperationConfig().init();
	}

	private void init() {
		initOperationList();
		initOperationMap();
	}

	private void initOperationList() {
		OPERATION_LIST = new ArrayList<StatusBean<String, String>>();
		OPERATION_LIST.add(new StatusBean<String, String>(SEARCH, "查询"));
		OPERATION_LIST.add(new StatusBean<String, String>(CREATE, "创建"));
		OPERATION_LIST.add(new StatusBean<String, String>(UPDATE, "修改"));
		OPERATION_LIST.add(new StatusBean<String, String>(REMOVE, "删除"));
		OPERATION_LIST.add(new StatusBean<String, String>(EXPORT, "导出"));
		OPERATION_LIST.add(new StatusBean<String, String>(APPROVE, "审批"));
	}

	private static void initOperationMap() {
		OPERATION_MAP = StatusKit.toMap(OPERATION_LIST);
	}
}
